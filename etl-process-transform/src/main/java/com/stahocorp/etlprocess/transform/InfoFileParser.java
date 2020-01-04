package com.stahocorp.etlprocess.transform;

import com.stahocorp.etlprocess.config.EtlStatistics;
import com.stahocorp.etlprocess.items.InfoItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parser class of an info files from morele.net website.
 * It uses mostly jsoup to extract specific fields from html elements.
 */
public class InfoFileParser {

    private Document document;
    private InfoItem infoItem;
    private String fileName;

    /**
     * Constructor takes plain html to parse it using jsoup.
     * @param plainHtml product info as html
     */
    public InfoFileParser(String plainHtml) {
        this.document = Jsoup.parse(plainHtml);
    }

    /**
     * This method defines flow of a single html element parsing to POJO.
     * @return info as POJO
     */
    public InfoItem parseHtmlToItem() {
        infoItem = new InfoItem();
        Elements productMain = document.select("div[class=prod-info-inside]");
        getProductNameFromMain(productMain);
        getProductIdFromMain(productMain);
        getProductManufacturer(productMain);

        Elements productSide = document.select("div[class=product-sidebar-fixed]");
        getProductPriceFromSide(productSide);
        getProductPriceDiscountFromSide(productSide);
        getProductUnitsAvailableFromSide(productSide);

        Elements attributesCard = document.select("div[class=specification-table]");
        getProductAttributesFromAttributesCard(attributesCard);


        infoItem.setProcessingDate(LocalDateTime.now());
        EtlStatistics.infosProcessed.getAndIncrement();
        EtlStatistics.infosFilesProcessed.getAndIncrement();
        return infoItem;
    }

    /**
     * Get product manufacturer name from jsoup element
     * @param main element which contains product manufacturer
     */
    private void getProductManufacturer(Elements main) {
        String name = main.select("a[class=prod-brand]").text();
        infoItem.setManufacturer(name);
    }

    /**
     * Get product name from jsoup element
     * @param main element which contains product name
     */
    private void getProductNameFromMain(Elements main) {
        String name = main.select("h1[class=prod-name]").text();
        infoItem.setName(name);
    }

    /**
     * Get product id from jsoup element
     * @param main element which contains product id
     */
    private void getProductIdFromMain(Elements main){
        String tempId = main.select("div[class=prod-id-contact]").text();
        List<String> idAttributes = Arrays.asList(tempId.split(" "));
        infoItem.setProductId(Long.parseLong(idAttributes.get(2)));
    }

    /**
     * Get product price from jsoup element
     * @param side element which contains product price
     */
    private void getProductPriceFromSide(Elements side) {
        String tempPrice = side.select("div[class=price-new]").attr("content");
        infoItem.setPrice(Double.parseDouble(tempPrice));
    }

    /**
     * Get product price before discount from jsoup element
     * @param side element which contains old product price
     */
    private void getProductPriceDiscountFromSide(Elements side) {
        String tempId = side.select("div[class=price-old]").text();
        List<String> idAttributes = Arrays.asList(tempId.split(" "));
        String priceDiscount = idAttributes.get(0);
        if(priceDiscount.isEmpty()) {
            infoItem.setDiscount(false);
        } else {
            infoItem.setDiscount(true);
            infoItem.setPriceDiscount(Double.parseDouble(priceDiscount));
        }

    }

    /**
     * Get amount of available products from jsoup element
     * @param side element which contains number of available product units
     */
    private void getProductUnitsAvailableFromSide(Elements side) {
        String tempNo = side.select("div[class=prod-available]").text();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(tempNo);
        if(matcher.find())
            infoItem.setNoOfUnitAvailable(Integer.parseInt(matcher.group()));
    }

    /**
     * Get product attributes from jsoup element
     * @param card element which contains product attributes
     */
    private void getProductAttributesFromAttributesCard(Elements card) {
        Element table = card.select("div[class=specification-table]").first();
        Elements children = table.children();
        Map<String, Map<String, String>> tempMap = new LinkedHashMap<>();
        String tempMainAttribute = "";
        for(Element x: children) {
            if(x.tagName().equalsIgnoreCase("h5")){
                tempMap.put(x.text(), new LinkedHashMap<>());
                tempMainAttribute = x.text();
            } else {
                Elements names = x.children()
                        .select("div[class=table-info-item]")
                        .stream()
                        .filter(c -> !c.text().isEmpty())
                        .collect(Collectors.toCollection(Elements::new));
                tempMap.put(tempMainAttribute, extractSpecificationFromInfoItem(names));
            }
        }

        infoItem.setAttributes(tempMap);
    }

    /**
     * Get product specification from jsoup element
     * @param inf element which contains product specification
     */
    private Map<String, String> extractSpecificationFromInfoItem(Elements inf) {
        Map<String,String> tempMap = new LinkedHashMap<>();

        for(Element x: inf) {
            StringBuilder stringBuilder = new StringBuilder();
            x.select("div[class=info-item]")
                    .forEach(c -> {
                        if(stringBuilder.length() != 0) stringBuilder.append(", ");
                        stringBuilder.append(c.text());
                    });
            tempMap.put(x.getElementsByAttributeValueContaining("class", "table-info-inner name").first().text(), stringBuilder.toString());
        }

        return tempMap;
    }
}

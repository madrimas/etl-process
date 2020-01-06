package data.warehouse.service;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Main controller to handle requests
 */
@RestController
public class Scrapper {

    /**
     * Field to keep host name, value come from src/main/java/resources/application.properties
     */
    @Value(value = "${host.name}")
    private String hostname;

    /**
     * Field to keep path for file storage, value come from src/main/java/resources/application.properties
     */
    @Value(value = "${path.to.write.files}")
    private String fileStoragePath;

    /**
     * Field to keep link from which scrapper will start getting content, value come from src/main/java/resources/application.properties
     */
    @Value(value = "${link.to.scrap}")
    private String linkToScrapFrom;

    /**
     * Map which contains statistics about scrapper
     */
    private Map<String, Object> statistics = new HashMap<>(Map.of("time", 0, "productFilesScrapped", 0, "opinionFilesScrapped", 0, "isFinished", false));

    /**
     * Main controller which collects all the data about products and opinions, then write it to file storage specified in {@link Scrapper#fileStoragePath}.
     * Files are named in convention: morele-{productId}-{timestamp}-(info/opinions).html
     * Also counts statistics about progress and keep them in {@link Scrapper#statistics}
     */
    @RequestMapping("/")
    public void scrap() {
        var processTimeStart = Instant.now();
        var productsAmount = 0;
        var opinionsAmount = 0;
        statistics.put("isFinished", false);
        try {
            var dateString = new SimpleDateFormat("yyyyMMdd").format(new Date());

            for (String hrefToProduct : getAllLinksToProducts()) {
                var document = Jsoup.connect(hostname + hrefToProduct).get();
                var mainBlock = document.select("main");
                var productId = document.select("div.grey-action").text().replaceAll("\\D+", "");

                var path = Paths.get(fileStoragePath + "morele-" + productId + "-" + dateString + "-info.html");
                Files.write(path, mainBlock.toString().getBytes());
                productsAmount++;
                statistics.put("productFilesScrapped", productsAmount);

                var productOpinions = getOpinions(document);
                if (!productOpinions.isEmpty()) {
                    path = Paths.get(fileStoragePath + "morele-" + productId + "-" + dateString + "-opinions.html");
                    Files.write(path, "<opinions>\n".getBytes());
                    for (String opinion : productOpinions) {
                        Files.write(path, opinion.getBytes(), StandardOpenOption.APPEND);
                    }
                    Files.write(path, "</opinions>".getBytes(), StandardOpenOption.APPEND);
                    opinionsAmount++;
                    statistics.put("opinionFilesScrapped", opinionsAmount);
                }
                var timeElapsed = Duration.between(processTimeStart, Instant.now()).toSeconds();
                statistics.put("time", timeElapsed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        statistics.put("isFinished", true);
    }

    /**
     * Method to get statistics about web scrapper work
     *
     * @return statistic of web scrapping in json format which returns time in seconds, product and opinions files amount
     */
    @RequestMapping("/stats")
    public String getStatistics() {
        var json = new JSONObject();
        json.put("time", statistics.get("time"));
        json.put("productFilesScrapped", statistics.get("productFilesScrapped"));
        json.put("opinionFilesScrapped", statistics.get("opinionFilesScrapped"));
        json.put("isFinished", statistics.get("isFinished"));
        return json.toString();
    }

    /**
     * Method to get all links from web service from {@link Scrapper#linkToScrapFrom} field, goes page by page on site
     *
     * @return links to all products scrapped page by page
     * @throws IOException if can't connect to website
     */
    private List<String> getAllLinksToProducts() throws IOException {
        var links = new ArrayList<String>();
        var mainDocument = Jsoup.connect(linkToScrapFrom + "1/").get();
        var pagesAmount = Integer.valueOf(mainDocument.select("ul.pagination.dynamic").attr("data-count"));
        for (var i = 1; i <= pagesAmount; ++i) {
            var document = Jsoup.connect(linkToScrapFrom + i + "/").get();
            var linksAtPage = document.select("a.productLink.cat-product-image");
            linksAtPage.forEach(linkElement -> links.add(linkElement.attr("href")));
        }
        return links;
    }

    /**
     * Method to get opinions about product
     *
     * @param document - html document about product which contains opinions about it
     * @return html document ready to parse by transformer or empty list if there is no opinions about product
     */
    private List<String> getOpinions(Document document) {
        var result = new ArrayList<String>();
        var opinions = document.select("div.reviews-item");
        opinions.forEach(opinion -> {
            result.add("<opinion>" + opinion.toString() + "</opinion>");
        });
        return result;
    }

    /**
     * Creating storage path if not exists, runs on startup
     */
    @Autowired
    private void setup() {
        new File(fileStoragePath).mkdirs();
    }

}
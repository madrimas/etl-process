package com.stahocorp.etlprocess.transform;

import com.stahocorp.etlprocess.config.EtlStatistics;
import com.stahocorp.etlprocess.items.OpinionItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is used for parsing opinions about products from morele.net website.
 */
public class OpinionFileParser {
    private Document content;
    private List<OpinionItem> opinions;

    /**
     * Constructor takes html element with opinions
     * @param plainHtml plain html
     */
    public OpinionFileParser(String plainHtml) {
        this.content = Jsoup.parse(plainHtml);
    }

    /**
     * This method is used to split one opinion html tag into single opinion elements
     * @return list of opinions
     */
    public List<OpinionItem> parseHtmlToItems() {
        Elements parsingOpinions = content.select("div[class*=reviews-item]");
        opinions = parsingOpinions
                .stream()
                .map(this::parseSingleOpinion)
                .collect(Collectors.toList());

        EtlStatistics.opinionFilesProcessed.getAndIncrement();
        return opinions;
    }

    /**
     * This method is used to parse single review opinion from html element into POJO
     * @param opinion single opinion element from jsoup parser
     * @return opinion POJO
     */
    private OpinionItem parseSingleOpinion(Element opinion) {
        OpinionItem opinionItem = new OpinionItem();

        opinionItem.setComment(getTextFromJquery("div[class=rev-desc]", opinion));
        opinionItem.setOpinionAuthor(getTextFromJquery("div[class=rev-author]", opinion));
        opinionItem.setOpinionId(Long.parseLong(opinion.attributes().get("data-review-id")));

        String tempDateString = getTextFromJquery("div[class=rev-date]", opinion);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        opinionItem.setOpinionPublicationDate(LocalDate.parse(tempDateString, formatter).atStartOfDay());

        Element stars = opinion.select("div[class=rev-stars]").first();
        opinionItem.setRating(getRatingFromComment(stars));

        if (opinion.select("div[class=rev-confirmed]").first() != null) {
            opinionItem.setProductBought(true);
        } else {
            opinionItem.setProductBought(false);
        }


        opinionItem.setProcessingDate(LocalDateTime.now());
        EtlStatistics.opinionsProcessed.getAndIncrement();
        return opinionItem;
    }

    /**
     * Used for getting text from first element which fulfilled jquery selection.
     * @param jquery jquery selector of elements
     * @param element jsoup element
     * @return text from bottom element in tree
     */
    private String getTextFromJquery(String jquery, Element element){
     return element
             .select(jquery)
             .first()
             .text();
    }

    /**
     * This method is used to extract consumer rating about product from jsoup element by query written below.
     * @param element input element
     * @return consumer rating as number
     */
    private double getRatingFromComment(Element element) {
        Element star = element.select("input[checked]").first();
        return Double.parseDouble(star.attributes().get("value"));
    }


}

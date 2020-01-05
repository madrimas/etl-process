package data.warehouse.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class Scrapper {

    @Value(value = "${host.name}")
    private String hostname;

    @Value(value = "${path.to.write.files}")
    private String fileStoragePath;

    @Value(value = "${link.to.scrap}")
    private String linkToScrapFrom;

    @RequestMapping("/")
    public void scrap() {
        try {
            var dateString = new SimpleDateFormat("yyyyMMdd").format(new Date());

            for (String hrefToProduct : getAllLinksToProducts()) {
                var document = Jsoup.connect(hostname + hrefToProduct).get();
                var mainBlock = document.select("main");
                var productId = document.select("div.grey-action").text().replaceAll("\\D+", "");

                var path = Paths.get(fileStoragePath + "morele-" + productId + "-" + dateString + "-info.html");
                Files.write(path, mainBlock.toString().getBytes());

                var productOpinions = getOpinions(document);
                if (!productOpinions.isEmpty()) {
                    path = Paths.get(fileStoragePath + "morele-" + productId + "-" + dateString + "-opinions.html");
                    Files.write(path, "<opinions>\n".getBytes());
                    for (String opinion : productOpinions) {
                        Files.write(path, opinion.getBytes(), StandardOpenOption.APPEND);
                    }
                    Files.write(path, "</opinions>".getBytes(), StandardOpenOption.APPEND);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private List<String> getOpinions(Document document) {
        var result = new ArrayList<String>();
        var opinions = document.select("div.reviews-item");
        opinions.forEach(opinion -> {
            result.add("<opinion>" + opinion.toString() + "</opinion>");
        });
        return result;
    }

}

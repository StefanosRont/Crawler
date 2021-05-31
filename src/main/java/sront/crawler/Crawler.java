package sront.crawler;

import java.io.IOException;
import java.util.HashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sront.crawler.schema.output.Article;
import sront.crawler.schema.input.Category;
import sront.crawler.schema.input.Sites;
import sront.crawler.schema.output.Site;

public class Crawler {
    Sites inputSites;
    private final DocumentFetcher documentFetcher;
    private final OutputWriter outputWriter;


    public Crawler(Sites inputSites,
                   DocumentFetcher documentFetcher,
                   OutputWriter outputWriter) {
        this.inputSites = inputSites;
        this.documentFetcher = documentFetcher;
        this.outputWriter = outputWriter;
    }

    public void startCrawling() throws IOException {
        outputWriter.writeResultsStart();
        for (sront.crawler.schema.input.Site inputSite : inputSites.getSites()) {
            // Create a new output site
            Site outputSite =
                    new Site();
            outputSite.setUrl(inputSite.getSite());
            outputWriter.writeSiteStart(inputSite.getSite());

            // Crawl all categories for current input site
            for (Category inputCategory : inputSite.getCategories()) {
                // Create a new output category
                outputWriter.writeCategoryStart(inputCategory.getCategory());
                try {
                    crawl(inputCategory, inputCategory.getCategory(), 1,  new HashSet<>());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    outputWriter.writeCategoryEnd();
                }
            }
            outputWriter.writeSiteEnd();
        }
        outputWriter.writeResultsEnd();
    }

    private boolean shouldVisit(String urlPrefix, String nextLink, HashSet<String> visited) {
        return !nextLink.isBlank() && nextLink.startsWith(urlPrefix + "/") && !visited.contains(nextLink);
    }

    private void setNewArticle(Category inputCategory,
                               String currentUrl,
                               Document articleDocument) throws IOException {
        Article outputArticle = new Article();
        outputArticle.setLink(currentUrl);

        Element titleElement = articleDocument.selectFirst(inputCategory.getTitleSelector());
        if (titleElement != null) {
            outputArticle.setTitle(titleElement.text());
        }

        Elements bodyElements = articleDocument.select(inputCategory.getBodySelector());
        for (Element bodyElement : bodyElements) {
            if (bodyElement != null && !bodyElement.text().isBlank()) {
                outputArticle.setBody(bodyElement.text());
                break;
            }
        }
        outputWriter.consumeArticle(outputArticle);
    }

    private void crawl(Category inputCategory,
                       String currentUrl,
                       int level,
                       HashSet<String> visited) throws IOException {
        try {
            Document doc = documentFetcher.connectAndParse(currentUrl);
            if (doc == null) {
                return;
            }
            visited.add(currentUrl);
            // TODO This needs to be revisited. Need a better way to identify if the Document is
            // an article.
            if (level > 1) {
                // The current `doc` is an article
                setNewArticle(inputCategory, currentUrl, doc);
            } else {
                for (String articleSelector : inputCategory.getArticleSelector()) {
                    for (Element link : doc.select(articleSelector)) {
                        String nextLink = link.absUrl("href");

                        if (shouldVisit(inputCategory.getCategory(), nextLink, visited)) {
                            crawl(inputCategory, nextLink, level + 1, visited);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}

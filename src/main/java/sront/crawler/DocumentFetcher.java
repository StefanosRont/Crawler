package sront.crawler;

import org.jsoup.nodes.Document;

public interface DocumentFetcher {
    Document connectAndParse(String url);
}

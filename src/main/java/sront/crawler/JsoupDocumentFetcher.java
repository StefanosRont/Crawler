package sront.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupDocumentFetcher implements DocumentFetcher {
    static final int CONN_TIMEOUT_MILLIS = 4000;

    public Document connectAndParse(String url) {
        Connection con = Jsoup.connect(url);
        con.timeout(CONN_TIMEOUT_MILLIS);
        try {
            Document doc = con.get();
            if (con.response().statusCode() == 200) {
                return doc;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

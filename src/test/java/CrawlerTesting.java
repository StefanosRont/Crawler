import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.jsoup.nodes.Document;
import sront.crawler.DocumentFetcher;
import sront.crawler.OutputWriter;
import sront.crawler.Crawler;
import sront.crawler.schema.input.Sites;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrawlerTests {

    /**
     * Reads given resource file as a string.
     *
     * @param fileName path to the resource file
     * @return the file's contents
     * @throws IOException if read fails for any reason
     */
    static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    static Sites GetTestInputSites() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return
                mapper.readValue(getResourceFileAsString("input.json"),
                        Sites.class);
    }

    @Test
    void crawlArticlesTest() throws IOException {
        DocumentFetcher documentFetcher = mock(DocumentFetcher.class);
        String baseUri = "https://www.capital.gr/diethni";
        Document testDocument = new Document(baseUri);
        testDocument.prepend(getResourceFileAsString("categoryTest.html"));
        when(documentFetcher.connectAndParse(baseUri)).thenReturn(testDocument);

        String articleUri = "https://www.capital.gr/diethni/3519480/rosiko-embolio-kata-tou" +
                "-koronoiou-se-sprei-edeixe-asfaleia-kai-anosologiki-apokrisi";
        Document articleDocument = new Document(articleUri);
        articleDocument.prepend(getResourceFileAsString("articleTest.html"));
        when(documentFetcher.connectAndParse(articleUri)).thenReturn(articleDocument);

        ObjectMapper outputObjectMapper = new ObjectMapper();
        Writer jsonWriter = new StringWriter();
        JsonGenerator jsonGenerator = outputObjectMapper.getFactory().createGenerator(jsonWriter);
        OutputWriter outputWriter = new OutputWriter(jsonGenerator);

        Sites inputSites = GetTestInputSites();
        Crawler crawler = new Crawler(inputSites, documentFetcher, outputWriter);
        crawler.startCrawling();
        jsonGenerator.close();

        String expectedJson = getResourceFileAsString("expectedResults.json");
        assertEquals(expectedJson, jsonWriter.toString());
    }
}
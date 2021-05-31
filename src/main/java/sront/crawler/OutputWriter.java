package sront.crawler;

import com.fasterxml.jackson.core.JsonGenerator;
import sront.crawler.schema.output.Article;

import java.io.IOException;

// To support streaming writes to output JSON
public class OutputWriter {
    private final JsonGenerator jsonGenerator;

    public OutputWriter(JsonGenerator jsonGenerator) {
        this.jsonGenerator = jsonGenerator;
    }

    public void writeResultsStart() throws IOException {
        jsonGenerator.useDefaultPrettyPrinter();
        jsonGenerator.writeStartArray();
    }

    public void writeResultsEnd() throws IOException {
        jsonGenerator.writeEndArray();
    }

    public void writeSiteStart(String siteUrl) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("url", siteUrl);
        jsonGenerator.writeFieldName("categories");
        jsonGenerator.writeStartArray();
    }

    public void writeSiteEnd() throws IOException {
        // close categories array
        jsonGenerator.writeEndArray();
        // close whole site object
        jsonGenerator.writeEndObject();
    }

    public void writeCategoryStart(String category) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("category", category);
        jsonGenerator.writeFieldName("articles");
        jsonGenerator.writeStartArray();
    }

    public void writeCategoryEnd() throws IOException {
        // end articles array
        jsonGenerator.writeEndArray();
        // end category object
        jsonGenerator.writeEndObject();
    }

    public void consumeArticle(Article article) throws IOException {
        jsonGenerator.writeObject(article);
    }
}

package sront.crawler;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        for (String arg : args) System.out.println(arg);
        if (args.length < 2) {
            System.err.println("Usage: crawl <input filename json> <output " +
                    "filename json>");
            System.exit(1);
        }

        String outputFilename = args[1];
        FileUtil.CreateOutputDirsAndFile(outputFilename);

        ObjectMapper outputObjectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator =
                outputObjectMapper.getFactory().createGenerator(new File(outputFilename),
                        JsonEncoding.UTF8);

        OutputWriter outputWriter = new OutputWriter(jsonGenerator);
        Crawler crawler = new Crawler(FileUtil.GetInputSites(args[0]),
                new JsoupDocumentFetcher(), outputWriter);
        crawler.startCrawling();
        jsonGenerator.close();
    }
}

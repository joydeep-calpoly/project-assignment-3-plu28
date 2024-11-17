package org.testparser;

import static org.junit.Assert.assertEquals;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.junit.Test;
import org.parser.*;


import jdk.jfr.Timestamp;

public class TestStandardParser {

    // Test that a json of all bad articles removes all bad articles
    @Test
    public void checkBadArticlesRemoved() {
        String jsonPath = "inputs/allbad.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        StandardParser testParser = new StandardParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that a json of all good articles removes no good articles
    @Test
    public void checkGoodArticlesStay() {
        String jsonPath = "inputs/allgood.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        StandardParser testParser = new StandardParser(logger);
        String expected =
            "[The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n, The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n, The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n, The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n]";

        assertEquals(testParser.getArticles(jsonString).toString(), expected);
    }

    // Test that a json of 4 bad articles and 4 good articles removes only bad articles
    @Test
    public void checkMixedBag() {
        String jsonPath = "inputs/mixedbag.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        StandardParser testParser = new StandardParser(logger);
        String expected =
            "[The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n, The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n, The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n, The latest on the coronavirus pandemic and vaccines: Live updates - CNN\nThe coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.\nWed Mar 24 15:32:00 PDT 2021\nhttps://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html\n]";
        assertEquals(testParser.getArticles(jsonString).toString(), expected);
    }

    // Test that an article with a bad url is removed
    @Test
    public void checkBadURL() {
        String jsonPath = "inputs/badurl.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        StandardParser testParser = new StandardParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that an article with a bad title is removed
    @Test
    public void checkBadTitle() {
        String jsonPath = "inputs/badtitle.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        StandardParser testParser = new StandardParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that an article with a bad description is removed
    @Test
    public void checkBadDesc() {
        String jsonPath = "inputs/baddesc.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        StandardParser testParser = new StandardParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that an article with a bad publishedAt is removed
    @Test
    public void checkBadDate() {
        String jsonPath = "inputs/baddate.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        StandardParser testParser = new StandardParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that print articles (on a file format parameter) calls internal get articles method 
    @Test
    public void checkPrintArticlesFileFormat() {
        FileFormat mockFileFormat = Mockito.mock(FileFormat.class);
        Logger logger = Logger.getLogger("");

        // Create a mockito spy 
        StandardParser standardParserSpy = Mockito.spy(new StandardParser(logger));


        String jsonString = fileToString("inputs/example.json");
        when(mockFileFormat.getString()).thenReturn(jsonString);

        // Call printArticles with a file format
        standardParserSpy.printArticles(mockFileFormat);

        // Check that getArticles was called with the file converted to a string
        verify(standardParserSpy, times(1)).getArticles(jsonString);

    }

    // Test that print articles (on a URL format parameter) calls internal get articles method
    @Test
    public void checkPrintArticlesURLFormat() {

        URLFormat mockURLFormat = Mockito.mock(URLFormat.class);
        Logger logger = Logger.getLogger("");

        StandardParser standardParserSpy = Mockito.spy(new StandardParser(logger));

        String jsonString = fileToString("inputs/example.json");
        when(mockURLFormat.getString()).thenReturn(jsonString);

        standardParserSpy.printArticles(mockURLFormat);

        verify(standardParserSpy, times(1)).getArticles(jsonString);

    }

    // Helper function to convert a file to a string
    private String fileToString(String jsonPath) {
        String jsonString = "";
        try {
            byte bytes[] = Files.readAllBytes(Paths.get(jsonPath));
            jsonString = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}

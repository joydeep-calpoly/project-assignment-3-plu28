package org.testparser;

import static org.junit.Assert.assertEquals;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.junit.Test;
import org.parser.*;

public class TestSimpleParser {

    // Test that it returns 1 when article healthy (has all required attributes)
    @Test
    public void noRemoves() {
        String jsonPath = "inputs/simple.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        SimpleParser testParser = new SimpleParser(logger);
        String expected =
            "[Assignment #2\nExtend Assignment #1 to support multiple sources and to introduce source processor.\n2021-04-16T09:53:23.709229\nhttps://canvas.calpoly.edu/courses/55411/assignments/274503\n]";
        assertEquals(testParser.getArticles(jsonString).toString(), expected);
    }

    // Test that it removes article when missing title
    @Test
    public void removesBadTitle() {
        String jsonPath = "inputs/simplebadtitle.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        SimpleParser testParser = new SimpleParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that it removes article when missing description
    @Test
    public void removesBadDesc() {
        String jsonPath = "inputs/simplebaddesc.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        SimpleParser testParser = new SimpleParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that it removes article when missing url
    @Test
    public void removesBadURL() {
        String jsonPath = "inputs/simplebadurl.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        SimpleParser testParser = new SimpleParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that it removes article when missing publishedAt
    @Test
    public void removesBadDate() {
        String jsonPath = "inputs/simplebaddate.json";
        String jsonString = fileToString(jsonPath);
        Logger logger = Logger.getLogger("");
        SimpleParser testParser = new SimpleParser(logger);
        assert testParser.getArticles(jsonString).size() == 0;
    }

    // Test that print articles (on a file format parameter) calls internal get articles method 
    @Test
    public void checkPrintArticlesFileFormat() {
        FileFormat mockFileFormat = Mockito.mock(FileFormat.class);
        Logger logger = Logger.getLogger("");

        // Create a mockito spy 
        SimpleParser simpleParserSpy = Mockito.spy(new SimpleParser(logger));


        String jsonString = fileToString("inputs/simple.json");
        when(mockFileFormat.getString()).thenReturn(jsonString);

        // Call printArticles with a file format
        simpleParserSpy.printArticles(mockFileFormat);

        // Check that getArticles was called with the file converted to a string
        verify(simpleParserSpy, times(1)).getArticles(jsonString);

    }

    // Test that print articles (on a URL format parameter) DOES NOT call internal get articles method
    @Test
    public void checkPrintArticlesURLFormat() {

        URLFormat mockURLFormat = Mockito.mock(URLFormat.class);
        Logger logger = Logger.getLogger("");

        SimpleParser simpleParserSpy = Mockito.spy(new SimpleParser(logger));

        String jsonString = fileToString("inputs/simple.json");
        when(mockURLFormat.getString()).thenReturn(jsonString);

        simpleParserSpy.printArticles(mockURLFormat);

        verify(simpleParserSpy, times(0)).getArticles(jsonString);

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

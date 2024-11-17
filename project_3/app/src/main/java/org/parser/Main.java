package org.parser;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// Implementing as visitor pattern:
// Create an interface of ParserVisitor
// Than you can have a class of ParserGetArticlesVisitor
// ParserGetArticlesVisitor has overloaded getArticles that take in different file types? So not just json string? A bunch of overloaded functions
// Add a ParserGetArticlesVisitor object in the Parser interface
// Every parser has a ParserGetArticles obj

public class Main {

    public static void main(String[] args) {
        // Create an configure logger
        Logger logger = Logger.getLogger("");

        // Remove console handler for logger
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            if (handler instanceof ConsoleHandler) {
                logger.removeHandler(handler);
            }
        }

        // Configure the logger to print to a file rather than console
        try {
            FileHandler fileHandler = new FileHandler("parser.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler); // sets the handler to be fileHandler
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Running standard parser on a file
        StandardParser standardParser = new StandardParser(logger);
        SourceFormat ff = new FileFormat("inputs/mixedbag.json");
        ff.accept(standardParser); // Should print

        // Running standard parser on a URL
        String apiKey = Dotenv.load().get("API_KEY");
        String urlString =
            "http://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;
        SourceFormat uf = new URLFormat(urlString);
        uf.accept(standardParser);

        // Running simple parser on a file
        SimpleParser simpleParser = new SimpleParser(logger);
        SourceFormat sff = new FileFormat("inputs/simple.json");
        sff.accept(simpleParser);
    }
}

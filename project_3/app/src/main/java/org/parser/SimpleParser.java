package org.parser;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleParser extends Parser<List<SimpleArticle>> {

    public SimpleParser(Logger logger) {
        super(logger);
    }

    public void printArticles(FileFormat ff) {
        // Converting file to a string
        String jsonString = ff.getString();
        for (SimpleArticle article : getArticles(jsonString)) {
            System.out.println(article);
        }
    }

    public void printArticles(URLFormat url) {
        System.out.println("URLFormat is invalid for SimpleParser");
    }

    /**
     * Given a string representation of a simple News json, returns a list of SimpleArticle objects (a list of 1).
     * Note: Articles that are missing a key field are automatically removed from the return list.
     * Key fields: String title, String description, String url, Date publishedAt
     * @param String - jsonString a string representation of simple json response
     * @return List<SimpleArticles.SimpleArticle> articlesList - A list of SimpleArticle objects (nested in the SimpleArticles class)
     */
    public List<SimpleArticle> getArticles(String jsonString) {
        // Use DateTimeFormatter to parse microseconds
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSSSS"
        );

        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // Need a customer serializing solution because of unique format of publishedAt element
        javaTimeModule.addDeserializer(
            LocalDateTime.class,
            new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer(
                formatter
            )
        );
        javaTimeModule.addSerializer(
            LocalDateTime.class,
            new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer(
                formatter
            )
        );

        // Register custom javeTimeModule to our mapper
        mapper.registerModule(javaTimeModule);

        SimpleArticle articles = null;

        try {
            articles = mapper.readValue(jsonString, SimpleArticle.class);
        } catch (DatabindException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
        List<SimpleArticle> articlesList = articles.getArticles();

        // Need the index, therefore using a traditional for loop over enhanced
        for (int i = articlesList.size() - 1; i >= 0; i--) {
            if (articlesList.get(i).getTitle() == null) {
                this.logger.warning(
                        "Article has no title and has been removed"
                    );
                articlesList.remove(i);
                continue;
            } else if (articlesList.get(i).getUrl() == null) {
                this.logger.warning("Article has no url and has been removed");
                articlesList.remove(i);
                continue;
            } else if (articlesList.get(i).getDescription() == null) {
                this.logger.warning(
                        "Article has no description and has been removed"
                    );
                articlesList.remove(i);
                continue;
            } else if (articlesList.get(i).getPublishedAt() == null) {
                this.logger.warning(
                        "Article has no published at date and has been removed"
                    );
                articlesList.remove(i);
                continue;
            }
        }

        return articlesList;
    }
}

package org.parser;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class StandardParser
    extends Parser<List<StandardArticles.StandardArticle>> {

    public StandardParser(Logger logger) {
        super(logger);
    }

    public void printArticles(FileFormat ff) {
        // Converting file to a string
        String jsonString = ff.getString();
        for (StandardArticles.StandardArticle article : getArticles(
            jsonString
        )) {
            System.out.println(article);
        }
    }

    public void printArticles(URLFormat uf) {
        String jsonString = uf.getString();

        for (StandardArticles.StandardArticle article : getArticles(
            jsonString
        )) {
            System.out.println(article);
        }
    }

    /**
     * Given a string representation of a json NewsAPI response, returns a list of StandardArticle objects.
     * Note: Articles that are missing a key field are automatically removed from the return list.
     * Key fields: String title, String description, String url, Date publishedAt
     * @param String - jsonString a string representation of the NewsAPI json response
     * @return List<StandardArticles.StandardArticle> articlesList - A list of StandardArticle objects (nested in the StandardArticles class)
     */
    public List<StandardArticles.StandardArticle> getArticles(
        String jsonString
    ) {
        ObjectMapper mapper = new ObjectMapper();

        StandardArticles articles = null;
        try {
            articles = mapper.readValue(jsonString, StandardArticles.class);
        } catch (DatabindException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        // Remove articles with a null title, url, publishedAt, description and log them
        List<StandardArticles.StandardArticle> articlesList =
            articles.getArticles();

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

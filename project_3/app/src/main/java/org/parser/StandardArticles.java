package org.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class StandardArticles {

    final String status;
    private final int totalResults;
    private final List<StandardArticle> articles;

    @JsonCreator
    private StandardArticles(
        @JsonProperty("status") String status,
        @JsonProperty("totalResults") int totalResults,
        @JsonProperty("articles") List<StandardArticle> articles
    ) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    /**
     * Returns the list of articles field.
     * @return List of Article
     */
    List<StandardArticle> getArticles() {
        return this.articles;
    }

    public static class StandardArticle {

        private final Source source;
        private final String author;
        private final String title;
        private final String description;
        private final String url;
        private final String urlToImage;
        private final Date publishedAt;
        private final String content;

        @JsonCreator
        private StandardArticle(
            @JsonProperty("source") Source source,
            @JsonProperty("author") String author,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("url") String url,
            @JsonProperty("urlToImage") String urlToImage,
            @JsonProperty("publishedAt") Date publishedAt,
            @JsonProperty("content") String content
        ) {
            this.source = source;
            this.author = author;
            this.title = title;
            this.description = description;
            this.url = url;
            this.urlToImage = urlToImage;
            this.publishedAt = publishedAt;
            this.content = content;
        }

        String getTitle() {
            return this.title;
        }

        String getDescription() {
            return this.description;
        }

        Date getPublishedAt() {
            return this.publishedAt;
        }

        String getUrl() {
            return this.url;
        }

        /*
         * Returns string representation of a StandardArticle
         * @return String
         */
        public String toString() {
            return new StringBuilder()
                .append(this.title)
                .append("\n")
                .append(this.description)
                .append("\n")
                .append(this.publishedAt)
                .append("\n")
                .append(this.url)
                .append("\n")
                .toString();
        }

        public static class Source {

            private final String id;
            private final String name;

            @JsonCreator
            private Source(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name
            ) {
                this.id = id;
                this.name = name;
            }
        }
    }
}

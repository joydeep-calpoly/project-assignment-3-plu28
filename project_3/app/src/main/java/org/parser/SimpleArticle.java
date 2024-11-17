package org.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleArticle {

    private final String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private final LocalDateTime publishedAt;

    private final String title;
    private final String url;

    @JsonCreator
    private SimpleArticle(
        @JsonProperty("description") String description,
        @JsonProperty("publishedAt") LocalDateTime publishedAt,
        @JsonProperty("title") String title,
        @JsonProperty("url") String url
    ) {
        this.description = description;
        this.publishedAt = publishedAt;
        this.title = title;
        this.url = url;
    }

    String getTitle() {
        return this.title;
    }

    String getDescription() {
        return this.description;
    }

    LocalDateTime getPublishedAt() {
        return this.publishedAt;
    }

    String getUrl() {
        return this.url;
    }

    List<SimpleArticle> getArticles() {
        return new ArrayList<>(Arrays.asList(this));
    }

    /*
     * Returns string representation of a SimpleArticle
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
}

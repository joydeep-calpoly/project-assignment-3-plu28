package org.parser;

import java.util.List;
import java.util.logging.Logger;

/**
 * Parses the given JSON string and returns a list of parsed elements
 * @param jsonString the JSON string to parse
 * @return a list of parsed elements
 */
public abstract class Parser<T> {

    // Giving children a logger
    protected final Logger logger;

    protected Parser(Logger logger) {
        this.logger = logger;
    }

    public abstract void printArticles(FileFormat ff);

    public abstract void printArticles(URLFormat uf);
    // public abstract T getArticles(String jsonString);
}

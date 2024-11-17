package org.parser;

public abstract class SourceFormat {

    public abstract void accept(Parser p);
    public abstract String getString();

    private String source;

    public String getSource() {
        return this.source;
    }

    public SourceFormat(String source) {
        this.source = source;
    }
}

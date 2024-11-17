package org.parser;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileFormat extends SourceFormat {

    public FileFormat(String source) {
        super(source);
    }

    public void accept(Parser p) {
        p.printArticles(this);
    }

    public String getString() {
        String jsonString = "";
        try {
            byte bytes[] = Files.readAllBytes(Paths.get(super.getSource()));
            jsonString = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}

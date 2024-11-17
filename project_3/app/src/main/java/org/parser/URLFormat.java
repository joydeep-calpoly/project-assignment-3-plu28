package org.parser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuffer;

public class URLFormat extends SourceFormat {

    public URLFormat(String source) {
        super(source);
    }

    public void accept(Parser p) {
        p.printArticles(this);
    }

    public String getString() {
        StringBuffer jsonString = new StringBuffer();
        try {
            URL url = new URL(super.getSource());
            HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Reading response as a string
            BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonString.append(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString.toString();
    }

}

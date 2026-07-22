package com.lyh.linkhub.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class UrlMetadataService {

    public String fetchTitle(String urlString) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("</title>")) {
                    int start = line.indexOf("<title>");
                    int end = line.indexOf("</title>");
                    if (start != -1 && end != -1) {
                        return line.substring(start + 7, end).trim();
                    }
                }
                if (line.contains("<TITLE>")) {
                    int start = line.indexOf("<TITLE>");
                    int end = line.indexOf("</TITLE>");
                    if (start != -1 && end != -1) {
                        return line.substring(start + 7, end).trim();
                    }
                }
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {
            }
            if (conn != null) conn.disconnect();
        }
        return null;
    }
}

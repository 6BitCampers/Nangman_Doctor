package com._6bitcampers.nangman_doctor.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Service
public class NaverSearchService {

    private static final String CLIENT_ID = "DTPi8BJSW9e710hOS2P9";
    private static final String CLIENT_SECRET = "k7iHOjwkfK";

    public Map<String, Object> searchNews(String keyword, int start, int display) {
        String text;
        try {
            text = URLEncoder.encode(keyword, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&start=" + start + "&display=" + display;
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", CLIENT_ID);
        requestHeaders.put("X-Naver-Client-Secret", CLIENT_SECRET);

        String responseBody = get(apiURL, requestHeaders);
        return parseResponse(responseBody);
    }

    private String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private Map<String, Object> parseResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode itemsNode = rootNode.path("items");

            List<Map<String, String>> items = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                Map<String, String> item = new HashMap<>();
                item.put("title", itemNode.path("title").asText());
                item.put("originallink", itemNode.path("originallink").asText());
                item.put("description", itemNode.path("description").asText());
                item.put("pubDate", itemNode.path("pubDate").asText());
                items.add(item);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("items", items);
            result.put("total", rootNode.path("total").asInt());
            result.put("start", rootNode.path("start").asInt());
            result.put("display", rootNode.path("display").asInt());
            return result;
        } catch (IOException e) {
            throw new RuntimeException("API 응답 파싱 실패", e);
        }
    }
}

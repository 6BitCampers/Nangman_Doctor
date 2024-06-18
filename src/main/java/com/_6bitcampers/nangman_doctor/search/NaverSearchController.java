package com._6bitcampers.nangman_doctor.search;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class NaverSearchController {

    @GetMapping("/naversearch")
    public String getNaverSearchResults(Model model, @RequestParam(defaultValue = "1") int page) {
        String clientId = "DTPi8BJSW9e710hOS2P9";
        String clientSecret = "k7iHOjwkfK";
        String query = "성형";
        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + query + "&start=" + ((page - 1) * 10 + 1);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(apiURL, HttpMethod.GET, entity, Map.class);

        model.addAttribute("items", response.getBody().get("items"));
        model.addAttribute("currentPage", page);

        return "naversearch";
    }
}

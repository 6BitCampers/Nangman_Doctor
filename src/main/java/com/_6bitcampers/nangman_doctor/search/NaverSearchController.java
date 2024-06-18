package com._6bitcampers.nangman_doctor.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NaverSearchController {

    private final NaverSearchService naverSearchService;

    @GetMapping("/naversearch")
    public String searchNaver(@RequestParam(name = "query", defaultValue = "성형") String query,
                              @RequestParam(name = "page", defaultValue = "1") int page,
                              Model model) throws JsonProcessingException {
        List<NaverSearchItem> items = naverSearchService.search(query, page);
        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        return "naversearch";
    }
}

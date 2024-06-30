package com._6bitcampers.nangman_doctor.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    private SeleniumCrawlerService seleniumCrawlerService;

    @GetMapping("/hospital-url")
    public String getHospitalUrl(@RequestParam("hospitalName") String hospitalName, Model model) {
        String hospitalUrl = seleniumCrawlerService.getHospitalUrl(hospitalName);
        if (hospitalUrl == null || hospitalUrl.isEmpty()) {
            model.addAttribute("error", "Unable to find the hospital URL.");
            return "error";
        }
        return "redirect:" + hospitalUrl;
    }
}

package com._6bitcampers.nangman_doctor.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/search")
    public String showSearchPage() {
        return "searchfor";
    }

    @GetMapping("/results")
    public String showResultsPage(@RequestParam("keyword") String keyword, Model model) {
        List<HospitalDto> hospitals = hospitalService.searchHospitals(keyword);
        List<HospitalDto> topRatedHospitals = hospitalService.searchTopRatedHospitals(keyword);
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("topRatedHospitals", topRatedHospitals);
        return "results";
    }

    @GetMapping("/about-hospital/{hospitalId}")
    public String showAboutHospitalPage(@PathVariable("hospitalId") Long hospitalId, Model model) {
        HospitalDto hospital = hospitalService.findHospitalById(hospitalId);
        model.addAttribute("hospital", hospital);
        return "about-hospital";
    }
}

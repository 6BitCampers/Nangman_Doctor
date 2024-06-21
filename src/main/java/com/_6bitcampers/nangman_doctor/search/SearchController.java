package com._6bitcampers.nangman_doctor.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private EmployeeService employeeService;

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
        List<EmployeeDto> employees = (employeeService != null) ? employeeService.getEmployeesByInfoNo(hospitalId) : Collections.emptyList();

        if (employeeService == null) {
            EmployeeDto naEmployee = new EmployeeDto();
            naEmployee.setEmployee_name("N/A");
            naEmployee.setEmployee_field("N/A");
            naEmployee.setEmployee_gender("N/A");
            naEmployee.setEmployee_age("N/A");
            naEmployee.setEmployee_hp("N/A");
            employees.add(naEmployee);
        }

        model.addAttribute("employees", employees);
        model.addAttribute("hospital", hospital);
        System.out.println(hospitalId);
        return "about-hospital";
    }
}

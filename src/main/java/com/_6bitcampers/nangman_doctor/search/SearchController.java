package com._6bitcampers.nangman_doctor.search;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String showResultsPage(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        boolean isLoggedIn = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                isLoggedIn = true;
            }
        }

        List<HospitalDto> hospitals = hospitalService.searchHospitals(keyword, page, size);
        List<HospitalDto> topRatedHospitals = hospitalService.searchTopRatedHospitals(keyword, 1, 10); // Assuming top rated is only the top 10

        long totalHospitals = hospitalService.countHospitals(keyword); // Count the total hospitals matching the keyword
        int totalPages = (int) Math.ceil((double) totalHospitals / size);

        // Calculate the page range to display
        int startPage = Math.max(1, page - 5);
        int endPage = Math.min(totalPages, page + 4);
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("topRatedHospitals", topRatedHospitals);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("pageNumbers", pageNumbers);

        return "results";
    }

    @GetMapping("/about-hospital/{hospitalId}")
    public String showAboutHospitalPage(@PathVariable("hospitalId") Long hospitalId, Model model) {
        HospitalDto hospital = hospitalService.findHospitalById(hospitalId);
        List<EmployeeDto> employees = (employeeService != null) ? employeeService.getEmployeesByInfoNo(hospitalId) : Collections.emptyList();
        String holidayBuisenessHour=hospitalService.convertTimeFormat(hospital.getHoliday());
        model.addAttribute("holidayBuisenessHour", holidayBuisenessHour);

        boolean isLoggedIn = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                isLoggedIn = true;
            }
        }

        if (employeeService == null) {
            EmployeeDto naEmployee = new EmployeeDto();
            naEmployee.setEmployee_name("N/A");
            naEmployee.setEmployee_field("N/A");
            naEmployee.setEmployee_gender("N/A");
            naEmployee.setEmployee_age("N/A");
            naEmployee.setEmployee_hp("N/A");
            employees.add(naEmployee);
        }
        List<String> images = Arrays.asList("person_1.jpg", "person_2.jpg", "person_3.jpg", "person_4.jpg");
        List<String> businessTimeList=hospitalService.getReorderedList(hospitalId);

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("businessTimeList", businessTimeList);
        model.addAttribute("images", images);
        model.addAttribute("random", new Random());
        model.addAttribute("employees", employees);
        model.addAttribute("hospital", hospital);

        return "about-hospital";
    }
}

package com._6bitcampers.nangman_doctor;

import com._6bitcampers.nangman_doctor.search.*;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService.roleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class homeController {
    private final roleService roleService;
    private final EmployeeService employeeService;
    private final NaverSearchService naverSearchService;
    private final HospitalService hospitalService;

    @GetMapping("/")
    public String home(Model model) throws JsonProcessingException {
        //로그인 유저의 이메일 및 로그인 타입
        //CustomUserDetails userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //로그인 유저의 이메일 출력
        //String useremail = userDetails.getUsername();
        //로그인 유저의 로그인 타입 출력
        //String type = userDetails.getType();


        //권한 읽어오기(ex. ROLE_EMP)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();
        List<EmployeeDto> top10EmployeesByLikeCount = employeeService.getTop10EmployeesByLikeCount();
        Map<Long, String> hospitalNames = top10EmployeesByLikeCount.stream()
                .collect(Collectors.toMap(
                        EmployeeDto::getInfo_no,
                        employee -> {
                            String hospitalName = employeeService.getHospitalNameByInfoNo(employee.getInfo_no());
                            return hospitalName != null ? hospitalName : "N/A";
                        },
                        (existing, replacement) -> existing // Handle duplicate keys by keeping the existing value
                ));
        String query="성형";
        int page=1;
        List<NaverSearchItem> items = naverSearchService.search(query,page);
        List<HospitalDto> top= hospitalService.findTop();
        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        model.addAttribute("hospitalNames", hospitalNames);
        model.addAttribute("topEmployees", top10EmployeesByLikeCount);
        model.addAttribute("top",top);
        model.addAttribute("home","home");
        return roleService.checkRole(role);
    }
}

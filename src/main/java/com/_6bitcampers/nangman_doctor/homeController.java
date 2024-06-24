package com._6bitcampers.nangman_doctor;

import com._6bitcampers.nangman_doctor.search.EmployeeDto;
import com._6bitcampers.nangman_doctor.search.EmployeeService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService.roleService;
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

    @GetMapping("/")
    public String home(Model model) {
        //로그인 시 이메일 출력
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

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
        model.addAttribute("hospitalNames", hospitalNames);
        model.addAttribute("topEmployees", top10EmployeesByLikeCount);

        return roleService.checkRole(role);
    }
}

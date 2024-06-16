package com._6bitcampers.nangman_doctor.leegahyun.management.managementController;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementService.EmpService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.customOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmpController {

    @Autowired
    private EmpService EmpService;

    @GetMapping("/emp")
    public String getEmployees(Model model) {
        ;
        List<EmpDto> employees = EmpService.getEmployeeLikeCounts(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("employees", employees);
        return "emp";
    }

}
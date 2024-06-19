package com._6bitcampers.nangman_doctor.leegahyun.management.managementController;

import ch.qos.logback.core.net.SyslogOutputStream;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementService.EmpService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.customOAuth2User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmpController {

    @Autowired
    private EmpService EmpService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/emp")
    public String getEmployees(Model model) {
        List<EmpDto> employees = EmpService.getEmployeeLikeCounts(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("employees", employees);

        List<EmpDto> emplist = EmpService.getEmpList(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("emplist", emplist);

        List<ReservationDto> reservationDtoList=reservationService.selectReservationsByEmployeeEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("reservationDtoList", reservationDtoList);

        return "emp";
    }

    @PostMapping("/registerRole")
    public String registerRole(@RequestParam("role") String role, Model model) {
        // 예시: 사용자의 역할을 업데이트하는 경우
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerRole(email, role);

        // 선택적으로, 등록 후 다른 페이지로 리다이렉트할 수 있습니다.
        return "redirect:/emp";
    }

}
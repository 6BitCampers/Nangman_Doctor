package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Dto.HosInfoDto;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.search.HospitalDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation")
    public String showReservationPage(@RequestParam("info_no") int infoNo, Model model) {
        // Fetch the employee number and hospital info based on the info_no
        int employeeNo = reservationService.getEmployeeNoByInfoNo(infoNo);

        List<ReservationDto> HosResDto = reservationService.getResbyempno(employeeNo);

        HosInfoDto hosInfoDto = reservationService.getHosdto(infoNo);

        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = customOAuth2User.getEmail();
        String type= customOAuth2User.getType();

        // Get the user's name using the userId
        String name = reservationService.getUserNameByNo(reservationService.getUserNo(userId,type));




        model.addAttribute("HosResDto", HosResDto);
        model.addAttribute("name", name);
        model.addAttribute("HosInfoDto", hosInfoDto);
        model.addAttribute("info_no", infoNo);
        model.addAttribute("employeeNo", employeeNo);


        // Return the name of the Thymeleaf template to be rendered
        return "reservation";
    }



    @PostMapping("/reserveProc")
    public String reserveProc(@RequestParam("name") String reservationName,
                              @RequestParam("reservation_date") String reservationDate,
                              @RequestParam("reservation_time") String reservationTime,
                              @RequestParam("reservation_reason") String reservationReason,
                              @RequestParam("reservation_role") int reservationRole,
                              @RequestParam("employee_no") int employeeNo,
                              @RequestParam("info_no") int infoNo,
                              @RequestParam("reservation_face") int reservationFace,
                              @RequestParam("reservation_content") String reservationContent,
                              Model model) {

        // Get the userNo from the SecurityContext

        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String type=customOAuth2User.getType();


        int userNo = reservationService.getUserNo(userId,type);
        System.out.println(userNo);
        System.out.println(reservationName);

        ReservationDto reservationDto = ReservationDto.builder()
                .reservation_name(reservationName)
                .reservation_reason(reservationReason)
                .reservation_date(reservationDate)
                .reservation_role(reservationRole)
                .employee_no(employeeNo)
                .user_no(userNo)
                .reservation_time(reservationTime)
                .reservation_content(reservationContent)
                .reservation_face(reservationFace)
                .build();

        reservationService.saveReservation(reservationDto);

        return "userreservation";
    }
}

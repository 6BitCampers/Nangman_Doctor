package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
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
        int employeeNo = reservationService.getEmployeeNoByInfoNo(infoNo);
        model.addAttribute("info_no", infoNo);
        model.addAttribute("employeeNo", employeeNo);
        return "reservation";  // 원하는 HTML 페이지로 이동합니다.
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
                              Model model) {

        // Get the userNo from the SecurityContext
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        int userNo = reservationService.getUserNo(userEmail);
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
                .reservation_face(reservationFace)

                .build();

        reservationService.saveReservation(reservationDto);


        return "userreservation";
    }


}
package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation.html")
    public String showReservationPage(@RequestParam("info_no") int infoNo, Model model) {
        int employeeNo = reservationService.getEmployeeNoByInfoNo(infoNo);
        model.addAttribute("info_no", infoNo);
        model.addAttribute("employeeNo", employeeNo);
        return "reservation";
    }

    @PostMapping("/reserveProc")
    public String reserveProc(@RequestParam("name") String name,
                              @RequestParam("reservation_date") String reservationDate,
                              @RequestParam("reservation_time") String reservationTime,
                              @RequestParam("reservation_reason") String reservationReason,
                              @RequestParam("reservation_role") int reservationRole,
                              @RequestParam("employee_no") int employeeNo,
                              @RequestParam("info_no") int infoNo,
                              Model model) {

        // Get the userNo from the SecurityContext
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        int userNo = reservationService.getUserNo(userEmail);

        ReservationDto reservationDto = ReservationDto.builder()
                .name(name)
                .reservationReason(reservationReason)
                .reservationDate(reservationDate)
                .reservationRole(reservationRole)
                .employeeNo(employeeNo)
                .userNo(userNo)
                .reservationTime(reservationTime)
                .build();

        reservationService.saveReservation(reservationDto);

        return "redirect:/userreservation.html";
    }

    @GetMapping("/userreservation.html")
    public String showUserReservations(Model model) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        int userNo = reservationService.getUserNo(userEmail);
        List<Map<String, Object>> reservations = reservationService.getUserReservations(userNo);
        model.addAttribute("reservations", reservations);
        return "userreservation";
    }
}

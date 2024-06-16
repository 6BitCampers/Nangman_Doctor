package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation.html")
    public String showReservationPage() {
        return "reservation";
    }

    @PostMapping("/reserveProc")
    public String reserveProc(@RequestParam("name") String name,
                              @RequestParam("reservation_date") String reservationDate,
                              @RequestParam("reservation_time") String reservationTime,
                              @RequestParam("reservation_reason") String reservationReason,
                              @RequestParam("reservation_role") int reservationRole,
                              Model model) {

        ReservationDto reservationDto = ReservationDto.builder()
                .reservationReason(reservationReason)
                .reservationDate(reservationDate)
                .reservationRole(reservationRole)
                .employeeNo(3) // Default value
                .userNo(7)     // Default value
                .reservationTime(reservationTime)
                .build();

        reservationService.saveReservation(reservationDto);

        // Add reservationDto to model
        model.addAttribute("reservationDto", reservationDto);

        return "userreservation";
    }
}

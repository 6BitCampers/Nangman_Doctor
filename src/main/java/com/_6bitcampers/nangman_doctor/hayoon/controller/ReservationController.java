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

       // String ema= SecurityContextHolder.getContext().getAuthentication().getName();


        ReservationDto reservationDto = ReservationDto.builder()
                .name(name)
                .reservationReason(reservationReason)
                .reservationDate(reservationDate)
                .reservationRole(reservationRole)
                .employeeNo(3) // Default value
                .userNo(7)     // Default value
                .reservationTime(reservationTime)
                .build();

        reservationService.saveReservation(reservationDto);

        return "redirect:/userreservation.html";
    }

    @GetMapping("/userreservation.html")
    public String showUserReservations(Model model) {
        List<Map<String, Object>> reservations = reservationService.getUserReservations(7);
        model.addAttribute("reservations", reservations);
        return "userreservation";
    }
}

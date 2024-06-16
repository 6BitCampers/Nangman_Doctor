package com._6bitcampers.nangman_doctor.hayoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class reservationController {


    @GetMapping("/reservation.html")
    public String showReservationPage() {
        return "reservation";
    }


}
package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.hayoon.Service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/updateReservationStatus")
    public void updateReservationStatus(@RequestParam int reservationNo) {
        statusService.updateStatus(reservationNo);

    }

   /* @GetMapping("/getInfoNo")
    @ResponseBody
    public int getInfoNo(@RequestParam("reservationNo") int reservationNo) {
        return reservationService.getInfoNo(reservationNo);
    }*/


}

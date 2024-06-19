package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping("/updateReservationStatus")
    public void updateStatus(@RequestBody Map<String, Object> request) {
        int reservationNo = (int) request.get("reservationNo");
        int status = (int) request.get("status");
        statusService.updateStatus(reservationNo, status);
    }
}

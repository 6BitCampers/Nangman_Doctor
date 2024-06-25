package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.hayoon.Service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class StatusController {

    private final ReservationMapper reservationMapper;
    private final StatusService statusService;

    @PostMapping("/SendStatus")
    @ResponseBody
    public Map<String, Object> updateReservationStatus(@RequestParam int reservationNo, @RequestParam String isAccepted) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("reservationNo", reservationNo);
            response.put("isAccepted", isAccepted);

            if ("true".equals(isAccepted)) {
                statusService.updateStatus(reservationNo);

                if (statusService.getStatus(reservationNo) == 2) {
                    statusService.updateRoom(UUID.randomUUID().toString(), reservationNo);

                    String employeeEmail = reservationMapper.getEmployeemailByNo(reservationMapper.getEmployeeNo(reservationNo));
                    String userEmail = reservationMapper.getUsermailByNo(reservationMapper.getUserNoByres(reservationNo));

                    ReservationDto reservationDto = reservationMapper.getResdto(reservationNo);
                    statusService.sendReservationRequestEmail(userEmail, reservationDto);
                    statusService.sendReservationRequestEmail(employeeEmail, reservationDto);
                }
            } else {
                statusService.deleteByReservationNo(reservationNo);
            }
            response.put("status", "success");
        } catch (Exception e) {
            System.err.println("Error updating reservation status: " + e.getMessage());
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }

    @GetMapping("/edit")
    public String editReservation(@RequestParam int reservationNo,Model model){
        int infoNo=statusService.getInfoNoByReservationNo(reservationNo);
        statusService.deleteByReservationNo(reservationNo);

        model.addAttribute(infoNo);
        return "redirect:/reservation?info_no=" + infoNo;


    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteReservation(@RequestParam int reservationNo) {
        statusService.deleteByReservationNo(reservationNo);
        return "success";
    }

}

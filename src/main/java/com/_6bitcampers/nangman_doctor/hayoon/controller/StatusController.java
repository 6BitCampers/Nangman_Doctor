package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.hayoon.Service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class StatusController {
    private  ReservationMapper reservationMapper;
    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;


    @Autowired
    private StatusService statusService;

    @Autowired
    private ReservationService reservationService;


    @PostMapping("/SendStatus")
    public void updateReservationStatus(@RequestParam int reservationNo,@RequestParam String isAccepted, Model model) {

        System.out.println(reservationNo);
        System.out.println(isAccepted);
        model.addAttribute("reservationNo", reservationNo);
        model.addAttribute("isAccepted", isAccepted);
        if(isAccepted.equals("true")) {
            statusService.updateStatus(reservationNo);
            if(statusService.getStatus(reservationNo)==2){
                statusService.updateRoom(UUID.randomUUID().toString(),reservationNo);


                // Get email addresses
                String employeeEmail=reservationMapper.getEmployeemailByNo(reservationMapper.getEmployeeNo(reservationNo));
                String userEmail=reservationMapper.getUsermailByNo(reservationMapper.getUserNoByres(reservationNo));



                // 예약 보내기
                ReservationDto reservationDto=reservationMapper.getResdto(reservationNo);
                statusService.sendReservationRequestEmail(userEmail, reservationDto);
                statusService.sendReservationRequestEmail(employeeEmail, reservationDto);

            }
        }
        else{
            statusService.deleteByReservationNo(reservationNo);
        }


    }
    @GetMapping("/edit")
    public String editReservation(@RequestParam int reservationNo,Model model){
        int infoNo=statusService.getInfoNoByReservationNo(reservationNo);
        statusService.deleteByReservationNo(reservationNo);

        model.addAttribute(infoNo);
        return "redirect:/reservation?info_no=" + infoNo;


    }

    @PostMapping("/delete")
    public void deleteReservation(@RequestParam int reservationNo,Model model){
        model.addAttribute("reservationNo", reservationNo);
        statusService.deleteByReservationNo(reservationNo);




    }




}

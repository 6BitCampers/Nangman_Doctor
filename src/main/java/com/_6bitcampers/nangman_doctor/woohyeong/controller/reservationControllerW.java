package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.woohyeong.Service.reservationServiceW;
import com._6bitcampers.nangman_doctor.woohyeong.dto.ReservationDTOW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

@Controller
public class reservationControllerW {
    @Autowired
    private reservationServiceW reservationService;

    @GetMapping("/reservation/insertsurvey")
    public String insertSurvey(@ModelAttribute ReservationDTOW dto){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        int employee_no = reservationService.getEmployeeNo(id);
        dto.setEmployee_no(employee_no);
        reservationService.insertSurvey(dto);
        return "";
    }

    @GetMapping("/reservation/updatesurvey")
    public String updateSurvey(int user_no){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        int employee_no = reservationService.getEmployeeNo(id);
        Map<String, Object> map = new HashMap<>();
        map.put("employee_no", employee_no);
        map.put("user_no", user_no);
        reservationService.updateSurvey(map);
        return "";
    }
}

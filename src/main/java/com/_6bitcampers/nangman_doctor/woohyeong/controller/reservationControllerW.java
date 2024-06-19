package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
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

    //유저가 입력해서 입력한 폼을 유지한 채 의사한테 승인요청
    @GetMapping("/reservation/insertsurvey")
    public String insertSurvey(@ModelAttribute ReservationDTOW dto){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = customOAuth2User.getEmail();
        reservationService.insertSurvey(dto);
        return "";
    }

    //승인요청된 걸 보고 의사가 들어가면 그 내용을 볼 수 있게 함
    //의사가 수정 불가능
    //의사가 승인 누르면 employee_no 추가하는 업데이트
    //취소 누르면 해당 예약 신청 거절(삭제)
    @GetMapping("/reservation/updatesurvey")
    public String updateSurvey(int user_no){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = customOAuth2User.getEmail();
        int employee_no = reservationService.getEmployeeNo(id);
        Map<String, Object> map = new HashMap<>();
        map.put("employee_no", employee_no);
        map.put("user_no", user_no);
        reservationService.updateSurvey(map);
        return "";
    }

    @GetMapping("/reservation/deletesurvey")
    public String deleteSurvey(int reservation_no){
        reservationService.deleteSurvey(reservation_no);
        return "";
    }
}

package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.woohyeong.Service.mypageService;
import com._6bitcampers.nangman_doctor.woohyeong.Service.reservationServiceW;
import com._6bitcampers.nangman_doctor.woohyeong.dto.EmployeeDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.ReceiptDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class mypageController {
    @Autowired
    private mypageService mypageService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private reservationServiceW reservationServiceW;

    @GetMapping("/mypage")
    public String mypage(Model model) {
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = customOAuth2User.getEmail();
        int userNo= reservationService.getUserNo(id);
        int status = reservationServiceW.getReservationStatus(userNo);
        try {
            UserDTO udto = mypageService.getUser(id);
            if (udto != null) {
                List<ReceiptDTO> dto = mypageService.getReceipt(userNo);
                model.addAttribute("udto", udto);
                model.addAttribute("status", status);
                model.addAttribute("dto", dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            EmployeeDTO edto = mypageService.getEmployee(id);
            if (edto != null) {
                model.addAttribute("edto", edto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            List<Map<String, Object>> reservationList = reservationService.getUserReservations(userNo);
            if (reservationList != null && !reservationList.isEmpty()) {
                model.addAttribute("reservationList", reservationList);
                System.out.println(reservationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "mypage"; // 템플릿 이름을 반환합니다. "mypage.html" 템플릿이 호출됩니다.
    }

    @GetMapping("/mypage/updateform")
    public String updateform(Model model) {
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = customOAuth2User.getEmail();
        int userNo = reservationService.getUserNo(id);
        int status = reservationServiceW.getReservationStatus(userNo);
        try {
            UserDTO udto = mypageService.getUser(id);
            System.out.println("유디티오: " + udto);
            if (udto != null) {
                List<ReceiptDTO> dto = mypageService.getReceipt(userNo);
                System.out.println("리스트 디ㅇ티오: " + dto);
                model.addAttribute("udto", udto);
                model.addAttribute("status", status);
                model.addAttribute("dto", dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            EmployeeDTO edto = mypageService.getEmployee(id);
            if (edto != null) {
                model.addAttribute("edto", edto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            List<Map<String, Object>> reservationList = reservationService.getUserReservations(userNo);
            if (reservationList != null && !reservationList.isEmpty()) {
                model.addAttribute("reservationList", reservationList);
                System.out.println(reservationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "mypageform";
    }

    @GetMapping("/mypage/update")
    public String update(Model model, String user_name, String user_email, String user_nickname, String user_hp, int user_no) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", user_name);
        map.put("user_email", user_email);
        map.put("user_nickname", user_nickname);
        map.put("user_hp", user_hp);
        map.put("user_no", user_no);
        mypageService.updateUser(map);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

}

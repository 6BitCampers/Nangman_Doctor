package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.woohyeong.Service.mypageService;
import com._6bitcampers.nangman_doctor.woohyeong.dto.EmployeeDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mypageController {
    @Autowired
    private mypageService mypageService;

    @GetMapping("/mypage")
    public String mypage(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            UserDTO udto = mypageService.getUser(id);
            if (udto != null) {
                model.addAttribute("udto", udto);
                System.out.println(udto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            EmployeeDTO edto = mypageService.getEmployee(id);
            if (edto != null) {
                model.addAttribute("edto", edto);
                System.out.println(edto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "mypage"; // 템플릿 이름을 반환합니다. "mypage.html" 템플릿이 호출됩니다.
    }

}

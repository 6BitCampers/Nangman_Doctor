package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.woohyeong.Service.mypageService;
import com._6bitcampers.nangman_doctor.woohyeong.Service.reservationServiceW;
import com._6bitcampers.nangman_doctor.woohyeong.dto.EmployeeDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.ReceiptDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Date;

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
        String type = customOAuth2User.getType();
        int userNo= reservationService.getUserNo(id,type);
        int status = reservationServiceW.getReservationStatus(userNo);
        try {
            UserDTO udto = mypageService.getUser(id, type);
            if (udto != null) {
                Map<String, Object> map = new HashMap<>();
//                map.put("user_no", userNo);
//                map.put("reservation_no", )
//                System.out.println(dto);
                model.addAttribute("udto", udto);
                model.addAttribute("status", status);
//                model.addAttribute("dto", dto);
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
            int completed = 0;
            int pending = 0;
            int reserved = 0;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if (reservationList != null && !reservationList.isEmpty()) {
                model.addAttribute("reservationList", reservationList);

                for (Map<String, Object> map : reservationList) {
                    Date reservationDateSql = (Date) map.get("reservation_date");
                    Integer reservationStatus = (Integer) map.get("reservation_status");

                    LocalDateTime reservationDate = reservationDateSql.toLocalDate().atStartOfDay();
                    if (reservationDate.isBefore(now)) {
                        completed++;
                        map.put("status","completed");
                    } else if (reservationStatus != null && reservationStatus == 1) {
                        pending++;
                        map.put("status","pending");
                    } else {
                        reserved++;
                        map.put("status","reserved");
                    }
                    model.addAttribute("completed",completed);
                    model.addAttribute("reserved",reserved);
                    model.addAttribute("pending",pending);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("type",type);
        //리뷰 작성시 필요한 값 보내기
        model.addAttribute("userNo",userNo);

        return "mypage"; // 템플릿 이름을 반환합니다. "mypage.html" 템플릿이 호출됩니다.
    }

    @GetMapping("/getPaymentInfo")
    public ResponseEntity<List<ReceiptDTO>> getPaymentInfo(@RequestParam("reservationNo") Long reservationNo) {

        System.out.println("getpayment왔음");
        // 현재 인증된 사용자 정보 가져오기
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = customOAuth2User.getEmail();
        String type = customOAuth2User.getType();
        // 사용자 번호 가져오기
        int userNo = reservationService.getUserNo(id,type);

        // 예약 번호와 사용자 번호를 맵에 저장
        Map<String, Object> map = new HashMap<>();
        map.put("reservation_no", reservationNo);
        map.put("user_no", userNo);

        System.out.println("map " + map);
        // 결제 정보 가져오기
        List<ReceiptDTO> dto = mypageService.getReceipt(map);

        System.out.println("리스트" + dto);

        // 결제 정보 응답으로 반환
        return ResponseEntity.ok(dto);
    }


//    @GetMapping("/mypage/updateform")
//    public String updateform(Model model) {
//        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String id = customOAuth2User.getEmail();
//        int userNo = reservationService.getUserNo(id);
//        int status = reservationServiceW.getReservationStatus(userNo);
//
//        try {
//            UserDTO udto = mypageService.getUser(id);
//            System.out.println("유디티오: " + udto);
//            if (udto != null) {
//                List<ReceiptDTO> dto = mypageService.getReceipt(userNo);
//                System.out.println("리스트 디ㅇ티오: " + dto);
//                model.addAttribute("udto", udto);
//                model.addAttribute("status", status);
//                model.addAttribute("dto", dto);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            EmployeeDTO edto = mypageService.getEmployee(id);
//            if (edto != null) {
//                model.addAttribute("edto", edto);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            List<Map<String, Object>> reservationList = reservationService.getUserReservations(userNo);
//            if (reservationList != null && !reservationList.isEmpty()) {
//                model.addAttribute("reservationList", reservationList);
//                System.out.println(reservationList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "mypageform";
//    }

    @GetMapping("/mypage/update")
    public String update(@RequestParam String user_email,
                         @RequestParam String user_nickname,
                         @RequestParam String user_hp,
                         @RequestParam String user_interest,
                         @RequestParam String user_addr,
                         @RequestParam int user_no) {
        Map<String, Object> map = new HashMap<>();

        map.put("user_email", user_email);
        map.put("user_nickname", user_nickname);
        map.put("user_addr1",user_addr);
        map.put("user_hp", user_hp);
        map.put("user_interest",user_interest);
        map.put("user_no", user_no);
        mypageService.updateUser(map);

        return "redirect:/mypage";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @ResponseBody
    @GetMapping("/getHospitalName")
    public Map<String,String> getHospitalName(@RequestParam int info_no) {
        Map<String,String> map=new HashMap<>();
        String info_name=mypageService.getInfoName(info_no);
        map.put("info_name", info_name);
        return map;
    }

}

package com._6bitcampers.nangman_doctor.leegahyun.management.managementController;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewAndReceiptService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementService.EmpService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class EmpController {

    @Autowired
    private ReviewAndReceiptService reviewAndReceiptService;
    @Autowired
    private EmpService EmpService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam("email") String email, @RequestParam("file") MultipartFile file) {
        try {
            EmpService.registerPhoto(email, file);
            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo");
        }
    }

    @PostMapping("/registerPhoto")
    public String registerPhoto(@RequestParam("photo") MultipartFile photo, Model model) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            EmpService.registerPhoto(email, photo);
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리 로직 추가
        }
        return "redirect:/emp";
    }
    @GetMapping("/emp")
    public String getEmployees(Model model) {

        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String userType= customOAuth2User.getType();
        String role= customOAuth2User.getRole();

        List<EmpDto> emp_hos = EmpService.getEmployeeLikeCounts(userId);
        model.addAttribute("emp_hos",emp_hos);

        //병원장이면 emp 전체 no 받아서 review 갖고오기
        if(role.equals("ROLE_MANAGER")){
            List<EmpDto> employees = EmpService.getEmployeeLikeCounts(userId);
            model.addAttribute("employees", employees);

            List<EmpDto> emplist = EmpService.getEmpList(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("emplist", emplist);

            //해당 병원에 작성된 리뷰보드 연결
            List<Integer> empNos= new ArrayList<>();
            for(EmpDto empDto : emplist){
                empNos.add(empDto.getEmployee_no());
            }

            Map<Integer, List<ReviewDto>> employeeReviews = new HashMap<>();
            for (int empNo : empNos) {
                List<ReviewDto> reviewList = reviewAndReceiptService.getReviewByEmployeeNo(empNo);
                employeeReviews.put(empNo, reviewList);
            }

            List<ReviewDto> list=new ArrayList<>();
            for (List<ReviewDto> reviewList : employeeReviews.values()) {
                list.addAll(reviewList);
            }

            Map<Integer, userEntity> userMap = new HashMap<>();

            for (ReviewDto dto : list) {
                var user_no = dto.getUser_no();
                userEntity userDto = reviewAndReceiptService.getUserInfoByNum(user_no);
                userMap.put(user_no, userDto);

                //리뷰에 출력할 의사 이름 넣어주기
                var employee_name=reviewAndReceiptService.getEmployeeName(dto.getEmployee_no());
                dto.setEmployee_name(employee_name);
            }

            model.addAttribute("list", list);
            model.addAttribute("userMap", userMap);

            int totalNum= reviewAndReceiptService.getAllReviewsCount();
            model.addAttribute("totalNum", totalNum);
            model.addAttribute("currentpage", 1);
        }//일반 유저는 직원페이지 자체를 못들어오니까 그냥 else로 할게요
        else {
            //한개만 보내기
            EmpDto empDto=EmpService.getEmployeeByEmail(userId,userType);
            List<EmpDto> employees=new ArrayList<>();
            List<EmpDto> emplist = EmpService.getEmpList(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("emplist", emplist);
            employees.add(empDto);

            model.addAttribute("employees", employees);
            model.addAttribute("emplist", emplist);

            int empNo=empDto.getEmployee_no();

            List<ReviewDto> list = reviewAndReceiptService.getReviewByEmployeeNo(empNo);
            Map<Integer, userEntity> userMap = new HashMap<>();

            for (ReviewDto dto : list) {
                var user_no = dto.getUser_no();
                userEntity userDto = reviewAndReceiptService.getUserInfoByNum(user_no);
                userMap.put(user_no, userDto);

                //리뷰에 출력할 의사 이름 넣어주기
                var employee_name=reviewAndReceiptService.getEmployeeName(dto.getEmployee_no());
                dto.setEmployee_name(employee_name);
            }
            model.addAttribute("list", list);
            model.addAttribute("userMap", userMap);

            int totalNum= reviewAndReceiptService.getAllReviewsCount();
            model.addAttribute("totalNum", totalNum);
            model.addAttribute("currentpage", 1);
        }

        List<ReservationDto> reservations = reservationService.getReservationsByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("reservations", reservations);

        List<EmpDto> hospital_list=EmpService.getAllHospitalNames();
        model.addAttribute("hospital_list", hospital_list);


        return "emp";
    }

    @PostMapping("/registerRole")
    public String registerRole(@RequestParam("role") String role,String email,Model model) {

        EmpService.registerRole(email, role);

        return "redirect:/emp";
    }


    @PostMapping("/registerName")
    public String registerName(@RequestParam("hname") String hname, Model model) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerName(email, hname);

        return "redirect:/emp";
    }

    @PostMapping("/registerDescription")
    public String registerDescription(@RequestParam("desciption") String description, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerDescription(email, description);

        return "redirect:/emp";
    }

    @PostMapping("/registerAddress")
    public String registerAddress(@RequestParam("address") String address, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerAddress(email, address);

        return "redirect:/emp";
    }
    @PostMapping("/registerPlus")
    public String registerPlus(@RequestParam("plus") String plus, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerPlus(email, plus);

        return "redirect:/emp";
    }
    @PostMapping("/registerHp")
    public String registerHp(@RequestParam("hp") String hp, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerHp(email, hp);

        return "redirect:/emp";
    }




}
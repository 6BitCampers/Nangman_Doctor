package com._6bitcampers.nangman_doctor.leegahyun.management.managementController;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementService.EmpService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;

import ch.qos.logback.core.net.SyslogOutputStream;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementService.EmpService;

import org.apache.ibatis.annotations.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmpController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private EmpService EmpService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/emp")
    public String getEmployees(Model model) {

        List<EmpDto> employees = EmpService.getEmployeeLikeCounts(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("employees", employees);

        List<EmpDto> emplist = EmpService.getEmpList(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("emplist", emplist);


        //리뷰보드 연결
        int perPage=10;
        int startnum=(1-1)*perPage;

        List<ReviewDto> list= reviewService.getPagenationedReviews(startnum, perPage);

        model.addAttribute("list", list);
        Map<Integer, userEntity> userMap = new HashMap<>();

        for (ReviewDto dto : list) {
            var user_no = dto.getUser_no();
            userEntity userDto = reviewService.getUserInfo(user_no);
            userMap.put(user_no, userDto);
        }

        model.addAttribute("userMap", userMap);

        int totalNum=reviewService.getAllReviewsCount();
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("currentpage", 1);


        List<ReservationDto> reservations = reservationService.getReservationsByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("reservations", reservations);
        System.out.println(reservations);


        return "emp";
    }

    @PostMapping("/registerRole")
    public String registerRole(@RequestParam("role") String role, Model model) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerRole(email, role);

        return "redirect:/emp";
    }

    public String registerPhoto(@RequestParam("photo") String photo, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerRole(email, photo);

        return "redirect:/emp";
    }
    public String registerDescription(@RequestParam("desciption") String description, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerRole(email, description);

        return "redirect:/emp";
    }

    public String registerAddress(@RequestParam("address") String address, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerRole(email, address);

        return "redirect:/emp";
    }
    public String registerPlus(@RequestParam("plus") String plus, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerRole(email, plus);

        return "redirect:/emp";
    }
    public String registerHp(@RequestParam("hp") String hp, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EmpService.registerRole(email, hp);

        return "redirect:/emp";
    }




}
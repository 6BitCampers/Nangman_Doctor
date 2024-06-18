package com._6bitcampers.nangman_doctor.baedongwoo.controller.hospital;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.employeeEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HospitalDetailController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviewboard/detail")
    public String detail(
            HttpServletRequest request,
            HttpSession session,
            @RequestParam int review_no,
            @RequestParam(required = false) String userId,
            Model model) {
        //회원일 경우에는 이름으로 식별, 비회원일 경우에는 sessionId로 식별
        String identifier = (userId != null && !userId.equals("anonymousUser")) ? userId : request.getSession().getId();

        //session에 해당 아이디가 없으면 조회수 증가
        String isVisited = (String) request.getSession().getAttribute(identifier);

        if (isVisited == null) {
            session.setAttribute(identifier, "visited");
            reviewService.updateViewcount(review_no);
        }


        ReviewDto dto = reviewService.getReviewBySeq(review_no);
        int user_no=dto.getUser_no();
        int employee_no=dto.getEmployee_no();

        userEntity userDto = reviewService.getUserInfo(user_no);
        String user_name=userDto.getUser_name();
        int hospital_no=reviewService.getHospitalNo(employee_no);
        String hospital_name=reviewService.getHospitalName(hospital_no);


        model.addAttribute("dto", dto);
        model.addAttribute("user_name", user_name);
        model.addAttribute("hospital_name", hospital_name);
        model.addAttribute("userDto",userDto);
        model.addAttribute("hospital_no",hospital_no);
        model.addAttribute("review_no",review_no);

        return "reviewdetail";
    }

    @PostMapping("/reviewboard/update")
    public String updateReview(
            @RequestParam String review_title,
            @RequestParam int review_no,
            @RequestParam String review_content,
            @RequestParam int review_likecount
    ){
        Map<String,Object>map=new HashMap<>();
        map.put("review_title",review_title);
        map.put("review_no",review_no);
        map.put("review_content",review_content);
        map.put("review_likecount",review_likecount);
        reviewService.updateReview(map);

        return "redirect:/reviewboard/detail";
    }

    @ResponseBody
    @GetMapping("/reviewboard/delete")
    public String deleteReview(
            @RequestParam int review_no
    ){
        reviewService.deleteReview(review_no);
        return "{}";
    }
}

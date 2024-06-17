package com._6bitcampers.nangman_doctor.baedongwoo.controller.hospital;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HospitalDetailController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/hospital/detail")
    public String detail(
            HttpServletRequest request,
            HttpSession session,
            @RequestParam int review_no,
            @RequestParam(required = false) String user_name,
            Model model) {
        //회원일 경우에는 이름으로 식별, 비회원일 경우에는 sessionId로 식별
        String identifier = user_name!=null?user_name:request.getSession().getId();

        //session에 해당 아이디가 없으면 조회수 증가
        String isVisited = (String) request.getSession().getAttribute(identifier);

        if (isVisited == null) {
            session.setAttribute(identifier, "visited");
            reviewService.updateViewcount(review_no);
        }

        ReviewDto dto = reviewService.getReviewBySeq(review_no);

        model.addAttribute("dto", dto);

        return "hospitaldetail";
    }
}

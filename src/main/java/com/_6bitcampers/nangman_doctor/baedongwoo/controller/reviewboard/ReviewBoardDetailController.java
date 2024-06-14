package com._6bitcampers.nangman_doctor.baedongwoo.controller.reviewboard;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewBoardDetailController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviewboard/detail")
    public String detail(
            @RequestParam int review_no,
            Model model) {
        ReviewDto dto = reviewService.getReviewBySeq(review_no);
        model.addAttribute("dto", dto);

        return "reviewdetail";
    }
}

package com._6bitcampers.nangman_doctor.baedongwoo.controller.reviewboard;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewBoardListController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviewboard")
    public String reviewBoard(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            Model model) {
        int perPage=10;
        int startnum=(page-1)*perPage;


        List<ReviewDto> list= reviewService.getPagenationedReviews(startnum, perPage);
        model.addAttribute("list", list);

        int totalNum=reviewService.getAllReviewsCount();
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("currentpage", page);

        return "reviewboard";
    }
}

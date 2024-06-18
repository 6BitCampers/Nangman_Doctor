package com._6bitcampers.nangman_doctor.baedongwoo.controller.reviewboard;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, userEntity> userMap = new HashMap<>();

        for (ReviewDto dto : list) {
            var user_no = dto.getUser_no();
            userEntity userDto = reviewService.getUserInfo(user_no);
            userMap.put(user_no, userDto);
        }

        model.addAttribute("userMap", userMap);

        int totalNum=reviewService.getAllReviewsCount();
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("currentpage", page);

        return "reviewboard";
    }
}

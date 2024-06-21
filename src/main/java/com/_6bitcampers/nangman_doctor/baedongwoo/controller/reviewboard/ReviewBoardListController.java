package com._6bitcampers.nangman_doctor.baedongwoo.controller.reviewboard;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import com._6bitcampers.nangman_doctor.search.HospitalDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        int totalNum=reviewService.getAllReviewsCount();
        int perPage=10;

        if(page<=0){
            page=1;
        } else if(page >= Math.ceil((double)totalNum / perPage)) {
            page = (int)Math.ceil((double)totalNum / perPage);
        }

        int startnum=(page-1)*perPage;


        List<ReviewDto> list= reviewService.getPagenationedReviews(startnum, perPage);

        model.addAttribute("list", list);
        Map<Integer, String> hospitalNameMap=new HashMap<>();
        Map<Integer, userEntity> userMap = new HashMap<>();

        for (ReviewDto dto : list) {
            var user_no = dto.getUser_no();
            var employee_no=dto.getEmployee_no();
            userEntity userDto = reviewService.getUserInfoByNum(user_no);
            userMap.put(user_no, userDto);

            var info_no=reviewService.getHospitalNo(employee_no);
            var info_name=reviewService.getHospitalName(info_no);
            hospitalNameMap.put(user_no,info_name);
        }

        model.addAttribute("hospitalDtoMap", hospitalNameMap);
        model.addAttribute("userMap", userMap);
        model.addAttribute("perPage", perPage);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("startnum", startnum);
        model.addAttribute("currentpage", page);

        return "reviewboard";
    }
}

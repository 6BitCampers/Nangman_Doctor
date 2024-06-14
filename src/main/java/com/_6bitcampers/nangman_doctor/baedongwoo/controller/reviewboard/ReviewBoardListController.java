package com._6bitcampers.nangman_doctor.baedongwoo.controller.reviewboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewBoardListController {
    @GetMapping("/reviewboard")
    public String reviewBoard(Model model) {
        return "reviewboard";
    }
}

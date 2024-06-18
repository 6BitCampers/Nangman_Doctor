package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.controller;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto.pagingInfo;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto.qnaBoard;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.service.qnaListService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class QnAController {
    private static final Logger log = LoggerFactory.getLogger(QnAController.class);
    final qnaListService service;

    @GetMapping("/qna")
    public String list(
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "5") int pageSize,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model) {

        pagingInfo<qnaBoard> info = service.getBoards(pageIndex,pageSize);
        model.addAttribute("boards",info);
        model.addAttribute("startpage",info.getPageIndex()/info.getPageSize()*info.getPageSize()+1);
        model.addAttribute("endpage", Math.min(info.getPageIndex() / info.getPageSize() * info.getPageSize() + 1 + info.getPageSize(), info.getTotalPage()));
        model.addAttribute("username",customUserDetails==null?"":customUserDetails.getNickname());

        return "qna";
    }
}

package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.controller;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto.pagingInfo;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto.qnaBoard;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.service.qnaListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class QnAController {
    final qnaListService service;

    @GetMapping("/qna")
    public String list(
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "5") int pageSize,
            Model model) {

        pagingInfo<qnaBoard> info = service.getBoards(pageIndex,pageSize);
        model.addAttribute("boards",info);
        model.addAttribute("startpage",info.getPageIndex()/info.getPageSize()*info.getPageSize()+1);
        model.addAttribute("endpage", Math.min(info.getPageIndex() / info.getPageSize() * info.getPageSize() + 1 + info.getPageSize(), info.getTotalPage()));

        return "qna";
    }
}

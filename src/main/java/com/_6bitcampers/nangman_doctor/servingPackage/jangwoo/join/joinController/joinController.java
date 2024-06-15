package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinController;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinService.joinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class joinController {
    final private joinService service;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(joinRequestDto dto) {
        service.joinService(dto);
        return "redirect:/login";
    }
}

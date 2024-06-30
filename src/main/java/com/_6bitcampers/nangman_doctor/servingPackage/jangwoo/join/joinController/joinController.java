package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinController;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinService.addInfoService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinService.joinService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class joinController {
    final private joinService service;
    final private addInfoService addinfoService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(joinRequestDto dto) {
        service.joinService(dto);
        return "redirect:/login";
    }

    @PostMapping("/addinfoProc")
    public String addProc(joinRequestDto dto) {
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        addinfoService.addinfo(customOAuth2User.getType(),customOAuth2User.getEmail(),dto);
        return "redirect:/logout";
    }

    @GetMapping("/vaild/{uuid}")
    public String pwreset(@PathVariable("uuid")String uuid){

        return service.vaildEmail(uuid)?"redirect:/":"paymentError";
    }
}

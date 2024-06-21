package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginController;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService.userResetPwService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class loginController {
    private final userResetPwService pwService;

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value="error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }

    @GetMapping("/addinfo")
    public String buf() {
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        System.out.println(customOAuth2User.getType());
        return "addinfotem/"+customOAuth2User.getType();
    }

    @GetMapping("/pwreset/{uuid}")
    public String pwreset(@PathVariable("uuid")String uuid){

        return pwService.checkurl(uuid)?"resetpw":"paymentError";
    }

    @PostMapping("/pwreset/{uuid}")
    public String pwresetComplete(@PathVariable("uuid") String uuid,
                                  String password) {

        pwService.resetPw(uuid,password);
        return "redirect:/login";
    }
}

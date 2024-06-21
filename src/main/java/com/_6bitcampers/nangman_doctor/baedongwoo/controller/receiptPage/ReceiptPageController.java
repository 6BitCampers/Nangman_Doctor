package com._6bitcampers.nangman_doctor.baedongwoo.controller.receiptPage;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.PaymentService;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReceiptPageController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/receiptView")
    public String recieptPage(@RequestParam int receipt_no,
                              Model model) {
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_type=customOAuth2User.getType();

        userEntity userEntity= reviewService.getUserInfo(userId, user_type);
        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);
        int infoNo = receiptDto.getInfo_no();
        EmpDto empDto=paymentService.gethospitalInfo(infoNo);

        model.addAttribute("empDto",empDto);
        model.addAttribute("receiptDto", receiptDto);
        model.addAttribute("userEntity", userEntity);

        //랜덤한 숫자들 넣어주기
        int randomNum= (int) (Math.random() * 9000 + 1000);
        model.addAttribute("randomNum",randomNum);

        char randomUpperCaseLetter = (char)('A' + (int)(Math.random() * 26));
        int randomDiseaseNum=(int)(Math.random() * 9000 + 1000);
        String randomDiseaseWord = String.valueOf(randomUpperCaseLetter) + " " + randomDiseaseNum;
        model.addAttribute("randomDiseaseWord",randomDiseaseWord);

        char randomUpperCaseLetter2 = (char)('A' + (int)(Math.random() * 26));
        int randomDiseaseNum2=(int)(Math.random() * 9000 + 1000);
        String randomDiseaseWord2 = String.valueOf(randomUpperCaseLetter2) +" " + randomDiseaseNum2;
        model.addAttribute("randomDiseaseWord2",randomDiseaseWord2);
        model.addAttribute("shouldBeVisible",true);

        return "ReceiptAndReviewWrite";
    }
}

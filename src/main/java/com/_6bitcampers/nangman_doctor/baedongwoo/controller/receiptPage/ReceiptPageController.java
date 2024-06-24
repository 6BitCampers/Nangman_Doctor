package com._6bitcampers.nangman_doctor.baedongwoo.controller.receiptPage;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PillDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.PaymentService;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

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
        int reservation_no=receiptDto.getReservation_no();
        ReservationDto reservationDto =paymentService.getReservation(reservation_no);
        int employee_no=reservationDto.getEmployee_no();
        String empName= reviewService.getEmployeeName(employee_no);

        EmpDto empDto=paymentService.gethospitalInfo(infoNo);

        PillDto soyumDto =reviewService.getPillInfo("소염제");
        PillDto jintongDto=reviewService.getPillInfo("진통제");

        model.addAttribute("soyumDto",soyumDto);
        model.addAttribute("jintonDto",jintongDto);
        model.addAttribute("empName",empName);
        model.addAttribute("empDto",empDto);
        model.addAttribute("receiptDto", receiptDto);
        model.addAttribute("userEntity", userEntity);

        //출력할 랜덤한 숫자들 넣어주기
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

        //age 갖고와서 주민등록번호 랜덤 생성하기 (null이면 여성, 20대로 설정)
        int userAge = userEntity.getUser_age() == null ? 2 : Integer.parseInt(userEntity.getUser_age().substring(0, 1));
        String userGender = userEntity.getUser_gender() == null ? "F" : userEntity.getUser_gender();

        int firstRegiNum = switch (userAge) {
            case 1 -> 0;
            case 2 -> 9;
            case 3 -> 8;
            case 4 -> 7;
            case 5 -> 6;
            case 6 -> 5;
            default -> 4;
        };

        Random random = new Random();
        int randomDigit = random.nextInt(10);
        int randomMonth = random.nextInt(12) + 1; // 1부터 12까지의 난수 생성
        int randomDay = random.nextInt(31) + 1; // 1부터 31까지의 난수 생성

        String frontRegiNum = String.format("%d%d%02d%02d", firstRegiNum, randomDigit, randomMonth, randomDay);

        int secondRegiNum;
        if (userGender.equals("F") && ( userAge == 1)) {
            secondRegiNum = 4;
        } else if (userGender.equals("M") && ( userAge == 1)) {
            secondRegiNum = 3;
        } else if (userGender.equals("F")) {
            secondRegiNum = 2;
        } else {
            secondRegiNum = 1;
        }

        int randomFourDigits = random.nextInt(10000);
        int secondLastDigit=random.nextInt(5);
        String lastRegiNumSix = String.format("%d%04d%d", secondRegiNum, randomFourDigits,secondLastDigit);

        int[] frontDigits = new int[6];
        int[] lastDigits = new int[6];

        // A~F까지의 숫자를 frontDigits 배열에 대입
        for (int i = 0; i < 6; i++) {
            frontDigits[i] = Character.getNumericValue(frontRegiNum.charAt(i));
        }

        // G~L까지의 숫자를 lastDigits 배열에 대입
        for (int i = 0; i < 6; i++) {
            lastDigits[i] = Character.getNumericValue(lastRegiNumSix.charAt(i));
        }

        // 공식에 따라 계산
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += (i + 2) * frontDigits[i];
        }
        for (int i = 0; i < 6; i++) {
            sum += (i + 8) * lastDigits[i];
        }

        int lastDigit = (11 - (sum % 11)) % 10;

        String lastRegiNum=lastRegiNumSix+lastDigit;

        model.addAttribute("frontRegiNum", frontRegiNum);
        model.addAttribute("lastRegiNum", lastRegiNum);

        return "ReceiptAndReviewWrite";
    }
}

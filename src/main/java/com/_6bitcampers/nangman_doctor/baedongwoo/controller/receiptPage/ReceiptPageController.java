package com._6bitcampers.nangman_doctor.baedongwoo.controller.receiptPage;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PcDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PillDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.PaymentService;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewAndReceiptService;
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
    private ReviewAndReceiptService reviewAndReceiptService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/receiptView")
    public String recieptPage(@RequestParam int receipt_no,
                              Model model) {
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = customOAuth2User.getEmail();
        String user_type = customOAuth2User.getType();

        userEntity userEntity = reviewAndReceiptService.getUserInfo(userId, user_type);
        ReceiptDto receiptDto = paymentService.getReceiptBySeq(receipt_no);
        int infoNo = receiptDto.getInfo_no();
        int reservation_no = receiptDto.getReservation_no();
        ReservationDto reservationDto = paymentService.getReservation(reservation_no);
        int employee_no = reservationDto.getEmployee_no();
        String empName = reviewAndReceiptService.getEmployeeName(employee_no);

        EmpDto empDto = paymentService.gethospitalInfo(infoNo);

        model.addAttribute("empName", empName);
        model.addAttribute("empDto", empDto);
        model.addAttribute("receiptDto", receiptDto);
        model.addAttribute("userEntity", userEntity);

        try {
            PcDto pcDto = reviewAndReceiptService.getPcContents(receipt_no);
            PillDto soyumDto=reviewAndReceiptService.getPillContent(pcDto.getPc_pill_1());
            PillDto jintongDto=reviewAndReceiptService.getPillContent(pcDto.getPc_pill_2());

            model.addAttribute("pcDto", pcDto);
            model.addAttribute("soyumDto", soyumDto);
            model.addAttribute("jintongDto", jintongDto);
        } catch (Exception e) {
            // 랜덤한 숫자 및 문자열 생성
            int randomNum = generateRandomNumber(1000, 9999);
            String randomDiseaseWord = generateRandomDiseaseWord();
            String randomDiseaseWord2 = generateRandomDiseaseWord();

            // 사용자 정보에서 나이와 성별 가져오기
            int userAge = getUserAge(userEntity);
            String userGender = getUserGender(userEntity);

            // 주민등록번호 생성
            String randomRegiNum = generateRandomRegiNum(userAge, userGender);
            
            //약 정보 랜덤으로 갖고오기
            PillDto soyumDto = reviewAndReceiptService.getRandomPillInfo("소염제");
            PillDto jintongDto = reviewAndReceiptService.getRandomPillInfo("진통제");

            // PcDto 생성 및 모델에 추가
            PcDto pcDto = PcDto.builder()
                    .pc_warranty_num(randomNum)
                    .pc_disease_num1(randomDiseaseWord)
                    .pc_disease_num2(randomDiseaseWord2)
                    .pc_regi_num(randomRegiNum)
                    .pc_pill_1(soyumDto.getPill_no())
                    .pc_pill_2(jintongDto.getPill_no())
                    .receipt_no(receipt_no)
                    .build();

            reviewAndReceiptService.insertPcContent(pcDto);

            model.addAttribute("pcDto", pcDto);
            model.addAttribute("soyumDto", soyumDto);
            model.addAttribute("jintongDto", jintongDto);
        }
        return "ReceiptAndReviewWrite";
    }

    private int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    private String generateRandomDiseaseWord() {
        char randomUpperCaseLetter = (char) ('A' + (int) (Math.random() * 26));
        int randomDiseaseNum = generateRandomNumber(1000, 9999);
        return randomUpperCaseLetter + " " + randomDiseaseNum;
    }

    private int getUserAge(userEntity userEntity) {
        return userEntity.getUser_age() == null ? 2 : Integer.parseInt(userEntity.getUser_age().substring(0, 1));
    }

    private String getUserGender(userEntity userEntity) {
        return userEntity.getUser_gender() == null ? "F" : userEntity.getUser_gender();
    }

    private String generateRandomRegiNum(int userAge, String userGender) {
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
        int randomMonth = random.nextInt(12) + 1;
        int randomDay = random.nextInt(31) + 1;

        String frontRegiNum = String.format("%d%d%02d%02d", firstRegiNum, randomDigit, randomMonth, randomDay);

        int secondRegiNum;
        if (userGender.equals("F") && userAge == 1) {
            secondRegiNum = 4;
        } else if (userGender.equals("M") && userAge == 1) {
            secondRegiNum = 3;
        } else if (userGender.equals("F")) {
            secondRegiNum = 2;
        } else {
            secondRegiNum = 1;
        }

        int randomFourDigits = random.nextInt(10000);
        int secondLastDigit = random.nextInt(5);
        String lastRegiNumSix = String.format("%d%04d%d", secondRegiNum, randomFourDigits, secondLastDigit);

        int[] frontDigits = new int[6];
        int[] lastDigits = new int[6];

        for (int i = 0; i < 6; i++) {
            frontDigits[i] = Character.getNumericValue(frontRegiNum.charAt(i));
        }

        for (int i = 0; i < 6; i++) {
            lastDigits[i] = Character.getNumericValue(lastRegiNumSix.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += (i + 2) * frontDigits[i];
        }
        for (int i = 0; i < 6; i++) {
            sum += (i + 8) * lastDigits[i];
        }

        int lastDigit = (11 - (sum % 11)) % 10;

        return frontRegiNum + "-" + lastRegiNumSix + lastDigit;
    }
}
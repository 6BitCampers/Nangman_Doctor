package com._6bitcampers.nangman_doctor.baedongwoo.controller.payment;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.AESUtil;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.PaymentService;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewAndReceiptService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ReviewAndReceiptService reviewAndReceiptService;

    private final AESUtil aesUtil;

    @Autowired
    public PaymentController(AESUtil aesUtil) {
        this.aesUtil = aesUtil;
    }

    @PostMapping("")
    public String payment(
            @RequestParam int user_no,
            @RequestParam int receipt_no,
            Model model
    ){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_type=customOAuth2User.getType();


        userEntity userDto= reviewAndReceiptService.getUserInfo(userId, user_type);
        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);

        model.addAttribute("userDto", userDto);
        model.addAttribute("receipt_no", receipt_no);
        model.addAttribute("receiptDto", receiptDto);
        model.addAttribute("user_no", user_no);
        return "payment";
    }

    @GetMapping("/process")
    public String payProcess(
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam String method,
            @RequestParam int receipt_no
    ){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_type=customOAuth2User.getType();

        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);
        int amount=receiptDto.getReceipt_amount();

        int hospital_no=receiptDto.getInfo_no();
        String user_noS=orderId.substring(5);
        int user_no=Integer.parseInt(user_noS);
        userEntity userEntity= reviewAndReceiptService.getUserInfoByNum(user_no);

        //현재 접속한 유저 아이디가 receipt의 user와 다르면 error페이지로
        if(userEntity.getUser_email().equals(userId)&&userEntity.getUser_type().equals(user_type)){
            PaymentDto paymentDto= PaymentDto.builder()
                    .payment_method(method)
                    .payment_amount(amount)
                    .user_no(user_no)
                    .payment_key(paymentKey)
                    .build();

            paymentService.uploadPayment(paymentDto);

            int payment_no=paymentDto.getPayment_no();

            Map<String,Object> receiptMap=new HashMap<>();

            receiptMap.put("receipt_no",receipt_no);
            receiptMap.put("receipt_paymentKey",paymentKey);
            receiptMap.put("receipt_amount",amount);
            receiptMap.put("payment_no",payment_no);

            paymentService.updateReceipt(receiptMap);

            String encryptedReceiptNo = aesUtil.encrypt(String.valueOf(receipt_no).trim());

            return "redirect:/payment/paymentSuccess?receipt_noEn="+encryptedReceiptNo;
        } else{
            return "paymentError";
        }
    }
    @GetMapping("/paymentSuccess")
    public String paymentSuccessPage(@RequestParam String receipt_noEn
    ,Model model){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_type=customOAuth2User.getType();

        int receipt_no=Integer.parseInt(aesUtil.decrypt(receipt_noEn));

        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);
        int amount=receiptDto.getReceipt_amount();
        int payment_no=receiptDto.getPayment_no();

        PaymentDto paymentDto=paymentService.getPayment(payment_no);
        String method=paymentDto.getPayment_method();
        int user_no=paymentDto.getUser_no();

        int hospital_no=receiptDto.getInfo_no();
        userEntity userEntity= reviewAndReceiptService.getUserInfo(userId, user_type);
        EmpDto empDto= paymentService.gethospitalInfo(hospital_no);
        String user_email=userEntity.getUser_email();

        Map<String,Object> response=new HashMap<>();

        String infoName=empDto.getInfo_name();

        response.put("receiptDto", receiptDto);
        response.put("paymentDto", paymentDto);
        response.put("empDto", empDto);
        response.put("userEntity", userEntity);
        response.put("method", method);
        response.put("receipt_no", receipt_no);
        response.put("amount", amount);
        response.put("user_no", user_no);

        paymentService.sendEmail(user_email, infoName+" 결제 메일", "emailTemplates/paymentConfirm",response);


        if(userEntity.getUser_email().equals(userId)&&userEntity.getUser_type().equals(user_type)){
            model.addAttribute("receipt_no", receipt_no);
            return "paymentSuccess";
        }else {
            return "paymentError";
        }
    }

    @ResponseBody
    @PostMapping("/success")
    public Map<String,Object> paySuccess(
            @RequestParam int receipt_no
    ){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_type=customOAuth2User.getType();

        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);
        int amount=receiptDto.getReceipt_amount();
        int payment_no=receiptDto.getPayment_no();

        PaymentDto paymentDto=paymentService.getPayment(payment_no);
        String method=paymentDto.getPayment_method();
        int user_no=paymentDto.getUser_no();

        int hospital_no=receiptDto.getInfo_no();
        userEntity userEntity= reviewAndReceiptService.getUserInfo(userId, user_type);
        EmpDto empDto= paymentService.gethospitalInfo(hospital_no);
        String user_email=userEntity.getUser_email();

        Map<String,Object> response=new HashMap<>();

        String infoName=empDto.getInfo_name();

        response.put("receiptDto", receiptDto);
        response.put("paymentDto", paymentDto);
        response.put("empDto", empDto);
        response.put("userEntity", userEntity);
        response.put("method", method);
        response.put("receipt_no", receipt_no);
        response.put("amount", amount);
        response.put("user_no", user_no);

        return response;
    }

}

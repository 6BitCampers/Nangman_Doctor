package com._6bitcampers.nangman_doctor.baedongwoo.controller.payment;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.PaymentService;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/payment")
    public String payment(
            @RequestParam int user_no,
            @RequestParam int receipt_no,
            Model model
    ){
        userEntity userDto= reviewService.getUserInfo(user_no);
        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);

        model.addAttribute("userDto", userDto);
        model.addAttribute("receiptDto", receiptDto);
        model.addAttribute("user_no", user_no);
        return "payment";
    }

    @GetMapping("/payment/success")
    public String paySuccess(
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam String method,
            @RequestParam int receipt_no,
            @RequestParam int amount
    ){
        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);

        int hospital_no=receiptDto.getInfo_no();
        String user_no=orderId.substring(5);

        PaymentDto paymentDto= PaymentDto.builder()
                .payment_method(method)
                .payment_amount(amount)
                .user_no(Integer.parseInt(user_no))
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

        return "redirect:mypage";
    }


}

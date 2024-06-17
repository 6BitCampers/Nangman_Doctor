package com._6bitcampers.nangman_doctor.baedongwoo.controller.payment;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment")
    public String payment(
            @RequestParam String user_no,
            @RequestParam int receipt_no,
            Model model
    ){
        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);
        model.addAttribute("receiptDto", receiptDto);
        model.addAttribute("user_no", user_no);
        return "payment";
    }

    @GetMapping("/payment/fail")
    public String payFail(){
        return "payment";
    }

    @GetMapping("/payment/success")
    public String paySuccess(
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam String method,
            @RequestParam int receipt_no
    ){
        if (orderId == null || orderId.isEmpty() ||
            paymentKey == null || paymentKey.isEmpty() ||
            method == null || method.isEmpty()) {
            throw new IllegalArgumentException("잘못된 접근방법입니다");
        }
        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);
        int amount=receiptDto.getReceipt_amount();
        int hospital_no=receiptDto.getInfo_no();

        PaymentDto paymentDto= new PaymentDto();

        paymentDto=PaymentDto.builder()
                .payment_method(method)
                .payment_amount(amount)
                .user_no(Integer.parseInt(orderId))
                .payment_key(paymentKey)
                .build();

        Map<String,Object> receiptMap=new HashMap<>();

        receiptMap.put("receipt_no",receipt_no);
        receiptMap.put("receipt_paymentKey",paymentKey);
        receiptMap.put("receipt_amount",amount);
        
        paymentService.updateReceipt(receiptMap);

        return "redirect:hospitaldetail?hospitalno="+hospital_no;
    }


}

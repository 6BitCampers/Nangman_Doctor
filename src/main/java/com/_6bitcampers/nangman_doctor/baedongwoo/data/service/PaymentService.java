package com._6bitcampers.nangman_doctor.baedongwoo.data.service;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.mapper.PaymentInter;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    private PaymentInter paymentInter;
    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public PaymentService(JavaMailSender mailSender, PaymentInter paymentInter, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.paymentInter = paymentInter;
    }

    public ReceiptDto getReceiptBySeq(int receipt_no){
        return paymentInter.getReceiptBySeq(receipt_no);
    }
    public void uploadPayment(PaymentDto paymentDto){
         paymentInter.uploadPayment(paymentDto);
    }
    public void updateReceipt(Map<String, Object> receiptMap){
        paymentInter.updateReceipt(receiptMap);
    }
    public EmpDto gethospitalInfo(int info_no){
        return paymentInter.gethospitalInfo(info_no);
    }
    public PaymentDto getPayment(int payment_no){
        return  paymentInter.getPayment(payment_no);
    }
    public ReservationDto getReservation(int reservation_no){
        return paymentInter.getReservation(reservation_no);
    }

    @Async
    public void sendEmail(String to, String subject, String templateName, Map<String, Object> response) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariables(response);

            // Load HTML template using TemplateEngine
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

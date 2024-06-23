package com._6bitcampers.nangman_doctor.baedongwoo.data.service;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.mapper.PaymentInter;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    private PaymentInter paymentInter;

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
}

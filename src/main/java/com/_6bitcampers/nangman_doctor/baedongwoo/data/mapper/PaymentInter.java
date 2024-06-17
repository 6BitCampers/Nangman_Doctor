package com._6bitcampers.nangman_doctor.baedongwoo.data.mapper;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface PaymentInter {
    @Select("select * from hospital_receipt where receipt_no=#{receipt_no}")
    public ReceiptDto getReceiptBySeq(int receipt_no);
    @Insert("insert into hospital_payment (payment_method, payment_amount, payment_date, user_no, payment_key) values (#{payment_method},#{payment_amount},now(),#{user_no},#{payment_key})")
    @Options(useGeneratedKeys = true, keyColumn = "payment_no", keyProperty = "payment_no")
    public int uploadPayment(PaymentDto paymentDto);
    @Update("update hospital_receipt set receipt_payment_key=#{receipt_paymentKey},payment_no=#{payment_no},receipt_amount=#{receipt_amount} where receipt_no=#{receipt_no}")
    public void updateReceipt(Map<String, Object> receiptMap);
    
}

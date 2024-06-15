package com._6bitcampers.nangman_doctor.baedongwoo.data.mapper;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface PaymentInter {
    @Select("select * from hospital_receipt where receipt_no=#{receipt_no}")
    public ReceiptDto getReceiptBySeq(int receipt_no);
    @Insert("insert into hospital_payment (payment_method, payment_amount, payment_date, user_no, payment_key) values (#{method},#{amount},now(),#{user_no},#{paymentKey})")
    public void uploadPayment(PaymentDto paymentDto);
    @Update("update hospital_receipt set receipt_paymentKey=#{receipt_paymentKey},receipt_amount=#{receipt_amount} where receipt_no=#{receipt_no}")
    public void updateReceipt(Map<String, Object> receiptMap);
}

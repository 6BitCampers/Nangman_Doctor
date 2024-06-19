package com._6bitcampers.nangman_doctor.woohyeong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceiptDTO {
    private int receipt_no;
    private String receipt_name;
    private int payment_no;
    private String receipt_status;
    private String receipt_reason;
    private String receipt_payment_key;
    private String receipt_date;
    private int reservation_no;
    private int receipt_amount;
    private int info_no;
}

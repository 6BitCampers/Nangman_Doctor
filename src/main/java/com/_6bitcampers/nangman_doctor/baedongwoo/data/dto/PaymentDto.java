package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("PaymentDto")
@Builder
public class PaymentDto {
    private int payment_no;
    private String payment_method;
    private int payment_amount;
    private int user_no;
    private String payment_key;
    private Timestamp payment_date;
}

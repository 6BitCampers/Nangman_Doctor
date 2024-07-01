package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("ReceiptDto")
@Schema(description = "Receipt Data Transfer Object")
public class ReceiptDto {
    private int receipt_no;
    private String receipt_name;
    private int payment_no;
    private String receipt_status;
    private String receipt_payment_key;
    private Timestamp receipt_date;
    private int reservation_no;
    private int receipt_amount;
    private int info_no;
}

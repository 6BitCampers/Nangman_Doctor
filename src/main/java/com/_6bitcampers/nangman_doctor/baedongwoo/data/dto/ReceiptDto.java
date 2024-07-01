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
public class ReceiptDto {
    @Schema(description = "영수증 시퀀스")
    private int receipt_no;
    @Schema(description = "영수증 이름")
    private String receipt_name;
    @Schema(description = "결제정보 시퀀스")
    private int payment_no;
    @Schema(description = "환자 상태")
    private String receipt_status;
    @Schema(description = "결제정보 반환키")
    private String receipt_payment_key;
    @Schema(description = "영수증 날짜")
    private Timestamp receipt_date;
    @Schema(description = "예약테이블 시퀀스")
    private int reservation_no;
    @Schema(description = "결제할 금액")
    private int receipt_amount;
    @Schema(description = "병원 시퀀스")
    private int info_no;
}

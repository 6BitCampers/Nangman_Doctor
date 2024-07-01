package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "결제정보 시퀀스")
    private int payment_no;
    @Schema(description = "결제방법")
    private String payment_method;
    @Schema(description = "결제한 금액")
    private int payment_amount;
    @Schema(description = "유저 시퀀스")
    private int user_no;
    @Schema(description = "결제정보 반환키")
    private String payment_key;
    @Schema(description = "결제일")
    private Timestamp payment_date;
}

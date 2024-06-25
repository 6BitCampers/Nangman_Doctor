package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("PcDto")
public class PcDto {
    private int pc_no;
    private int pc_warranty_num;
    private String pc_regi_num;
    private String pc_disease_num1;
    private String pc_disease_num2;
    private int pc_pill_1;
    private int pc_pill_2;
    private int receipt_no;
}

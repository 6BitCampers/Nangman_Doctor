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
@Alias("PillDto")
public class PillDto {
    private int pill_no;
    private String pill_name;
    private String pill_act;
    private String pill_code;
}

package com._6bitcampers.nangman_doctor.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@Alias("HospitalDto")
public class HospitalDto {
    private Long info_no;
    private String info_name;
    private double info_x;
    private double info_y;
    private String info_description;
    private String info_photo;
    private int info_likecount;


}

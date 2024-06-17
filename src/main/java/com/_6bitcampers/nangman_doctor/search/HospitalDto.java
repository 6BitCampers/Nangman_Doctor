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
    private String Monday;
    private String Tuesday;
    private String Wednesday;
    private String Thursday;
    private String Friday;
    private String Saturday;
    private String Sunday;
    private String Holiday;
    private String info_addr;
    private String info_plus;
    private String info_hp;
}

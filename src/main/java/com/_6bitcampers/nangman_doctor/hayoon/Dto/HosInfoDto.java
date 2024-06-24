package com._6bitcampers.nangman_doctor.hayoon.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HosInfoDto {
    private int info_no;
    private String info_name;
    private double info_x;
    private double info_y;
    private String info_description;
    private String info_photo;
    private double info_likecount;
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

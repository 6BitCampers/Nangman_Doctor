package com._6bitcampers.nangman_doctor.woohyeong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private int employee_no;
    private String employee_pw;
    private String employee_name;
    private String employee_addr1;
    private String employee_email;
    private String employee_role;
    private String employee_hp;
    private String employee_nickname;
    private String employee_field;
    private int employee_likecount;
    private int info_no;
    private String employee_gender;
    private String employee_age;
    private String employee_type;
}

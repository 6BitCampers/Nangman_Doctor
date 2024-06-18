package com._6bitcampers.nangman_doctor.leegahyun.management.managementDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpDto {
    private int info_no;
    private String employee_name;
    private float info_likecount;
    private int employee_no;
    private String employee_role;
    private String employee_email;
    private String employee_addr1;
    private String employee_hp;
    private String employee_nickname;
    private String employee_likecount;

}
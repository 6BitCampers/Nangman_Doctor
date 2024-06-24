package com._6bitcampers.nangman_doctor.search;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long employee_no;
    private String employee_name;
    private String employee_field;
    private String employee_gender;
    private String employee_age;
    private String employee_hp;
    private String employee_role;
    private double employee_likecount;
    private Long info_no;
}


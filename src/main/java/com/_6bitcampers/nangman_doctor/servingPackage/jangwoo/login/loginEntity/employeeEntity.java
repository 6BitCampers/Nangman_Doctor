package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("employeeEntity")
public class employeeEntity {
    //not null
    private String employee_name;
    private String employee_email;
    private String employee_role;
    private String employee_type;

    //null
    private String employee_pw;
    private String employee_gender;
    private String employee_addr;
    private String employee_hp;
    private String employee_age;
    private String employee_nickname;
    private String employee_interest;
}

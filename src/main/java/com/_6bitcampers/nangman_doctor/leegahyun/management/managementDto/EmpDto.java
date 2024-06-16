package com._6bitcampers.nangman_doctor.leegahyun.management.managementDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class EmpDto {
    private int info_no;
    private String employee_name;
    private float info_likecount;

    public int getInfo_no() {
        return info_no;
    }

    public void setInfo_no(int info_no) {
        this.info_no = info_no;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public float getInfo_likecount() {
        return info_likecount;
    }

    public void setInfo_likecount(int info_likecount) {
        this.info_likecount = info_likecount;
    }
}

package com._6bitcampers.nangman_doctor.search;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("SELECT * FROM hospital_employee WHERE info_no = #{infoNo} and employee_role = 'ROLE_DOCTOR'")
    List<EmployeeDto> findByInfoNo(Long infoNo);

    @Select("SELECT * FROM hospital_employee " +
            "WHERE employee_role = 'ROLE_DOCTOR' " +
            "ORDER BY employee_likecount DESC " +
            "LIMIT 10")
    List<EmployeeDto> findTop10ByRoleAndLikeCount();

    @Select("SELECT info_name FROM hospital_info WHERE info_no = #{infoNo}")
    String findHospitalNameByInfoNo(Long infoNo);
}


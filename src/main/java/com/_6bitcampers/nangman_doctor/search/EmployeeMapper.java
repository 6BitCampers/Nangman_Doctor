package com._6bitcampers.nangman_doctor.search;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("SELECT * FROM hospital_employee WHERE info_no = #{infoNo}")
    List<EmployeeDto> findByInfoNo(Long infoNo);
}

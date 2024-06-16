package com._6bitcampers.nangman_doctor.leegahyun.management.managementMapper;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface empMapper {

    @Select("SELECT hi.info_no, he.employee_name, hi.info_likecount FROM (select * from hospital_employee where employee_email=#{email}) he JOIN hospital_info hi ON he.info_no = hi.info_no")
    List<EmpDto> findLikecountByInfoNo(@Param("email") String email);




}

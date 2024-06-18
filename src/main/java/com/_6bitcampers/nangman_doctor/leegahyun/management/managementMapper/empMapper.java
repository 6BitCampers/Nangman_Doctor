package com._6bitcampers.nangman_doctor.leegahyun.management.managementMapper;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface empMapper {
    @Select("SELECT hi.info_no,he.employee_name,hi.info_likecount FROM (select * from hospital_employee where employee_email=#{email}) he JOIN hospital_info hi ON he.info_no = hi.info_no")
    List<EmpDto> findLikecountByInfoNo(@Param("email") String email);

    @Select("SELECT hi.info_no,he.employee_name,hi.info_likecount,he.employee_no,he.employee_role,he.employee_email,he.employee_addr1,he.employee_hp,he.employee_nickname,he.employee_likecount FROM hospital_employee he JOIN hospital_info hi ON he.info_no = hi.info_no WHERE he.info_no = (SELECT info_no FROM hospital_employee WHERE employee_email = #{email});")
    List<EmpDto> getEmpList(@Param("email") String email);

    @Update("UPDATE hospital_employee SET employee_role = #{role} WHERE employee_email = #{email}")
    void updateRoleByEmail(@Param("email") String email, @Param("role") String role);
}
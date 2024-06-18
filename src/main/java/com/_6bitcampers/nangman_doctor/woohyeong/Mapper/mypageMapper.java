package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import com._6bitcampers.nangman_doctor.woohyeong.dto.EmployeeDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface mypageMapper {
    @Select("""
    select * from normal_user where user_email = #{user_email}
""")
    UserDTO getUser(String user_email);

    @Select("""
    select * from hospital_employee where employee_email = #{employee_email}
""")
    EmployeeDTO getEmployee(String employee_email);
}

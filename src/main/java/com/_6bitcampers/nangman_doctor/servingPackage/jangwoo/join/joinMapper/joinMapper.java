package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinMapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface joinMapper {

    @Insert("insert into normal_user (user_name, user_email, user_password, user_gender, user_addr1, user_hp, user_age, user_nickname, user_interest, user_role, user_type) values (#{name},#{email},#{password},#{gender},#{addr},#{hp},#{age},#{nickname},#{interest},'ROLE_USER','local')")
    void insertNormalUserDefault(joinRequestDto dto);

    @Insert("insert into hospital_employee (employee_name, employee_email, employee_pw, employee_gender, employee_addr1, employee_hp, employee_age, employee_nickname, employee_role, employee_type) values (#{name},#{email},#{password},#{gender},#{addr},#{hp},#{age},#{nickname},'ROLE_USER','local')")
    void insertEmployeeUserDefault(joinRequestDto dto);
}

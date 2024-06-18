package com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestMappper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface joinRestMapper {

    @Select("select count(*) from hospital_employee where employee_email=#{email}")
    int findByEmailEmployee(String email);

    @Select("select count(*) from hospital_employee where employee_nickname=#{nickname}")
    int findByNicknameEmployee(String nickname);

    @Select("select count(*) from normal_user where user_email=#{email}")
    int findByEmailUser(String email);

    @Select("select count(*) from normal_user where user_nickname=#{nickname}")
    int findByNicknameUser(String nickname);
}

package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginMapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface userEntityMapper {

    @Select("SELECT count(*) from normal_user where user_email=#{email}")
    int findByEmail(String email);

    @Insert("insert into normal_user (user_name, user_email, user_gender, user_hp, user_age, user_nickname, user_role) values (#{user_name}, #{user_email}, #{user_gender}, #{user_hp}, #{user_age}, #{user_nickname}, #{user_role});")
    void insertNaverUser(userEntity userEntity);

    @Update("update normal_user set user_name=#{user_name}, user_gender=#{user_gender}, user_hp=#{user_hp},user_age=#{user_age},user_nickname=#{user_nickname} where user_email=#{user_email}")
    void updateNaverUser(userEntity userEntity);

    @Select("select user_role from normal_user where user_email=#{email}")
    String findRoleByEmail(String email);

    @Select("select * from normal_user where user_email=#{email} and user_type='local'")
    userEntity findNormalUserByEmail(@Param("email") String email);

    @Select("select * from hospital_employee where employee_email=#{email} and employee_type='local'")
    userEntity findEmployeeUserByEmail(String email);
}

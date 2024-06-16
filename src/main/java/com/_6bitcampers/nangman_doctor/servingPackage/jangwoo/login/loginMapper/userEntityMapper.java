package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginMapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.employeeEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface userEntityMapper {

    @Select("SELECT count(*) from normal_user where user_email=#{user_email} and user_type=#{user_type}")
    int findByEmail(userEntity entity);

    @Select("select count(*) from hospital_employee where employee_email=#{user_email} and employee_type=#{user_type}")
    int findEmployeeByEmail(userEntity entity);

    @Insert("insert into normal_user (user_name, user_email, user_gender, user_hp, user_age, user_nickname, user_role,user_type) values (#{user_name}, #{user_email}, #{user_gender}, #{user_hp}, #{user_age}, #{user_nickname}, #{user_role}, #{user_type})")
    void insertNaverUser(userEntity userEntity);

    @Update("update normal_user set user_name=#{user_name}, user_gender=#{user_gender}, user_hp=#{user_hp},user_age=#{user_age},user_nickname=#{user_nickname} where user_email=#{user_email} and user_type=#{user_type}")
    void updateNaverUser(userEntity userEntity);

    @Update("update hospital_employee set employee_name=#{user_name}, employee_gender=#{user_gender}, employee_hp=#{user_hp},employee_age=#{user_age},employee_nickname=#{user_nickname} where employee_email=#{user_email} and employee_type=#{user_type}")
    void updateEmployeeUser(userEntity userEntity);

    @Select("select user_role from normal_user where user_email=#{user_email} and user_type=#{user_type}")
    String findRoleByUserEmail(userEntity entity);

    @Select("select employee_role from hospital_employee where employee_email=#{user_email} and employee_type=#{user_type}")
    String findRoleByEmployeeEmail(userEntity entity);

    @Select("select * from normal_user where user_email=#{email} and user_type='local'")
    userEntity findNormalUserByEmail(@Param("email") String email);

    @Select("select * from hospital_employee where employee_email=#{email} and employee_type='local'")
    employeeEntity findEmployeeUserByEmail(@Param("email") String email);
}

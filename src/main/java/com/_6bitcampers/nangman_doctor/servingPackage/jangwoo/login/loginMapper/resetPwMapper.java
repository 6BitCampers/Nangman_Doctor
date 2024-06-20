package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginMapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.employeeEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface resetPwMapper {

    @Select("select search_email from search_password where search_url=#{uuid}")
    String findOneByUUID(String uuid);

    @Select("select * from normal_user where user_email=#{email} and user_type='local'")
    userEntity findOneUserByEmail(String email);

    @Select("select * from hospital_employee where employee_email=#{email} and employee_type='local'")
    employeeEntity findOneEmployeeByEmail(String email);

    @Update("update normal_user set user_password = #{password}, user_role='ROLE_USER' where user_email=#{email} and user_type='local'")
    void updateUserPW(String email,String password);

    @Update("update hospital_employee set employee_pw = #{password}, employee_role='ROLE_USER' where employee_email=#{email} and employee_type='local'")
    void updateEmployeePW(String email,String password);

    @Delete("delete from search_password where search_url=#{uuid}")
    void deleteUUID(String uuid);
}

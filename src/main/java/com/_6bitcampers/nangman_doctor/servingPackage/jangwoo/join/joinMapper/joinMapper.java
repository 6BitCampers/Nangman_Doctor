package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinMapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface joinMapper {

    @Insert("insert into normal_user (user_name, user_email, user_password, user_gender, user_addr1, user_hp, user_age, user_nickname, user_interest, user_role, user_type) values (#{name},#{email},#{password},#{gender},#{addr},#{hp},#{age},#{nickname},#{interest},'ROLE_USER','local')")
    void insertNormalUserDefault(joinRequestDto dto);

    @Insert("insert into hospital_employee (employee_name, employee_email, employee_pw, employee_gender, employee_addr1, employee_hp, employee_age, employee_nickname, employee_role, employee_type) values (#{name},#{email},#{password},#{gender},#{addr},#{hp},#{age},#{nickname},'ROLE_EMP','local')")
    void insertEmployeeUserDefault(joinRequestDto dto);

    @Select("select * from normal_user where user_type=#{type} and user_email=#{email}")
    userEntity findByEmailAndType(@Param("type")String type,@Param("email")String email);

    @Delete("delete from normal_user where user_email=#{email} and user_type=#{type};")
    void deleteByEmailAndType(@Param("type")String type,@Param("email")String email);

    @Insert("insert into hospital_employee (employee_name, employee_email, employee_pw, employee_gender, employee_addr1, employee_hp, employee_age, employee_nickname, employee_role, employee_type,employee_field) values (#{dto.name},#{dto.email},#{dto.password},#{dto.gender},#{dto.addr},#{dto.hp},#{dto.age},#{dto.nickname},'ROLE_EMP',#{type},#{dto.interest})")
    void insertEmployeeUserNormal(joinRequestDto dto,@Param("type") String type);

    @Insert("insert into normal_user (user_name, user_email, user_password, user_gender, user_addr1, user_hp, user_age, user_nickname, user_interest, user_role, user_type) values (#{dto.name},#{dto.email},#{dto.password},#{dto.gender},#{dto.addr},#{dto.hp},#{dto.age},#{dto.nickname},#{dto.interest},'ROLE_USER',#{type})")
    void insertNormalUserNormal(joinRequestDto dto, @Param("type") String type);
}

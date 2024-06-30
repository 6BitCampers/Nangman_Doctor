package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinMapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface joinMapper {

    @Insert("insert into normal_user (user_name, user_email, user_password, user_gender, user_addr1, user_hp, user_age, user_nickname, user_interest, user_role, user_type) values (#{name},#{email},#{password},#{gender},#{addr},#{hp},#{age},#{nickname},#{interest},'ROLE_NOTVAILD','local')")
    void insertNormalUserDefault(joinRequestDto dto);

    @Insert("insert into hospital_employee (employee_name, employee_email, employee_pw, employee_gender, employee_addr1, employee_hp, employee_age, employee_nickname, employee_role, employee_type,employee_field) values (#{name},#{email},#{password},#{gender},#{addr},#{hp},#{age},#{nickname},'ROLE_NOTVAILD','local',#{interest})")
    void insertEmployeeUserDefault(joinRequestDto dto);

    @Select("select * from normal_user where user_type=#{type} and user_email=#{email}")
    userEntity findByEmailAndType(@Param("type")String type,@Param("email")String email);

    @Delete("delete from normal_user where user_email=#{email} and user_type=#{type};")
    void deleteByEmailAndType(@Param("type")String type,@Param("email")String email);

    @Insert("insert into hospital_employee (employee_name, employee_email, employee_pw, employee_gender, employee_addr1, employee_hp, employee_age, employee_nickname, employee_role, employee_type,employee_field) values (#{dto.name},#{dto.email},#{dto.password},#{dto.gender},#{dto.addr},#{dto.hp},#{dto.age},#{dto.nickname},'ROLE_EMP',#{type},#{dto.interest})")
    void insertEmployeeUserNormal(joinRequestDto dto,@Param("type") String type);

    @Insert("insert into normal_user (user_name, user_email, user_password, user_gender, user_addr1, user_hp, user_age, user_nickname, user_interest, user_role, user_type) values (#{dto.name},#{dto.email},#{dto.password},#{dto.gender},#{dto.addr},#{dto.hp},#{dto.age},#{dto.nickname},#{dto.interest},'ROLE_USER',#{type})")
    void insertNormalUserNormal(joinRequestDto dto, @Param("type") String type);

    @Insert("insert into email_vaild (vaild_uuid,vaild_email, vaild_type) values (#{uuid},#{email},'local')")
    void insertEmailVaild(@Param("uuid") String uuid,@Param("email") String email);
    
    @Select("select count(*) from email_vaild where vaild_uuid=#{uuid}")
    boolean existEmailVaild(@Param("uuid") String uuid);

    @Select("select vaild_email from email_vaild where vaild_uuid=#{uuid}")
    String findOneEmailVaild(@Param("uuid") String uuid);

    @Update("update normal_user set user_role = 'ROLE_USER' where user_email=#{email} and user_type='local'")
    void updateUserRole(@Param("email") String email);

    @Update("update hospital_employee set employee_role = 'ROLE_EMP' where employee_email=#{email} and employee_type='local'")
    void updateEmployeeRole(@Param("email") String email);

    @Delete("delete from email_vaild where vaild_uuid=#{uuid}")
    void deleteEmailVaild(@Param("uuid") String uuid);

}

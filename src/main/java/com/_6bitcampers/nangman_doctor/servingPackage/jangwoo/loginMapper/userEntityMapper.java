package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginMapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginEntity.userEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface userEntityMapper {

    @Select("SELECT count(*) from normal_user where normal_user_email=#{email}")
    int findByEmail(String email);

    @Insert("insert into normal_user (normal_user_name, normal_user_email, normal_user_gender, normal_user_hp, normal_user_age, normal_user_nickname, normal_user_role) values (#{normal_user_name}, #{normal_user_email}, #{normal_user_gender}, #{normal_user_hp}, #{normal_user_age}, #{normal_user_nickname}, #{normal_user_role});")
    void insertNaverUser(userEntity userEntity);

    @Update("update normal_user set normal_user_name=#{normal_user_name}, normal_user_gender=#{normal_user_gender}, normal_user_hp=#{normal_user_hp},normal_user_age=#{normal_user_age},normal_user_nickname=#{normal_user_nickname} where normal_user_email=#{normal_user_email}")
    void updateNaverUser(userEntity userEntity);

    @Select("select normal_user_role from normal_user where normal_user_email=#{email}")
    String findRoleByEmail(String email);
}

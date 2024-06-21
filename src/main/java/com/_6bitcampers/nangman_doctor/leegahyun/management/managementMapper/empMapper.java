package com._6bitcampers.nangman_doctor.leegahyun.management.managementMapper;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface empMapper {
    @Select("SELECT hi.info_name,hi.info_photo,hi.info_description,hi.info_addr,hi.info_plus,hi.info_hp,hi.info_no,he.employee_name,hi.info_likecount FROM (select * from hospital_employee where employee_email=#{email}) he JOIN hospital_info hi ON he.info_no = hi.info_no")
    List<EmpDto> findLikecountByInfoNo(@Param("email") String email);

    @Select("SELECT hi.info_photo,hi.info_description,hi.info_addr,hi.info_plus,hi.info_hp,hi.info_no,he.employee_name,hi.info_likecount,he.employee_no,he.employee_role,he.employee_email,he.employee_addr1,he.employee_hp,he.employee_nickname,he.employee_likecount FROM hospital_employee he JOIN hospital_info hi ON he.info_no = hi.info_no WHERE he.info_no = (SELECT info_no FROM hospital_employee WHERE employee_email = #{email});")
    List<EmpDto> getEmpList(@Param("email") String email);

    @Update("UPDATE hospital_employee SET employee_role = #{role} WHERE employee_email = #{email}")
    void updateRoleByEmail(@Param("email") String email, @Param("role") String role);

    @Update("UPDATE hospital_info hi JOIN hospital_employee he ON he.info_no = hi.info_no SET hi.info_photo = #{photo} WHERE he.employee_email = #{email}")
    void updatePhotoByEmail(@Param("email") String email, @Param("photo") String photo);

    @Update("UPDATE hospital_info hi JOIN hospital_employee he ON he.info_no = hi.info_no SET hi.info_description = #{description} WHERE he.employee_email = #{email}")
    void updateDescriptionByEmail(@Param("email") String email, @Param("decription") String description);

    @Update("UPDATE hospital_info hi JOIN hospital_employee he ON he.info_no = hi.info_no SET hi.info_addr = #{addr} WHERE he.employee_email = #{email}")
    void updateAddrByEmail(@Param("email") String email, @Param("addr") String addr);

    @Update("UPDATE hospital_info hi JOIN hospital_employee he ON he.info_no = hi.info_no SET hi.info_plus = #{plus} WHERE he.employee_email = #{email}")
    void updatePlusByEmail(@Param("email") String email, @Param("plus") String plus);

    @Update("UPDATE hospital_info hi JOIN hospital_employee he ON he.info_no = hi.info_no SET hi.info_hp = #{hp} WHERE he.employee_email = #{email}")
    void updateHpByEmail(@Param("email") String email, @Param("hp") String hp);

    @Select("SELECT info_name FROM hospital_info")
    List<EmpDto> getAllHospitalNames();

    @Update("UPDATE hospital_employee he SET he.info_no = ( SELECT hi.info_no FROM hospital_info hi WHERE hi.info_name = #{hname} LIMIT 1) WHERE he.employee_email = #{email}")
    void updateNameByEmail(@Param("email") String email,@Param("hname") String hname);
}
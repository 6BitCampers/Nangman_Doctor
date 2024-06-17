package com._6bitcampers.nangman_doctor.search;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface HospitalMapper {

    @Select("SELECT * FROM hospital_info WHERE info_description LIKE CONCAT('%', #{keyword}, '%')")
    List<HospitalDto> findByNameContaining(String keyword);

    @Select("SELECT * FROM hospital_info WHERE info_description LIKE CONCAT('%', #{keyword}, '%') ORDER BY info_likecount DESC")
    List<HospitalDto> findTopRatedByKeyword(String keyword);
    @Select("SELECT * FROM hospital_info WHERE info_no = #{info_no}")
    HospitalDto findById(Long info_no);
}
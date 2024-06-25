package com._6bitcampers.nangman_doctor.search;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface HospitalMapper {

    @Select("SELECT * FROM hospital_info WHERE info_description LIKE CONCAT('%', #{keyword}, '%') OR info_name LIKE CONCAT('%', #{keyword}, '%') LIMIT #{limit} OFFSET #{offset}")
    List<HospitalDto> findByNameContaining(String keyword, int limit, int offset);

    @Select("SELECT * FROM hospital_info WHERE info_description LIKE CONCAT('%', #{keyword}, '%') OR info_name LIKE CONCAT('%', #{keyword}, '%') ORDER BY info_likecount DESC LIMIT #{limit} OFFSET #{offset}")
    List<HospitalDto> findTopRatedByKeyword(String keyword, int limit, int offset);

    @Select("SELECT COUNT(*) FROM hospital_info WHERE info_description LIKE CONCAT('%', #{keyword}, '%') OR info_name LIKE CONCAT('%', #{keyword}, '%')")
    long countByNameContaining(String keyword);

    @Select("SELECT * FROM hospital_info WHERE info_no = #{info_no}")
    HospitalDto findById(Long info_no);

    @Select("SELECT info_name FROM hospital_info WHERE info_no = #{info_no}")
    HospitalDto findName(Long info_no);
}
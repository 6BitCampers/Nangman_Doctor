package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import com._6bitcampers.nangman_doctor.woohyeong.dto.InfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoMapper {
    @Select("SELECT info_name FROM hospital_info WHERE info_name LIKE #{info_name}")
    List<InfoDTO> getInfoByName(String info_name);
}

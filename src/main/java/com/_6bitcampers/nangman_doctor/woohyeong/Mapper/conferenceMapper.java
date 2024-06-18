package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface conferenceMapper {
    @Select("""
    select user_name from normal_user where user_email = #{user_email}
""")
    String getEmail(String user_email);
}

package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface conferenceMapper {
    @Select("""
    select user_no from normal_user where user_email = #{user_email}
""")
    int getUserNo(String user_email);
}

package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("userEntity")
public class userEntity {
    //not null
    private String normal_user_name;
    private String normal_user_email;
    private String normal_user_role;

    //null
    private String normal_user_password;
    private String normal_user_gender;
    private String normal_user_addr;
    private String normal_user_hp;
    private String normal_user_age;
    private String normal_user_nickname;
    private String normal_user_interest;
}

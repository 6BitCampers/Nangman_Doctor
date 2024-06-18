package com._6bitcampers.nangman_doctor.woohyeong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private int user_no;
    private String user_name;
    private String user_password;
    private String user_email;
    private String user_gender;
    private String user_addr1;
    private String user_hp;
    private String user_age;
    private String user_nickname;
    private String user_interest;
    private String user_role;
    private String user_type;
}

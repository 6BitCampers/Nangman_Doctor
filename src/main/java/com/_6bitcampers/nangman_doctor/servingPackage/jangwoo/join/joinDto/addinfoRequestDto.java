package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("addinfoRequestDto")
public class addinfoRequestDto {
    private String type;
    private String gender;
    private String addr;
    private String hp;
    private String age;
    private String nickname;
    private String interest;
}

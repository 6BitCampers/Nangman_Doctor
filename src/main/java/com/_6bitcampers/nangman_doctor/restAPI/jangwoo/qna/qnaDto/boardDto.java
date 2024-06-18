package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("boardDto")
public class boardDto {
    private String qna_title;
    private String qna_comment;
    private String qna_username;
    private String qna_password;
    private String qna_image;
}

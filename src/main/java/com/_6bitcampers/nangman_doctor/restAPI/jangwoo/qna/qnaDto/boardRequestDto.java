package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class boardRequestDto {
    private String qna_title;
    private String qna_comment;
    private String qna_username;
    private String qna_password;
    private MultipartFile qna_image = null;
}

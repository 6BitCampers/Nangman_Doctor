package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@Alias("qnaBoard")
public class qnaBoard {
    private int qna_no;
    private String qna_title;
    private String qna_comment;
    private LocalDateTime qna_writeday;
    private String qna_username;
    private String qna_password;
    private String qna_image;
    private int comment_count;
}

package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("ReviewDto")
public class ReviewDto {
    @Schema(description = "리뷰 시퀀스")
    private int review_no;
    @Schema(description = "리뷰 제목")
    private String review_title;
    @Schema(description = "리뷰 내용")
    private String review_content;
    @Schema(description = "리뷰 작성일")
    private Timestamp review_writeday;
    @Schema(description = "리뷰 조회수")
    private int review_viewcount;
    @Schema(description = "리뷰 쓴 사람 시퀀스")
    private int user_no;
    @Schema(description = "리뷰가 달린 직원 시퀀스")
    private int employee_no;
    @Schema(description = "리뷰 평점")
    private int review_likecount;
    @Schema(description = "리뷰가 달린 직원 이름")
    private String employee_name;


    public String getFormattedWriteday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(review_writeday);
    }
}

package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("ReviewDto")
public class ReviewDto {
    private int review_no;
    private String review_title;
    private String review_content;
    private Timestamp review_writeday;
    private int review_date;
    private int user_no;
    private int info_no;
}

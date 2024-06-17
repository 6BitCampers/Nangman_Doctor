package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
    private int review_viewcount;
    private int user_no;
    private int employee_no;
    private int review_likecount;

    public String getFormattedWriteday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(review_writeday);
    }
}

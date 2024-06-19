package com._6bitcampers.nangman_doctor.baedongwoo.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private int review_no;
    private String review_title;
    private String review_content;
    private Timestamp review_writeday;
    private int review_viewcount;
    private int user_no;
    private int employee_no;
    private int review_likecount;
    private String photos;

    public String getFormattedWriteday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(review_writeday);
    }
    public List<String> getSplitedPhotos(){
        // photos 필드를 ",,"로 분리하여 리스트로 반환
        if (photos != null && !photos.isEmpty()) {
            return Arrays.asList(photos.split(",,")); // 구분자 ",,"로 분리하여 배열을 리스트로 변환
        } else {
            return null; // photos가 null이거나 비어있을 경우 처리
        }
    }
}

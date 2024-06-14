package com._6bitcampers.nangman_doctor.baedongwoo.data.mapper;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewInter {
    public List<ReviewDto> getPagenationedReviews(Map<String,Integer> pagination);
    public int getAllReviewsCount();
    public ReviewDto getReviewBySeq(int review_no);
    public void addReview(ReviewDto reviewDto);
    public void updateReview(ReviewDto reviewDto);
    public void deleteReview(int review_no);
    public void updateViewcount(int review_no);
}

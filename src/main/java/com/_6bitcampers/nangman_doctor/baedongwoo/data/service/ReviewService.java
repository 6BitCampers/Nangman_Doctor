package com._6bitcampers.nangman_doctor.baedongwoo.data.service;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.mapper.ReviewInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private ReviewInter reviewInter;

    public List<ReviewDto> getPagenationedReviews(int startnum, int perPage) {
        Map<String,Integer> pagination = new HashMap<>();
        pagination.put("startnum", startnum);
        pagination.put("perPage", perPage);
        return reviewInter.getPagenationedReviews(pagination);
    }

    public int getAllReviewsCount(){
        return reviewInter.getAllReviewsCount();
    }
    public ReviewDto getReviewBySeq(int review_no){
        return reviewInter.getReviewBySeq(review_no);
    }
    public void addReview(ReviewDto reviewDto){
        reviewInter.addReview(reviewDto);
    }
    public void updateReview(ReviewDto reviewDto){
        reviewInter.updateReview(reviewDto);
    }
    public void deleteReview(int review_no){
        reviewInter.deleteReview(review_no);
    }
    public void updateViewcount(int review_no){
        reviewInter.updateViewcount(review_no);
    }
}

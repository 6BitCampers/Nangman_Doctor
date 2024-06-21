package com._6bitcampers.nangman_doctor.baedongwoo.data.service;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.mapper.ReviewInter;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
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
    public void updateReview(Map<String,Object> map){
        reviewInter.updateReview(map);
    }
    public void deleteReview(int review_no){
        reviewInter.deleteReview(review_no);
    }
    public void updateViewcount(int review_no){
        reviewInter.updateViewcount(review_no);
    }
    public userEntity getUserInfo(String user_email,String user_type){
        return reviewInter.getUserInfo(user_email,user_type);
    }
    public userEntity getUserInfoByNum(int user_no){
        return reviewInter.getUserInfoByNum(user_no);
    }
    public int getHospitalNo(int employee_no){
        return reviewInter.getHospitalNo(employee_no);
    }
    public String getHospitalName(int info_no){
        return reviewInter.getHospitalName(info_no);
    }
    public void insertReview(ReviewDto reviewDto){
        reviewInter.insertReview(reviewDto);
    }
}

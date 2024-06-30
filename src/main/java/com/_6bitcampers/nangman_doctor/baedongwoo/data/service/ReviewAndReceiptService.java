package com._6bitcampers.nangman_doctor.baedongwoo.data.service;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PcDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PillDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.mapper.ReviewAndReceiptInter;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewAndReceiptService {

    @Autowired
    private ReviewAndReceiptInter reviewAndReceiptInter;

    public List<ReviewDto> getPagenationedReviews(int startnum, int perPage, String orderBy) {
        Map<String,Object> pagination = new HashMap<>();

        pagination.put("startnum", startnum);
        pagination.put("perPage", perPage);
        pagination.put("orderBy", orderBy);

        return reviewAndReceiptInter.getPagenationedReviews(pagination);
    }

    public int getAllReviewsCount(){
        return reviewAndReceiptInter.getAllReviewsCount();
    }
    public ReviewDto getReviewBySeq(int review_no){
        return reviewAndReceiptInter.getReviewBySeq(review_no);
    }
    public void updateReview(Map<String,Object> map){
        reviewAndReceiptInter.updateReview(map);
    }
    public void deleteReview(int review_no){
        reviewAndReceiptInter.deleteReview(review_no);
    }
    public void updateViewcount(int review_no){
        reviewAndReceiptInter.updateViewcount(review_no);
    }
    public userEntity getUserInfo(String user_email,String user_type){
        return reviewAndReceiptInter.getUserInfo(user_email,user_type);
    }
    public userEntity getUserInfoByNum(int user_no){
        return reviewAndReceiptInter.getUserInfoByNum(user_no);
    }
    public int getHospitalNo(int employee_no){
        return reviewAndReceiptInter.getHospitalNo(employee_no);
    }
    public String getHospitalName(int info_no){
        return reviewAndReceiptInter.getHospitalName(info_no);
    }
    public void insertReview(ReviewDto reviewDto){
       reviewAndReceiptInter.insertReview(reviewDto);
    }
    public String getEmployeeName(int employee_no){
        return reviewAndReceiptInter.getEmployeeName(employee_no);
    }
    public PillDto getRandomPillInfo(String pill_act){
        return reviewAndReceiptInter.getRandomPillInfo(pill_act);
    }
    public PcDto getPcContents(int receipt_no){
        return reviewAndReceiptInter.getPcContents(receipt_no);
    }
    public PillDto getPillContent(int pill_no){
        return reviewAndReceiptInter.getPillContent(pill_no);
    }
    public void insertPcContent(PcDto pcDto){
        reviewAndReceiptInter.insertPcContent(pcDto);
    }
    public void updateReceiptPc(int prescription_no, int receipt_no){
        reviewAndReceiptInter.updateReceiptPc(prescription_no,receipt_no);
    }
    public List<ReviewDto> getReviewByEmployeeNo(int employee_no){
        return reviewAndReceiptInter.getReviewByEmployeeNo(employee_no);
    }
}

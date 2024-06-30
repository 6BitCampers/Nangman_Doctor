package com._6bitcampers.nangman_doctor.baedongwoo.data.mapper;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PcDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PillDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewAndReceiptInter {
    @Select("select * from hospital_review order by ${orderBy} limit #{startnum},#{perPage}")
    public List<ReviewDto> getPagenationedReviews(Map<String,Object> pagination);
    @Select("select count(*) from hospital_review")
    public int getAllReviewsCount();
    @Select("select * from hospital_review where review_no=#{review_no}")
    public ReviewDto getReviewBySeq(int review_no);
    @Update("update hospital_review set review_title=#{review_title}, review_content=#{review_content},review_likecount=#{review_likecount} where review_no=#{review_no}")
    public void updateReview(Map<String,Object> map);
    @Delete("delete from hospital_review where review_no=#{review_no}")
    public void deleteReview(int review_no);
    @Update("update hospital_review set review_viewcount=review_viewcount+1 where review_no=#{review_no}")
    public void updateViewcount(int review_no);
    @Select("select * from normal_user where user_email=#{user_email} and user_type=#{user_type}")
    public userEntity getUserInfo(String user_email,String user_type);
    @Select("select * from normal_user where user_no=#{user_no}")
    public userEntity getUserInfoByNum(int user_no);
    @Select("select info_no from hospital_employee where employee_no=#{employee_no}")
    public int getHospitalNo(int employee_no);
    @Select("select info_name from hospital_info where info_no=#{info_no}")
    public String getHospitalName(int info_no);
    @Insert("insert into hospital_review (review_title, review_content, review_writeday, employee_no, user_no, review_likecount) values (#{review_title},#{review_content},now(),#{employee_no},#{user_no},#{review_likecount})")
    @Options(useGeneratedKeys = true, keyColumn = "review_no", keyProperty = "review_no")
    public void insertReview(ReviewDto reviewDto);
    @Select("select employee_name from hospital_employee where employee_no=#{employee_no}")
    public String getEmployeeName(int employee_no);
    @Select("select * from pill_names where pill_act=#{pill_act} order by rand() limit 1")
    public PillDto getRandomPillInfo(String pill_act);
    @Select("select * from prescription_content where receipt_no=#{receipt_no}")
    public PcDto getPcContents(int receipt_no);
    @Select("select * from pill_names where pill_no=#{pill_no}")
    public PillDto getPillContent(int pill_no);
    @Insert("insert into prescription_content (pc_warranty_num, pc_regi_num, pc_disease_num1, pc_disease_num2, pc_pill_1, pc_pill_2, receipt_no) values (#{pc_warranty_num},#{pc_regi_num},#{pc_disease_num1}, #{pc_disease_num2}, #{pc_pill_1}, #{pc_pill_2}, #{receipt_no})")
    @Options(useGeneratedKeys = true, keyColumn = "pc_no", keyProperty = "pc_no")
    public void insertPcContent(PcDto pcDto);
    @Update("UPDATE hospital_receipt SET prescription_no = #{prescription_no} WHERE receipt_no=#{receipt_no}")
    public void updateReceiptPc(int prescription_no,int receipt_no);
    @Select("select * from hospital_review where employee_no=#{employee_no}")
    public List<ReviewDto> getReviewByEmployeeNo(int employee_no);

}

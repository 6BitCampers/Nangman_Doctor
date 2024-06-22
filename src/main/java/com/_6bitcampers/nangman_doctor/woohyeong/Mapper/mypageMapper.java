package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.woohyeong.dto.EmployeeDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.ReceiptDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface mypageMapper {
    @Select("""
    select * from normal_user where user_email = #{user_email}
""")
    UserDTO getUser(String user_email);

    @Select("""
    select * from hospital_employee where employee_email = #{employee_email}
""")
    EmployeeDTO getEmployee(String employee_email);

    @Select("""
    select * from hospital_receipt r left join hospital_reservation hr 
        on r.reservation_no = hr.reservation_no left join normal_user u on hr.user_no = u.user_no
    where u.user_no = #{user_no}
""")
    List<ReceiptDTO> getReceipt(int user_no);

    @Update("""
    update normal_user set user_name=#{user_name}, user_email=#{user_email}, user_hp = #{user_hp}, user_nickname=#{user_nickname}
    where user_no = #{user_no}
""")
    void updateUser(Map<String, Object> map);
    @Select("""
    select info_name from hospital_info where info_no=#{info_no}
""")
   String getInfoName(int info_no);
}

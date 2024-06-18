package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import com._6bitcampers.nangman_doctor.woohyeong.dto.ReservationDTOW;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface reservationMapperW {
    @Insert("""
    insert into hospital_reservation(reservation_reason, reservation_date, reservation_role, user_no)
    values(#{reservation_reason}, #{reservation_date}, #{reservation_role}, #{user_no})
""")
    void insertSurvey(ReservationDTOW dto);

    @Update("""
    update into hospital_reservation set employee_no = #{employee_no} 
                                     where reservation_no = #{reservation_no}
""")
    void updateSurvey(Map<String, Object> map);

    @Select("""
    select employee_no from hospital_employee where employee_email = #{employee_email}
""")
    int getEmployeeNo(String employee_email);
}

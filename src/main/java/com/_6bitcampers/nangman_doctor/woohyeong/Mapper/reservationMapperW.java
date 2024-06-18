package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import com._6bitcampers.nangman_doctor.woohyeong.dto.ReservationDTOW;
import org.apache.ibatis.annotations.*;

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

    @Delete("""
    delete from hospital_reservation where reservation_no = #{reservation_no}
""")
    void deleteSurvey(int reservation_no);

    @Select("""
    SELECT CASE
    WHEN EXISTS (SELECT 1 FROM hospital_reservation WHERE reservation_status = 3 AND user_no = #{user_no}) THEN 1
    ELSE 0
    END AS result;                                                                     
""")
    int getReservationStatus(int user_no);
}

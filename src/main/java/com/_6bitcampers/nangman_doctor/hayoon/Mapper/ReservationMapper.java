package com._6bitcampers.nangman_doctor.hayoon.Mapper;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReservationMapper {

    @Insert("INSERT INTO hospital_reservation (reservation_reason, reservation_date, reservation_role, employee_no, user_no, reservation_time) " +
            "VALUES (#{reservationReason}, #{reservationDate}, #{reservationRole}, #{employeeNo}, #{userNo}, #{reservationTime})")
    void insertReservation(ReservationDto dto);
    @Select("SELECT user_name FROM normal_user WHERE user_no = #{userNo}")
    String getUserNameByNo(String userNo);

    @Select("SELECT employee_email FROM hospital_employee WHERE employee_no = #{employeeNo}")
    String getEmployeemailByNo(int employeeNo);

    @Select("SELECT user_email FROM normal_user WHERE user_no = #{userNo}")
    String getUsermailByNo(int userNo);
}

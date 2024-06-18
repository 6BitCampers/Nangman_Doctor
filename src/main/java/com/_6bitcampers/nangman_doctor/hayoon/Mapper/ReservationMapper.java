package com._6bitcampers.nangman_doctor.hayoon.Mapper;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {

    @Insert("INSERT INTO hospital_reservation (reservation_room, reservation_reason, reservation_date, reservation_role, employee_no, user_no, reservation_time, reservation_status) " +
            "VALUES (#{reservationRoom}, #{reservationReason}, #{reservationDate}, #{reservationRole}, #{employeeNo}, #{userNo}, #{reservationTime}, 1)")
    void insertReservation(ReservationDto dto);

    @Select("SELECT user_name FROM normal_user WHERE user_no = #{userNo}")
    String getUserNameByNo(int userNo);

    @Select("SELECT employee_email FROM hospital_employee WHERE employee_no = #{employeeNo}")
    String getEmployeemailByNo(int employeeNo);

    @Select("SELECT user_email FROM normal_user WHERE user_no = #{userNo}")
    String getUsermailByNo(int userNo);

    @Select("SELECT hr.*, nu.user_name FROM hospital_reservation hr " +
            "JOIN normal_user nu ON hr.user_no = nu.user_no WHERE hr.user_no = #{userNo}")
    List<Map<String, Object>> getUserReservations(int userNo);




}

package com._6bitcampers.nangman_doctor.hayoon.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StatusMapper {
    @Update("UPDATE hospital_reservation SET reservation_status = 2 WHERE reservation_no = #{reservationNo}")
    void updateStatus(int reservationNo);

    @Delete("DELETE FROM hospital_reservation WHERE reservation_no = #{reservationNo}")
    void deleteByReservationNo(int reservationNo);

    @Update("UPDATE hospital_reservation SET reservation_room=#{reservationRoom} WHERE reservation_no = #{reservationNo}")
    void updateRoom(String reservationRoom,int reservationNo);

    @Select("SELECT reservation_status FROM hospital_reservation WHERE reservation_no= #{reservationNo}")
    int getStatus(int reservationNo);


    @Select("SELECT he.info_no FROM hospital_reservation hr JOIN hospital_employee he ON hr.employee_no = he.employee_no WHERE hr.reservation_no = #{reservationNo}")
    int getInfoNoByReservationNo(int reservationNo);


}

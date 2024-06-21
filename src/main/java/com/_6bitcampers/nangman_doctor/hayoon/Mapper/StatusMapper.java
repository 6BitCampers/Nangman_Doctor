package com._6bitcampers.nangman_doctor.hayoon.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StatusMapper {
    @Update("UPDATE hospital_reservation SET reservation_status = reservation_status + 1 WHERE reservation_no = #{reservationNo}")
    void updateStatus(int reservationNo);
}

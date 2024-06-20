package com._6bitcampers.nangman_doctor.woohyeong.Mapper;

import com._6bitcampers.nangman_doctor.woohyeong.dto.ReceiptDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.ReservationDTOW;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface conferenceMapper {
    @Select("""
    select user_no from normal_user where user_email = #{user_email}
""")
    int getUserNo(String user_email);

    @Select("""
    select * from hospital_reservation r 
        left join normal_user u on u.user_no = r.user_no
        where employee_no = #{employee_no}
""")
    ReservationDTOW getReservation(int employee_no);

    @Insert("""
    insert into hospital_receipt
        (receipt_name, receipt_status, receipt_reason, receipt_date, reservation_no, receipt_amount, info_no)
    values(#{receipt_name}, #{receipt_status}, #{receipt_reason}, now(), #{reservation_no}, #{receipt_amount}, #{info_no})
""")
    void insertRecipt(ReceiptDTO dto);

    @Select("""
    select i.info_no from hospital_reservation r left join hospital_employee e on r.employee_no = e.employee_no
    left join hospital_info i on i.info_no = e.info_no where r.reservation_no = #{reservation_no}
""")
    int getInfoNo(int reservation_no);
}

package com._6bitcampers.nangman_doctor.hayoon.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationDto {
    private int reservation_no;
    private String reservation_name;
    private String reservation_reason;
    private String reservation_date;
    private int reservation_role;
    private int employee_no;
    private int user_no;
    private String reservation_time;
    private int reservation_status;
    private String reservation_room;
    private String reservation_content;

}
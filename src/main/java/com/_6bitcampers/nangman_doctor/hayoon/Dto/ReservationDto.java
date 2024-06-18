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
    private String reservationRoom;
    private String name;
    private int reservationNo; // Add this field
    private String reservationReason;
    private String reservationDate;
    private int reservationRole;
    private int employeeNo; // Default value set to 3
    private int userNo; // Default value set to 7
    private String reservationTime;
    private int reservationStatus;
}
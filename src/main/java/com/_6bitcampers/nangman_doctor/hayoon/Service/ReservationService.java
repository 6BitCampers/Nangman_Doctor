package com._6bitcampers.nangman_doctor.hayoon.Service;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final JavaMailSender mailSender;

    @Autowired
    public ReservationService(ReservationMapper reservationMapper, JavaMailSender mailSender) {
        this.reservationMapper = reservationMapper;
        this.mailSender = mailSender;
    }

    public void saveReservation(ReservationDto reservationDto) {
        reservationMapper.insertReservation(reservationDto);
        String employeeEmail = reservationMapper.getEmployeemailByNo(reservationDto.getEmployeeNo());
        String userEmail = reservationMapper.getUsermailByNo(reservationDto.getUserNo());
        sendConfirmationEmail(employeeEmail, userEmail, reservationDto);
    }
//첨에 유저가 예약폼입력하면 병원,유저에 예약 확인 보냄
    public void sendConfirmationEmail(String employeeEmail, String userEmail, ReservationDto dto) {
        SimpleMailMessage messageToUser = new SimpleMailMessage();
        messageToUser.setTo(userEmail);
        messageToUser.setSubject("예약 신청함");
        messageToUser.setText("예약 신청 이거맞쥬? " + dto.getReservationDate() +
                " at " + dto.getReservationTime() + ".");
        mailSender.send(messageToUser);

        SimpleMailMessage messageToHos = new SimpleMailMessage();
        messageToHos.setTo(employeeEmail);
        messageToHos.setSubject("예약 요청옴");
        messageToHos.setText("예약 요청 확인하세여 " + dto.getReservationDate() +
                " at " + dto.getReservationTime() + ".");
        mailSender.send(messageToHos);
    }
    //첨에 병원이 예약 수락하면 메일 보냄

    public void sendHoscheckEmail(String employeeEmail, String userEmail, ReservationDto dto) {
        SimpleMailMessage messageToUser = new SimpleMailMessage();
        messageToUser.setTo(userEmail);
        messageToUser.setSubject("예약 가능");
        messageToUser.setText("예약 가능함 " + dto.getReservationDate() +
                " at " + dto.getReservationTime() + ".");
        mailSender.send(messageToUser);

        SimpleMailMessage messageToHos = new SimpleMailMessage();
        messageToHos.setTo(employeeEmail);
        messageToHos.setSubject("예약 가능하다고 보냄");
        messageToHos.setText("예약 가능하다고 보냄 " + dto.getReservationDate() +
                " at " + dto.getReservationTime() + ".");
        mailSender.send(messageToHos);
    }
//유저가 완전히 확정
    public void sendUserchkEmail(String employeeEmail, String userEmail, ReservationDto dto) {
        SimpleMailMessage messageToUser = new SimpleMailMessage();
        messageToUser.setTo(userEmail);
        messageToUser.setSubject("예약 찐 확");
        messageToUser.setText("예약 찐 확 " + dto.getReservationDate() +
                " at " + dto.getReservationTime() + ".");
        mailSender.send(messageToUser);

        SimpleMailMessage messageToHos = new SimpleMailMessage();
        messageToHos.setTo(employeeEmail);
        messageToHos.setSubject("예약 예약 찐 확임");
        messageToHos.setText("예약 예약 찐 확임 " + dto.getReservationDate() +
                " at " + dto.getReservationTime() + ".");
        mailSender.send(messageToHos);
    }



}

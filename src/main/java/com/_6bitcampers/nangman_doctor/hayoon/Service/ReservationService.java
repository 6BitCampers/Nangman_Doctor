package com._6bitcampers.nangman_doctor.hayoon.Service;

import com._6bitcampers.nangman_doctor.hayoon.Dto.HosInfoDto;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
import com._6bitcampers.nangman_doctor.search.HospitalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReservationService {

    private static final int DEFAULT_EMPLOYEE_NO = 3; // 기본 관리자 ID

    private  ReservationMapper reservationMapper;
    private  JavaMailSender mailSender;
    private  TemplateEngine templateEngine;


    public ReservationService(ReservationMapper reservationMapper, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.reservationMapper = reservationMapper;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    // 예약 저장
    public void saveReservation(ReservationDto reservationDto) {

        // Insert the reservation
        reservationMapper.insertReservation(reservationDto);

        // Get email addresses
        String employeeEmail = reservationMapper.getEmployeemailByNo(reservationDto.getEmployee_no());
        String userEmail = reservationMapper.getUsermailByNo(reservationDto.getUser_no());

        // 예약 보내기
        sendReservationRequestEmail(userEmail, reservationDto);
        sendReservationRequestEmail(employeeEmail, reservationDto);
    }

    public int getUserNo(String id) {
        return reservationMapper.getUserNo(id);
    }

    public List<Map<String, Object>> getUserReservations(int userNo) {
        return reservationMapper.getUserReservations(userNo);
    }

    public List<ReservationDto> getReservationsByEmail(String email){
        return reservationMapper.getReservationsByEmail(email);
    }

    public void sendReservationRequestEmail(String to, ReservationDto reservationDto) {
        //System.out.println("dd" + reservationMapper.getUserNameByNo(reservationDto.getUser_no()));
        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", reservationMapper.getUserNameByNo(reservationDto.getUser_no()));
        variables.put("reservation", reservationDto);
       // System.out.println("ww" + variables);

        sendEmail(to, "예약 요청", "emailTemplates/firstuseremail", variables);
        sendEmail(to,"예약 요청 확인","emailTemplates/firsthosemail",variables);
    }

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariables(variables);

            // Load HTML template using TemplateEngine
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public int getEmployeeNoByInfoNo(int infoNo) {
        Integer employeeNo = reservationMapper.getEmployeeNoByInfoNo(infoNo);
        return (employeeNo != null) ? employeeNo : DEFAULT_EMPLOYEE_NO;
    }

    public HosInfoDto getHosdto(int infoNo) {
        return reservationMapper.getHosdto(infoNo);
    }

    public String getUserNameByNo(int infoNo){
        return reservationMapper.getUserNameByNo(infoNo);
    }




}

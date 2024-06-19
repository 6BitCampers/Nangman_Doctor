package com._6bitcampers.nangman_doctor.hayoon.Service;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
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

    private final ReservationMapper reservationMapper;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public ReservationService(ReservationMapper reservationMapper, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.reservationMapper = reservationMapper;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    //예약 저장
    public void saveReservation(ReservationDto reservationDto) {
// Generate a random UUID and set it to the reservationDto
        reservationDto.setReservationRoom(UUID.randomUUID().toString());


        // Insert the reservation
        reservationMapper.insertReservation(reservationDto);

        // Get email addresses
        String employeeEmail = reservationMapper.getEmployeemailByNo(reservationDto.getEmployeeNo());
        String userEmail = reservationMapper.getUsermailByNo(reservationDto.getUserNo());

        // 예약보내기
        sendReservationRequestEmail(userEmail, reservationDto);
        sendReservationRequestEmail(employeeEmail, reservationDto);
    }
    public int getUserNo(String id){
        return reservationMapper.getUserNo(id);
    }

    public List<Map<String, Object>> getUserReservations(int userNo) {
        return reservationMapper.getUserReservations(userNo);
    }

    public void sendReservationRequestEmail(String to, ReservationDto reservationDto) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", reservationMapper.getUserNameByNo(reservationDto.getUserNo()));
        variables.put("reservation", reservationDto);

        sendEmail(to, "예약 요청", "firstemail", variables);
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
}
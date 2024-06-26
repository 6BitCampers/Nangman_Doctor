package com._6bitcampers.nangman_doctor.hayoon.Service;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.StatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final ReservationMapper reservationMapper;
    private final StatusMapper statusMapper;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void updateStatus(int reservationNo) {
        statusMapper.updateStatus(reservationNo);
    }

    public void deleteByReservationNo(int reservationNo) {
        statusMapper.deleteByReservationNo(reservationNo);
    }

    public void updateRoom(String reservationRoom, int reservationNo) {
        statusMapper.updateRoom(reservationRoom, reservationNo);
    }

    public int getStatus(int reservationNo) {
        return statusMapper.getStatus(reservationNo);
    }
    @Async
    public void sendReservationRequestEmail(String to, ReservationDto reservationDto) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("url", "http://deploysemi.midichi.kro.kr/");
        variables.put("userName", reservationMapper.getUserNameByNo(reservationDto.getUser_no()));
        variables.put("reservation", reservationDto);

        sendEmail(to, "예약 요청", "emailTemplates/secondemail", variables);
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

    public int getInfoNoByReservationNo(int reservationNo) {
        return statusMapper.getInfoNoByReservationNo(reservationNo);
    }
}

package com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestService;

import com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestMappper.joinRestMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class searchPwService {
    private final joinRestMapper joinRestMapper;
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${pw_reset_url}")
    private String defaultUrl;

    public boolean searchPassword(String email, String name) {
        if (joinRestMapper.existByEmailAndName(email, name)) {
            return false;
        } else {
            String url = UUID.randomUUID().toString();
            if (joinRestMapper.findTableByEmailAndName(email, name).equals("normal_user")) {
                //update 유저 정보(user_role)
                joinRestMapper.updateNormalUserByEmail(email);
            } else {
                joinRestMapper.updateEmployeeUserByEmail(email);
            }
            joinRestMapper.insertPWInitUrl(url,email);
            //email 전송
            sendTemplete(name,url,email);
            return true;
        }
    }

    public void sendTemplete(String name, String url,String email){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            //메일 제목 설정
            helper.setSubject(name+"님이 요청하신 비밀번호 초기화 이메일입니다.");
            //수신자 설정
            helper.setTo(email);

            //참조자 설정
            helper.setCc(sender);

            //템플릿에 전달할 데이터 설정
            HashMap<String, String> emailValues = new HashMap<>();
            emailValues.put("userName", name);
            emailValues.put("url",defaultUrl.replace("\"","")+url);

            Context context = new Context();
            emailValues.forEach(context::setVariable);

            //메일 내용 설정 : 템플릿 프로세스
            String html = springTemplateEngine.process("/emailTemplates/passwordforgot", context);
            helper.setText(html, true);

            //메일 보내기
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}

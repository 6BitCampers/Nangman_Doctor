package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinMapper.joinMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class joinService {
    private final joinMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${join_vaild_url}")
    private String defaultUrl;

    public void joinService(joinRequestDto dto) {
        String row_pw = dto.getPassword();
        dto.setPassword(passwordEncoder.encode(row_pw));
        if (dto.getType().equals("general")) {
            mapper.insertNormalUserDefault(dto);
        } else {
            mapper.insertEmployeeUserDefault(dto);
        }
        String uuid = UUID.randomUUID().toString();
        String email = dto.getEmail();
        mapper.insertEmailVaild(uuid, email);
        this.sendTemplete(dto.getName(),uuid,dto.getEmail());
    }

    public void sendTemplete(String name, String url,String email){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            //메일 제목 설정
            helper.setSubject(name+"님의 계정인증 이메일입니다.");
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
            String html = springTemplateEngine.process("emailTemplates/emailvaild", context);
            helper.setText(html, true);

            //메일 보내기
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean vaildEmail(String uuid){
        if (mapper.existEmailVaild(uuid)){
            String email = mapper.findOneEmailVaild(uuid);
            mapper.updateUserRole(email);
            mapper.updateEmployeeRole(email);
            mapper.deleteEmailVaild(uuid);
            return true;
        }
        else return false;
    }
}

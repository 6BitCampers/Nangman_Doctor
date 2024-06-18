package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.woohyeong.Service.conferenceService;
import com._6bitcampers.nangman_doctor.woohyeong.Service.reservationServiceW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class conferenceController {

    @Autowired
    private conferenceService conferenceService;

    @Autowired
    private reservationServiceW reservationService;

    @GetMapping("/test")
    public String home(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean employeeNoSuccess = false;
        boolean userNoSuccess = false;

        startNodeServer();

        try {
            int employee_no = reservationService.getEmployeeNo(id);
            employeeNoSuccess = true;
        } catch (Exception e) {
        }

        try {
            int user_no = conferenceService.getUserNo(id);
            userNoSuccess = true;
        } catch (Exception e) {
        }

        if(userNoSuccess){
            int user_no = conferenceService.getUserNo(id);
            return "redirect:/payment?user_no=" + user_no;
        }

        if(employeeNoSuccess){
            return "redirect:/";
        }

        return "redirect:/errorPage";

    }

    private void startNodeServer() {
        try {
            // npm 실행 명령어 설정 (Mac 기준)
            String command = "/usr/local/bin/npm run dev"; // npm이 있는 실제 경로를 설정해야 합니다.
            // Node.js 프로젝트 디렉토리 설정
            File directory = new File("/Users/uhyeonge/Desktop/noom");

            // ProcessBuilder를 사용하여 외부 프로세스 실행
            ProcessBuilder builder = new ProcessBuilder(command.split("\\s+"));
            builder.directory(directory);

            // 프로세스 실행
            Process process = builder.start();

            // Node.js 서버가 준비되기를 기다릴 수도 있음
            Thread.sleep(5000); // 예: 5초 대기

            // 세션 데이터를 Node.js 서버로 전송
//            sendSessionDataToNodeServer(name);

            // 브라우저 열기
            openBrowser("https://192.168.0.18:3000");

            System.out.println("Node.js 서버가 시작되었습니다.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void sendSessionDataToNodeServer(String name) {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            String url = "http://192.168.0.18:3000/receive-session-data";
//
//            // 세션 데이터
//            Map<String, Object> sessionData = new HashMap<>();
//            sessionData.put("name", name);
//            sessionData.put("user", "exampleUser");
//            sessionData.put("role", "exampleRole");
//
//            restTemplate.postForObject(url, sessionData, String.class);
//            System.out.println("세션 데이터를 Node.js 서버로 전송했습니다.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

package com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestController;


import com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestMappper.joinRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class joinRestController {
    private final joinRestMapper mapper;

    @GetMapping("/emailcheck")
    public Map<String, String> checkEmail(String email) {
        Map<String, String> map = new HashMap<>();
        String status = mapper.findByEmailEmployee(email)+mapper.findByEmailUser(email) == 0?"true":"false";
        map.put("status", status);
        return map;
    }

    @GetMapping("/nicknamecheck")
    public Map<String, String> checkNickname(String nickname) {
        Map<String, String> map = new HashMap<>();
        String status = mapper.findByNicknameEmployee(nickname)+mapper.findByNicknameUser(nickname) == 0?"true":"false";
        map.put("status",status);
        return map;
    }
}

package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.woohyeong.Mapper.InfoMapper;
import com._6bitcampers.nangman_doctor.woohyeong.dto.InfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class InfoController {

    @Autowired
    private InfoMapper infoMapper;

    @GetMapping("/info")
    public List<InfoDTO> getInfoByName(@RequestParam String info_name) {
        try {
            System.out.println("서치인포로 왔어요1");
            String keyword = "%" + info_name + "%";
            System.out.println("검색어: " + keyword);
            List<InfoDTO> results = infoMapper.getInfoByName(keyword);
            System.out.println("검색 결과: " + results);
            return results;
        } catch (Exception e) {
            throw e;
        }
    }
}

package com._6bitcampers.nangman_doctor.woohyeong.controller;

import com._6bitcampers.nangman_doctor.woohyeong.Mapper.InfoMapper;
import com._6bitcampers.nangman_doctor.woohyeong.dto.InfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "병원명 검색 API Controller", description = "병원명 실시간 검색을 위한 Rest API 컨트롤러 입니다.")
@RestController
@RequestMapping("/search")
public class InfoController {

    @Autowired
    private InfoMapper infoMapper;

    @Operation(operationId = "/search/info",summary = "병원명 검색",description = "병원명 검색시 별도의 제출(submit)없이 검색기능 제공",responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회된 병원명이 있으면 info_name 출력",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "status", schema = @Schema(type = "boolean", description = "조회 성공 여부"))
                            }
                    )
            )
    })

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

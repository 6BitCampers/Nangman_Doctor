package com._6bitcampers.nangman_doctor.baedongwoo.controller;

import com._6bitcampers.nangman_doctor.woohyeong.Service.mypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Tag(name = "MyPage Api Controller",description = "마이페이지에서 병원 이름 받아오는 api controller")
@RestController
@RequiredArgsConstructor
public class MyPageAPIController {
    private final mypageService mypageService;

    @Operation(operationId = "GetHospitalName",summary = "마이페이지 예약된 병원 이름들 가져오기", description = "사용자가 예약한 병원 이름들을 반환시킨다",
    responses ={
            @ApiResponse(
                    responseCode = "200",
                    description = "예약된 병원들 이름 반환",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "info_name",schema = @Schema(type = "string",description = "병원 이름"))
                            }
                    )
            )

    })
    @GetMapping("/getHospitalName")
    public Map<String,String> getHospitalName(@RequestParam int info_no) {
        Map<String,String> map=new HashMap<>();
        String info_name=mypageService.getInfoName(info_no);
        map.put("info_name", info_name);
        return map;
    }
}

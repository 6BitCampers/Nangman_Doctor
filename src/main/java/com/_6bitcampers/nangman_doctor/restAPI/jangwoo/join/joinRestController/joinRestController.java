package com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestController;


import com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestMappper.joinRestMapper;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestService.searchPwService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "회원가입 API Controller", description = "회원가입을 위한 Rest API 컨트롤러 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class joinRestController {
    private final joinRestMapper mapper;
    private final searchPwService pwService;

    @Operation(operationId = "EmailCheck",summary = "사용자 이메일 중복 체크",description = "회원가입 전 사용자의 이메일이 중복되지 않게 체크한다.",responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회된 사용자가 있으면 True",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "status", schema = @Schema(type = "boolean", description = "조회 성공 여부"))
                            }
                    )
            )
    })
    @GetMapping("/emailcheck")
    public Map<String, String> checkEmail(@Parameter(name = "email",description = "사용자 이메일",in = ParameterIn.QUERY,example = "aaa@gmail.com") String email) {
        Map<String, String> map = new HashMap<>();
        String status = mapper.findByEmailEmployee(email)+mapper.findByEmailUser(email) == 0?"true":"false";
        map.put("status", status);
        return map;
    }

    @Operation(operationId = "NicknameCheck",summary = "사용자 닉네임 중복 체크",description = "회원가입 전 사용자의 닉네임이 중복되지 않게 체크한다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회된 사용자가 있으면 True",
                            content = @Content(
                                    schemaProperties = {
                                            @SchemaProperty(name = "status", schema = @Schema(type = "boolean", description = "조회 성공 여부"))
                                    }
                            )
                    )
            })
    @GetMapping("/nicknamecheck")
    public Map<String, String> checkNickname(@Parameter(name = "nickname",description = "사용자 닉네임",in = ParameterIn.QUERY,example = "짱구") String nickname) {
        Map<String, String> map = new HashMap<>();
        String status = mapper.findByNicknameEmployee(nickname)+mapper.findByNicknameUser(nickname) == 0?"true":"false";
        map.put("status",status);
        return map;
    }

    @Operation(operationId = "ForgetPW",summary = "사용자 패스워드 찾기",description = "회원가입 시 입력한 회원의 이름,이메일 기반으로 계정 체크")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "406", description = "User Not Found",content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("/searchPW")
    public ResponseEntity<Object> searchPW(@Param("email")@Parameter(name = "email",description = "사용자 이메일",in = ParameterIn.QUERY,example = "aaa@gmail.com") String email,
                                           @Param("name")@Parameter(name = "name",description = "사용자 이름",in = ParameterIn.QUERY,example = "박보민") String name) {

        if (pwService.searchPassword(email,name)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(406).build();
        }
    }
}

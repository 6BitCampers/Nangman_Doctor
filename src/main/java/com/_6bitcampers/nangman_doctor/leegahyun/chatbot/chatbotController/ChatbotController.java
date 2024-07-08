package com._6bitcampers.nangman_doctor.leegahyun.chatbot.chatbotController;
import com._6bitcampers.nangman_doctor.leegahyun.chatbot.chatbotService.ChatbotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chatbot Api Controller",description = "챗봇 채팅 기능을 구현하기 위한 api controller입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatbotController {
    final ChatbotService chatbotService;

    @Operation(operationId = "ChatbotMessageSend", summary = "입력받은 텍스트를 처리하는 API", description = "입력된 텍스트를 기반으로 챗봇의 응답을 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "응답 성공",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 에러",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            })
    @PostMapping(value = "/chatbotSend", produces = "application/json;charset=UTF-8;")
    @ResponseBody
    public ResponseEntity<String> chatbotSend(@RequestParam("inputText") @Parameter(name = "inputText", description = "사용자가 입력한 텍스트", in = ParameterIn.QUERY, example = "안녕하세요") String inputText) {
        String msg = chatbotService.main(inputText);
        return ResponseEntity.ok(msg);
    }

    @Operation(operationId = "ChatbotPage", summary = "챗봇 페이지 렌더링", description = "챗봇 채팅 페이지를 렌더링합니다.")
    @GetMapping("/chat")
    public String chatbot() {
        return "chatbot";  // templates/chatbot.html 파일을 렌더링
    }


}


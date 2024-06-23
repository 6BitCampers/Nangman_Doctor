package com._6bitcampers.nangman_doctor.leegahyun.chatbot.chatbotController;
import com._6bitcampers.nangman_doctor.leegahyun.chatbot.chatbotService.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatbotController {
    final ChatbotService chatbotService;

    @PostMapping(value = "/chatbotSend",produces = "application/json;charset=UTF-8;")
    @ResponseBody
    public String chatbotSend(@RequestParam("inputText") String inputText) {
        String msg = "";
        msg = chatbotService.main(inputText);
        System.out.println(msg);
        return msg;
    }
    @GetMapping("/chat")
    public String chatbot() {
        return "chatbot";  // templates/chatbot.html 파일을 렌더링
    }

//    @Controller
//    public static class HomeController {
//
//        @GetMapping("/")
//        public String index() {
//            return "index.html";
//        }
//    }
}


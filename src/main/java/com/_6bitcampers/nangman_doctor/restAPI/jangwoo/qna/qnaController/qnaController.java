package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaController;


import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.commentDto;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaService.qnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class qnaController {
    private final qnaService service;

    @GetMapping("/checkcomment")
    public List<commentDto> selectAllcomment(@RequestParam("qna_no") int a){
        return service.findAllComment(a);
    }

    @PostMapping("/insertComment")
    public void insertComment(@RequestParam("board_no") int board_no,
                              @RequestParam("comment_comment")String comment) {

    }

    @PostMapping("/insertBoard")
    public void insertBoard(@RequestParam("title") String title,
                            @RequestParam("comment") String comment,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam(value = "image", defaultValue = "") String image) {

    }

    @GetMapping("/deleteComment")
    public ResponseEntity<Object> deleteComment() {
        return ResponseEntity.ok().build();
    }
}

package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaController;


import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.commentDto;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaService.qnaService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Object> insertComment(@RequestParam("board_no") int board_no,
                              @RequestParam("comment_comment")String comment) {
        CustomUserDetails oAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        service.insertComment(board_no,comment,oAuth2User.getEmail(),oAuth2User.getType());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/insertBoard")
    public ResponseEntity<Object> insertBoard(@Param("title")String title,
                                              @Param("comment")String comment,
                                              @Param("username")String username,
                                              @Param("password")String password,
                                              @Param("image") MultipartFile image) {
        service.insertBoard(title,comment,username,password,image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deleteComment")
    public ResponseEntity<Object> deleteComment(@RequestParam("username") String username,
                                                @RequestParam("password") String password,
                                                @RequestParam("qna_no") int qna_no) {

        if (service.deleteComment(username, password, qna_no)) {
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(201).build();
    }

    @GetMapping("/searchPW")
    public ResponseEntity<Object> searchPw(@RequestParam("email")String email,
                                           @RequestParam("name") String name) {
        return ResponseEntity.ok().build();
    }
}

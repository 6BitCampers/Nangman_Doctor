package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaController;


import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.commentDto;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaService.qnaService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "익명게시판 API Controller", description = "익명 게시판 CRUD 구현을 위한 Rest API 컨트롤러 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class qnaController {
    private final qnaService service;

    @Operation(operationId = "CommentCheck", summary = "댓글 확인", description = "특정 게시물에 달린 모든 댓글을 확인하는 API입니다.")
    @GetMapping("/checkcomment")
    public List<commentDto> selectAllcomment(@RequestParam("qna_no") @Parameter(name = "게시글 ID", description = "게시글에 부여한 고유 ID", in = ParameterIn.QUERY, example = "1") int a) {
        return service.findAllComment(a);
    }

    @Operation(operationId = "InsertComment", summary = "댓글 작성", description = "특정 게시물에 댓글을 작성하는 API입니다.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "작성 성공",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    @PostMapping("/insertComment")
    public ResponseEntity<Object> insertComment(@RequestPart("board_no") @Parameter(name = "게시글 ID", description = "게시글에 부여한 고유 ID", example = "1") int board_no,
                                                @RequestPart("comment_comment") @Parameter(name = "작성한 댓글 내용", description = "작성한 댓글 내용 입력값", example = "안녕하세요.") String comment) {
        CustomUserDetails oAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        service.insertComment(board_no, comment, oAuth2User.getEmail(), oAuth2User.getType());

        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "InsertBoard", summary = "게시글 작성", description = "새로운 익명게시판에 글을 작성하는 API입니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "작성 완료",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            })
    @PostMapping(value = "/insertBoard",consumes = {"multipart/form-data"})
    public ResponseEntity<Object> insertBoard(@RequestPart("title") @Parameter(name = "게시글 제목", description = "작성자가 게시글을 작성할 때의 제목") String title,
                                              @RequestPart("comment") @Parameter(name = "게시글 내용", description = "작성자가 게시글을 작성할 때의 내용") String comment,
                                              @RequestPart("username") @Parameter(name = "작성자", description = "작성자가 게시글을 작성할 때의 익명 이름") String username,
                                              @RequestPart("password") @Parameter(name = "작성자 패스워드", description = "작성자가 게시글을 작성할 때의 익명 이름의 패스워드") String password,
                                              @RequestPart(value = "image",required = false) @Parameter(name = "게시글 내용의 이미지", description = "작성자가 게시글을 작성할 때의 삽입한 이미지") MultipartFile image) {
        service.insertBoard(title,comment,username,password,image);
        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "DeleteBoard", summary = "게시글 삭제", description = "익명게시판에 글을 삭제하는 API입니다.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "삭제 완료",
                    content = @Content(schema = @Schema(implementation = Void.class))
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "삭제 실패",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    @GetMapping("/deleteComment")
    public ResponseEntity<Object> deleteComment(@RequestParam("username") @Parameter(name = "작성자", description = "작성자가 익명으로 설정한 닉네임", in = ParameterIn.QUERY, example = "익명1", required = true) String username,
                                                @RequestParam("password") @Parameter(name = "작성자 암호", description = "작성자가 익명으로 설정한 닉네임에 대한 암호", in = ParameterIn.QUERY, example = "암호1", required = true) String password,
                                                @RequestParam("qna_no") @Parameter(name = "게시글 ID", description = "게시글에 부여한 고유 ID", in = ParameterIn.QUERY, example = "1") int qna_no) {

        if (service.deleteComment(username, password, qna_no)) {
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(201).build();
    }
}

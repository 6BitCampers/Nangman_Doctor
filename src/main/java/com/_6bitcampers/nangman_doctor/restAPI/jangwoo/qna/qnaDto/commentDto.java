package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("commentDto")
public class commentDto {
    @Schema(description = "댓글 내용", defaultValue = "안녕하세요.", requiredMode = Schema.RequiredMode.REQUIRED, example = "안녕하세요.")
    private String comment_comment;
    @Schema(description = "댓글 작성자", defaultValue = "병원 관계자1", requiredMode = Schema.RequiredMode.REQUIRED, example = "병원 관계자1")
    private String employee_nickname;
    @Schema(description = "게시글 ID", defaultValue = "1", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private int qna_no;
}

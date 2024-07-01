package com._6bitcampers.nangman_doctor.baedongwoo.controller.reviewboard;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewAndReceiptService;
import com._6bitcampers.nangman_doctor.minio.service.storageService;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tag(name = "Review Api Controller",description = "리뷰 CURD 시 업로드 되는 이미지 파일 관리를 위한 API controller")
@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewAndReceiptService reviewAndReceiptService;
    private final storageService storageService;
    private static final String filepath="http://minioDB.midichi.kro.kr/nangmandoctor";

    @Operation(operationId = "ReviewDelete",summary = "리뷰 삭제 api", description = "리뷰 번호를 보내서 해당 리뷰를 삭제시킵니다",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "해당 리뷰 삭제 완료",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "status",schema = @Schema(type = "string", description = "성공여부"))
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "해당하는 리뷰 없음",
                    content = @Content(
                            schema = @Schema(implementation = Void.class)
                    )
            )
    })
    @GetMapping("/reviewboard/delete")
    public ResponseEntity<String> deleteReview(
            @RequestParam("review_no")@Parameter(name = "영수증 시퀀스",example = "1") int review_no
    ){
        ReviewDto dto= reviewAndReceiptService.getReviewBySeq(review_no);
        String review_content=dto.getReview_content();
        //삭제시킬 이미지 UUID 갖고오기
        List<String> deletedUrls=extractImageUrlsFromDeleting(review_content);

        //갖고온 UUID 하나하나 delete 하기
        for(String deletedUrl:deletedUrls){
            storageService.deleteFile("nangmandoctor","/reviewBoard/"+deletedUrl);
        }

        reviewAndReceiptService.deleteReview(review_no);

        return ResponseEntity.status(200).build();
    }

    @Operation(operationId = "ReviewUploadImage",summary = "이미지 업로드 시키는 api",description = "이미지를 db에 업로드 시킨다",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "업로드 완료",
                    content = @Content(
                            schema = @Schema(implementation = Void.class))
            )
    })
    @PostMapping(value="/reviewboard/uploadSummernoteImageFile", produces = "application/json")
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") @Parameter(name = "file",description = "올리는 파일",example = "kakaotalk_202406291103.jpg") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        String filename = UUID.randomUUID() + "";

        try {
            storageService.uploadFile("nangmandoctor", "/temp/" + filename, multipartFile.getInputStream(), multipartFile.getContentType());
            jsonObject.addProperty("url", filepath+"/temp/"+filename);
            jsonObject.addProperty("filename", filename);
            jsonObject.addProperty("responseCode", "success");
        } catch (IOException e) {
            storageService.deleteFile("nangmandoctor", "/temp/"+filename);
            jsonObject.addProperty("responseCode", "error");
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    //삭제하는 리뷰에 저장된 이미지 이름 가져오기
    private List<String> extractImageUrlsFromDeleting(String content) {
        List<String> deletedUrls = new ArrayList<>();
        String regex = "/reviewBoard/([^\"']+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            deletedUrls.add(matcher.group(1));
        }

        return deletedUrls;
    }

}

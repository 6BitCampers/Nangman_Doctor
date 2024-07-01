package com._6bitcampers.nangman_doctor.baedongwoo.controller.payment;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.PaymentDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReceiptDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.PaymentService;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewAndReceiptService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Payment Api Controller",description = "결제성공 페이지 출력시 필요한 api controller")
@RestController
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService paymentService;
    private final ReviewAndReceiptService reviewAndReceiptService;

    @Operation(operationId = "PaymentSuccessPage",summary = "결제 성공 페이지에 필요한 정보를 가져오는 api",description = "로그인 된 유저 정보와 receipt_no를 받아서 결제 성공 페이지에 출력할 데이터를 반환시킵니다")
    @ApiResponses(value= {
            @ApiResponse(
                    responseCode = "200",
                    description = "결제 성공 페이지에 출력할 데이터 반환 성공",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "receiptDto", schema = @Schema(type = "ReceiptDto", description = "영수증 정보 가져오는 dto")),
                                    @SchemaProperty(name = "paymentDto", schema = @Schema(type = "PaymentDto", description = "결제 정보 담아오는 dto")),
                                    @SchemaProperty(name = "empDto", schema = @Schema(type = "EmpDto", description = "병원과 의사 정보를 담아오는 dto")),
                                    @SchemaProperty(name = "userEntity", schema = @Schema(type = "userEntity", description = "유저 정보 가져오는 entity")),
                                    @SchemaProperty(name = "method", schema = @Schema(type = "string", description = "결제 방법")),
                                    @SchemaProperty(name = "receipt_no", schema = @Schema(type = "integer", description = "결제 번호")),
                                    @SchemaProperty(name = "amount", schema = @Schema(type = "integer", description = "결제 금액")),
                                    @SchemaProperty(name = "user_no", schema = @Schema(type = "string", description = "리뷰 쓰기 버튼에 mapping 해줄 유저정보"))
                            }
                    )
            ),@ApiResponse(responseCode = "400",description = "잘못된 요청 형식",
            content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping("/payment/success")
    public Map<String,Object> paySuccess(
            @RequestParam int receipt_no
    ){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_type=customOAuth2User.getType();

        ReceiptDto receiptDto=paymentService.getReceiptBySeq(receipt_no);
        int amount=receiptDto.getReceipt_amount();
        int payment_no=receiptDto.getPayment_no();

        PaymentDto paymentDto=paymentService.getPayment(payment_no);
        String method=paymentDto.getPayment_method();
        int user_no=paymentDto.getUser_no();

        int hospital_no=receiptDto.getInfo_no();
        userEntity userEntity= reviewAndReceiptService.getUserInfo(userId, user_type);
        EmpDto empDto= paymentService.gethospitalInfo(hospital_no);
        String user_email=userEntity.getUser_email();

        Map<String,Object> response=new HashMap<>();

        String infoName=empDto.getInfo_name();

        response.put("receiptDto", receiptDto);
        response.put("paymentDto", paymentDto);
        response.put("empDto", empDto);
        response.put("userEntity", userEntity);
        response.put("method", method);
        response.put("receipt_no", receipt_no);
        response.put("amount", amount);
        response.put("user_no", user_no);

        return response;
    }
}

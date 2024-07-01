package com._6bitcampers.nangman_doctor.hayoon.controller;

import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Mapper.ReservationMapper;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import com._6bitcampers.nangman_doctor.hayoon.Service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController // 변경된 부분
@RequiredArgsConstructor
@Tag(name = "Status Controller", description = "예약 상태에 따라 기능을 구현하는 API 컨트롤러")

public class StatusController {


    private final ReservationMapper reservationMapper;
    private final StatusService statusService;
    @Operation(
            summary = "예약 상태 업데이트",
            description = "예약이 최종 확정이 되게끔 예약 상태를 업데이트 해줍니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 최종 확정", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "예약 업데이트 실패")
    })
    @PostMapping("/SendStatus")
    @ResponseBody
    public Map<String, Object> updateReservationStatus(@Parameter(description = "예약 번호", required = true) @RequestParam int reservationNo,
                                                       @Parameter(description = "수락 또는 거절", required = true) @RequestParam String isAccepted) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("reservationNo", reservationNo);
            response.put("isAccepted", isAccepted);

            if ("true".equals(isAccepted)) {
                statusService.updateStatus(reservationNo);

                if (statusService.getStatus(reservationNo) == 2) {
                    statusService.updateRoom(UUID.randomUUID().toString(), reservationNo);

                    String employeeEmail = reservationMapper.getEmployeemailByNo(reservationMapper.getEmployeeNo(reservationNo));
                    String userEmail = reservationMapper.getUsermailByNo(reservationMapper.getUserNoByres(reservationNo));

                    ReservationDto reservationDto = reservationMapper.getResdto(reservationNo);
                    statusService.sendReservationRequestEmail(userEmail, reservationDto);
                    statusService.sendReservationRequestEmail(employeeEmail, reservationDto);
                }
            } else {
                statusService.deleteByReservationNo(reservationNo);
            }
            response.put("status", "success");
        } catch (Exception e) {
            System.err.println("Error updating reservation status: " + e.getMessage());
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }
    @Operation(summary = "예약 수정 기능", description = "사용자가 예약을 수정할 수 있게 해줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 수정 기능을 수행합니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))

    })
    @GetMapping("/edit")

    public String editReservation(@Parameter(description = "예약 번호", required = true) @RequestParam int reservationNo,
                                  Model model){
        int infoNo=statusService.getInfoNoByReservationNo(reservationNo);
        statusService.deleteByReservationNo(reservationNo);

        model.addAttribute(infoNo);
        return "redirect:/reservation?info_no=" + infoNo;


    }
    @Operation(summary = "예약 삭제 기능", description = "사용자 또는 직원이 예약을 삭제할 수 있게 해줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 삭제 기능을 수행합니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/delete")
    @ResponseBody
    public String deleteReservation(@Parameter(description = "예약 번호", required = true) @RequestParam int reservationNo,
                                    Model model) {
        statusService.deleteByReservationNo(reservationNo);
        return "success";
    }

}

package com._6bitcampers.nangman_doctor.leegahyun.management.managementController;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewAndReceiptService;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementService.EmpService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import com._6bitcampers.nangman_doctor.hayoon.Dto.ReservationDto;
import com._6bitcampers.nangman_doctor.hayoon.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@Tag(name = "Emp API Controller", description = "직원 관리 기능을 구현하기 위한 API 컨트롤러입니다.")
@Controller
@RequiredArgsConstructor
public class EmpController {

    @Autowired
    private ReviewAndReceiptService reviewAndReceiptService;
    @Autowired
    private EmpService empService;
    @Autowired
    private ReservationService reservationService;

    @Operation(operationId = "UploadPhoto", summary = "사진 업로드", description = "병원 사진을 업로드합니다.")
    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam("email") @Parameter(name = "email", description = "직원 이메일", example = "example@example.com") String email,
                                              @RequestParam("file") @Parameter(name = "file", description = "업로드할 파일") MultipartFile file) {
        try {
            empService.registerPhoto(email, file);
            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo");
        }
    }

    @Operation(operationId = "RegisterPhoto", summary = "사진 등록", description = "로그인된 사용자의 사진을 등록합니다.")
    @PostMapping("/registerPhoto")
    public String registerPhoto(@RequestParam("photo") @Parameter(name = "photo", description = "등록할 사진 파일") MultipartFile photo, Model model) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            empService.registerPhoto(email, photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/emp";
    }

    @Operation(operationId = "GetEmployees", summary = "직원 정보 조회", description = "직원 정보를 조회하고 필요한 데이터를 모델에 추가합니다.")
    @GetMapping("/emp")
    public String getEmployees(Model model) {
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = customOAuth2User.getEmail();
        String userType = customOAuth2User.getType();
        String role = customOAuth2User.getRole();

        List<EmpDto> empHos = empService.getEmployeeLikeCounts(userId);
        model.addAttribute("emp_hos", empHos);

        if (role.equals("ROLE_MANAGER")) {
            List<EmpDto> employees = empService.getEmployeeLikeCounts(userId);
            model.addAttribute("employees", employees);

            List<EmpDto> empList = empService.getEmpList(userId);
            model.addAttribute("emplist", empList);

            List<Integer> empNos = new ArrayList<>();
            for (EmpDto empDto : empList) {
                empNos.add(empDto.getEmployee_no());
            }

            Map<Integer, List<ReviewDto>> employeeReviews = new HashMap<>();
            for (int empNo : empNos) {
                List<ReviewDto> reviewList = reviewAndReceiptService.getReviewByEmployeeNo(empNo);
                employeeReviews.put(empNo, reviewList);
            }

            List<ReviewDto> reviewList = new ArrayList<>();
            for (List<ReviewDto> reviews : employeeReviews.values()) {
                reviewList.addAll(reviews);
            }

            Map<Integer, userEntity> userMap = new HashMap<>();
            for (ReviewDto dto : reviewList) {
                var userNo = dto.getUser_no();
                userEntity userDto = reviewAndReceiptService.getUserInfoByNum(userNo);
                userMap.put(userNo, userDto);

                var employeeName = reviewAndReceiptService.getEmployeeName(dto.getEmployee_no());
                dto.setEmployee_name(employeeName);
            }

            model.addAttribute("list", reviewList);
            model.addAttribute("userMap", userMap);

            int totalNum = reviewAndReceiptService.getAllReviewsCount();
            model.addAttribute("totalNum", totalNum);
            model.addAttribute("currentpage", 1);
        } else {
            EmpDto empDto = empService.getEmployeeByEmail(userId, userType);
            List<EmpDto> employees = Collections.singletonList(empDto);
            List<EmpDto> empList = empService.getEmpList(userId);
            model.addAttribute("emplist", empList);

            model.addAttribute("employees", employees);

            int empNo = empDto.getEmployee_no();

            List<ReviewDto> reviewList = reviewAndReceiptService.getReviewByEmployeeNo(empNo);
            Map<Integer, userEntity> userMap = new HashMap<>();

            for (ReviewDto dto : reviewList) {
                var userNo = dto.getUser_no();
                userEntity userDto = reviewAndReceiptService.getUserInfoByNum(userNo);
                userMap.put(userNo, userDto);

                var employeeName = reviewAndReceiptService.getEmployeeName(dto.getEmployee_no());
                dto.setEmployee_name(employeeName);
            }
            model.addAttribute("list", reviewList);
            model.addAttribute("userMap", userMap);

            int totalNum = reviewAndReceiptService.getAllReviewsCount();
            model.addAttribute("totalNum", totalNum);
            model.addAttribute("currentpage", 1);
        }

        List<ReservationDto> reservations = reservationService.getReservationsByEmail(userId);
        model.addAttribute("reservations", reservations);

        List<EmpDto> hospitalList = empService.getAllHospitalNames();
        model.addAttribute("hospital_list", hospitalList);

        return "emp";
    }

    @Operation(operationId = "RegisterRole", summary = "역할 등록", description = "직원의 역할을 등록합니다.")
    @PostMapping("/registerRole")
    public String registerRole(@RequestParam("role") @Parameter(name = "role", description = "등록할 역할", example = "ROLE_MANAGER") String role,
                               @RequestParam("email") @Parameter(name = "email", description = "직원 이메일", example = "example@example.com") String email,
                               Model model) {
        empService.registerRole(email, role);
        return "redirect:/emp";
    }

    @Operation(operationId = "RegisterName", summary = "이름 등록", description = "직원의 이름을 등록합니다.")
    @PostMapping("/registerName")
    public String registerName(@RequestParam("hname") @Parameter(name = "hname", description = "등록할 이름", example = "홍길동") String hname,
                               Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        empService.registerName(email, hname);
        return "redirect:/emp";
    }

    @Operation(operationId = "RegisterDescription", summary = "설명 등록", description = "직원의 설명을 등록합니다.")
    @PostMapping("/registerDescription")
    public String registerDescription(@RequestParam("description") @Parameter(name = "description", description = "등록할 설명", example = "성실한 직원입니다.") String description,
                                      Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        empService.registerDescription(email, description);
        return "redirect:/emp";
    }

    @Operation(operationId = "RegisterAddress", summary = "주소 등록", description = "직원의 주소를 등록합니다.")
    @PostMapping("/registerAddress")
    public String registerAddress(@RequestParam("address") @Parameter(name = "address", description = "등록할 주소", example = "서울시 강남구") String address,
                                  Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        empService.registerAddress(email, address);
        return "redirect:/emp";
    }

    @Operation(operationId = "RegisterPlus", summary = "추가 정보 등록", description = "직원의 추가 정보를 등록합니다.")
    @PostMapping("/registerPlus")
    public String registerPlus(@RequestParam("plus") @Parameter(name = "plus", description = "등록할 추가 정보", example = "추가 정보입니다.") String plus,
                               Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        empService.registerPlus(email, plus);
        return "redirect:/emp";
    }

    @Operation(operationId = "RegisterHp", summary = "전화번호 등록", description = "직원의 전화번호를 등록합니다.")
    @PostMapping("/registerHp")
    public String registerHp(@RequestParam("hp") @Parameter(name = "hp", description = "등록할 전화번호", example = "010-1234-5678") String hp,
                             Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        empService.registerHp(email, hp);
        return "redirect:/emp";
    }
}

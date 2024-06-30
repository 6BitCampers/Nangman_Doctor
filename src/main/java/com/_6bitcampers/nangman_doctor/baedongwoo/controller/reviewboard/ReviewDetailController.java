package com._6bitcampers.nangman_doctor.baedongwoo.controller.reviewboard;

import com._6bitcampers.nangman_doctor.baedongwoo.data.dto.ReviewDto;
import com._6bitcampers.nangman_doctor.baedongwoo.data.service.ReviewAndReceiptService;
import com._6bitcampers.nangman_doctor.minio.service.storageService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/reviewboard")
public class ReviewDetailController {

    @Autowired
    private ReviewAndReceiptService reviewAndReceiptService;
    @Autowired
    private storageService storageService;

    private static final String filepath="http://minioDB.midichi.kro.kr/nangmandoctor";

    @PostMapping("/detail")
    public String detail(
            HttpServletRequest request,
            HttpSession session,
            @RequestParam("review_no") int review_no,
            @RequestParam(required = false) String userId,
            @RequestParam int currentPage,
            @RequestParam String orderBy,
            Model model) {
        //회원일 경우에는 이름으로 식별, 비회원일 경우에는 sessionId로 식별
        String identifier = (userId != null && !userId.equals("anonymousUser")) ? userId : request.getSession().getId();

        //session에 해당 아이디가 없으면 조회수 증가
        String isVisited = (String) request.getSession().getAttribute(identifier+review_no);

        if (isVisited == null) {
            session.setAttribute(identifier+review_no, "visited");
            reviewAndReceiptService.updateViewcount(review_no);
        }
        ReviewDto dto = reviewAndReceiptService.getReviewBySeq(review_no);
        int user_no=dto.getUser_no();
        int employee_no=dto.getEmployee_no();

        userEntity userDto = reviewAndReceiptService.getUserInfoByNum(user_no);
        String user_name=userDto.getUser_name();
        int hospital_no= reviewAndReceiptService.getHospitalNo(employee_no);
        String hospital_name= reviewAndReceiptService.getHospitalName(hospital_no);


        model.addAttribute("dto", dto);
        model.addAttribute("user_name", user_name);
        model.addAttribute("hospital_name", hospital_name);
        model.addAttribute("userDto",userDto);
        model.addAttribute("user_no",user_no);
        model.addAttribute("hospital_no",hospital_no);
        model.addAttribute("review_no",review_no);
        model.addAttribute("userId",userId);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("orderBy",orderBy);

        return "reviewdetail";
    }

    @PostMapping("/update")
    public String updateReview(
            @RequestParam String review_title,
            @RequestParam int review_no,
            @RequestParam String review_content,
            @RequestParam int review_likecount,
            @RequestParam String userId,
            @RequestParam int currentPage,
            @RequestParam List<String> uploadedUUIDs,
            Model model
    ){
        List<String> imageUrls = extractImageUrls(review_content);
        storageService.moveFilesToFinalBucket(imageUrls,uploadedUUIDs);
        String updatedReview_content=updateImagePaths(review_content);

        Map<String,Object>map=new HashMap<>();

        map.put("review_title",review_title);
        map.put("review_no",review_no);
        map.put("review_content",updatedReview_content);
        map.put("review_likecount",review_likecount);
        reviewAndReceiptService.updateReview(map);

       model.addAttribute("review_no",review_no);
       model.addAttribute("userId",userId);
       model.addAttribute("currentPage",currentPage);

        return "forward:/reviewboard/detail";
    }

    @PostMapping("/reviewUpdateForm")
    public String reviewUpdateForm(
            @RequestParam int user_no,
            @RequestParam int review_no,
            @RequestParam int currentPage,
            Model model
    ){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_name=customOAuth2User.getRealName();

        ReviewDto dto= reviewAndReceiptService.getReviewBySeq(review_no);
        userEntity userDto= reviewAndReceiptService.getUserInfoByNum(user_no);

        model.addAttribute("dto",dto);
        model.addAttribute("user_no",user_no);
        model.addAttribute("user_name",user_name);
        model.addAttribute("userDto",userDto);
        model.addAttribute("userId",userId);
        model.addAttribute("currentPage",currentPage);

        return "reviewUpdateForm";
    }

    @PostMapping("/writeReview")
    public String writeReview(
            Model model
    ){
        CustomUserDetails customOAuth2User = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId= customOAuth2User.getEmail();
        String user_name=customOAuth2User.getRealName();

        model.addAttribute("userId",userId);
        model.addAttribute("user_name",user_name);
        return "reviewWriteForm";
    }
    
    @PostMapping("/insert")
    public String insertReview(
            @RequestParam String review_title,
            @RequestParam String review_content,
            @RequestParam int review_likecount,
            @RequestParam int employee_no,
            @RequestParam int user_no,
            @RequestParam List<String> uploadedUUIDs
            ){
        List<String> imageUrls = extractImageUrls(review_content);
        storageService.moveFilesToFinalBucket(imageUrls,uploadedUUIDs);
        String updatedReview_content=updateImagePaths(review_content);

        ReviewDto reviewDto= ReviewDto.builder().
                review_title(review_title).
                review_content(updatedReview_content).
                employee_no(employee_no).
                user_no(user_no).
                review_likecount(review_likecount).
                build();

        reviewAndReceiptService.insertReview(reviewDto);

        return "redirect:/reviewboard";
    }

    @ResponseBody
    @GetMapping("/delete")
    public String deleteReview(
            @RequestParam int review_no
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
        return "{'status':'success'}";
    }

    //AJAX로 업로드 되는 이미지 바로바로 저장하기
    @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        String filename = UUID.randomUUID()+"";

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

    //저장되는 리뷰에서 진짜로 저장할 이미지 이름 가져오기
    private List<String> extractImageUrls(String content) {
        List<String> imageUrls = new ArrayList<>();
        String regex = "/temp/([^\"']+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            imageUrls.add(matcher.group(1));
        }

        return imageUrls;
    }

    public String updateImagePaths(String review_content) {
        return review_content.replaceAll("/temp/", "/reviewBoard/");
    }

}

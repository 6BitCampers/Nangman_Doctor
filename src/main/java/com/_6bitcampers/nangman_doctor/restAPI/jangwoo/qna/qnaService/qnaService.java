package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaService;

import com._6bitcampers.nangman_doctor.minio.service.storageService;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.boardDto;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.commentDto;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaMapper.qnaRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class qnaService {

    private final qnaRestMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final storageService storageService;

    public List<commentDto> findAllComment(int qna_no) {
        return mapper.findAllByQnANo(qna_no);
    }

    public void insertComment(int qna_no,String comment,String email, String type) {
        mapper.insertComment(qna_no,comment,email,type);
    }

    public void insertBoard(String title, String comment, String username, String password, MultipartFile image){
        String filename = null;

        if (image!=null){
            filename = UUID.randomUUID().toString();
            try {
                storageService.uploadFile("nangmandoctor","/QnABoard/"+filename,image.getInputStream(),image.getContentType());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        mapper.insertBoard(title,comment, username, passwordEncoder.encode(password),filename);
    }

    public boolean deleteComment(String username, String password,int qna_no) {
        boardDto board = mapper.findOneByQnaNo(qna_no);
        if (username.equals(board.getQna_username()) && passwordEncoder.matches(password, board.getQna_password())){
            mapper.deleteBoard(qna_no);
            return true;
        }
        return false;
    }


}

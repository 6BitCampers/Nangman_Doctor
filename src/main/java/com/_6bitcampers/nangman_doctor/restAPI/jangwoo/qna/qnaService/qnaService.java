package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaService;

import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.commentDto;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaMapper.qnaRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class qnaService {

    private final qnaRestMapper mapper;

    public List<commentDto> findAllComment(int qna_no) {
        return mapper.findAllByQnANo(qna_no);
    }
}

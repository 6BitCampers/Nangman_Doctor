package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.mapper;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto.qnaBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface qnaMapper {


    @Select("select count(*) from board_qna;")
    int getTotalCount();

    @Select("select *,(select count(*) from board_comment where qna_no=board_qna.qna_no) as comment_count from board_qna order by qna_writeday limit #{limit} offset #{offset}")
    List<qnaBoard> findAllBoard(int offset, int limit);
}

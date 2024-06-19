package com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaMapper;

import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.boardDto;
import com._6bitcampers.nangman_doctor.restAPI.jangwoo.qna.qnaDto.commentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface qnaRestMapper {

    @Select("select comment_comment,comment_writeday,qna_no,he.employee_nickname from board_comment bc join semi.hospital_employee he on he.employee_no = bc.employee_no where qna_no=#{qna_no}")
    List<commentDto> findAllByQnANo(@Param("qna_no")int qna_no);

    @Insert("insert into board_comment (employee_no, comment_comment, qna_no) values ((select employee_no from hospital_employee where employee_type=#{type} and employee_email=#{email}),#{comment},#{qna_no})")
    void insertComment(@Param("qna_no")int qna_no,@Param("comment")String comment,@Param("email")String email,@Param("type")String type);

    @Insert("insert into board_qna (qna_title, qna_comment, qna_username, qna_password, qna_image) values (#{title},#{comment},#{username},#{password},#{image})")
    void insertBoard(@Param("title")String title,@Param("comment")String comment,@Param("username")String username,@Param("password")String password,@Param("image")String image);

    @Select("select qna_username,qna_password from board_qna where qna_no=#{qna_no}")
    boardDto findOneByQnaNo(@Param("qna_no") int qna_no);

    @Delete("delete from board_qna where qna_no=#{qna_no}")
    void deleteBoard(@Param("qna_no") int qna_no);

    @Select("""
select if(normal.count+employee.count=0,1,0) as count
from (select count(*) count
      from normal_user
      where user_name = #{name}
        and user_email = #{email}
        and user_type = 'local') normal,
     (select count(*) count
      from hospital_employee
      where employee_name = #{name}
        and employee_email = #{email}
        and employee_type = 'local') employee;
""")
    boolean existByEmailAndName(@Param("email")String email,@Param("name") String name);
}

package com._6bitcampers.nangman_doctor.restAPI.jangwoo.join.joinRestMappper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface joinRestMapper {

    @Select("select count(*) from hospital_employee where employee_email=#{email} and employee_type='local'")
    int findByEmailEmployee(String email);

    @Select("select count(*) from hospital_employee where employee_nickname=#{nickname}")
    int findByNicknameEmployee(String nickname);

    @Select("select count(*) from normal_user where user_email=#{email} and user_type='local'")
    int findByEmailUser(String email);

    @Select("select count(*) from normal_user where user_nickname=#{nickname}")
    int findByNicknameUser(String nickname);

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
    boolean existByEmailAndName(@Param("email")String email, @Param("name") String name);

    @Select("""
select *
from (select IF(normal.count=1,'normal_user','hospital_employee') as board
from (select count(*) count
      from normal_user
      where user_name = #{name}
        and user_email = #{email}
        and user_type = 'local') normal,
     (select count(*) count
      from hospital_employee
      where employee_name = #{name}
        and employee_email = #{email}
        and employee_type = 'local') employee) as neb
""")
    String findTableByEmailAndName(@Param("email")String email, @Param("name") String name);

    @Update("update normal_user set user_role = 'ROLE_RESET' where user_email=#{email} and user_type='local'")
    void updateNormalUserByEmail(@Param("email")String email);

    @Update("update hospital_employee set employee_role = 'ROLE_RESET' where employee_email=#{email} and employee_type='local'")
    void updateEmployeeUserByEmail(@Param("email")String email);


    @Insert("insert into search_password (search_url, search_email) values (#{url},#{email})")
    void insertPWInitUrl(@Param("url")String url,@Param("email")String email);
}

package com.foufou.mapper;

import com.foufou.domain.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommenMapper {

    @Select("select * from students where student_id = #{id};")
    Student selectFromStu(Long id);

    @Update("update students set password = #{newPassword} where student_id = #{id}")
    void resetStuPassword(Long id, String newPassword);

    @Update("update teachers set password = #{newPassword} where teacher_id = #{id}")
    void resetTeaPassword(Long id, String newPassword);
}

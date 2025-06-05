package com.foufou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foufou.domain.entity.Student;
import com.foufou.domain.vo.StuGradeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    StuGradeVO getGrade(Long id);
}

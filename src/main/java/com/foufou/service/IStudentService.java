package com.foufou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foufou.domain.dto.StudentLoginDTO;
import com.foufou.domain.entity.Student;
import com.foufou.domain.vo.StuGradeVO;

public interface IStudentService extends IService<Student> {
    Student login(StudentLoginDTO stuLoginDTO);

    StuGradeVO getGrade(Long id);
}

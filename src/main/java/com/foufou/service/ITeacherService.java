package com.foufou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foufou.domain.dto.AuditGradeDTO;
import com.foufou.domain.dto.TeacherLoginDTO;
import com.foufou.domain.entity.Teacher;
import com.foufou.domain.vo.GradeVO;

import java.util.List;

public interface ITeacherService extends IService<Teacher> {
    Teacher login(TeacherLoginDTO userLoginDTO);

    List<GradeVO> getGradeList(String gradeStatus, Long teaId);

    void auditGrade(List<AuditGradeDTO> auditGradeDTOList);
}

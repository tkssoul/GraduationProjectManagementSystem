package com.foufou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foufou.domain.dto.AuditGradeDTO;
import com.foufou.domain.entity.Grade;
import com.foufou.domain.entity.Teacher;
import com.foufou.domain.vo.GradeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    List<GradeVO> searchGrades(String gradeStatus);

    @Update("update grades set final_grade = #{finalGrade} where grade_id = #{gradeId}")
    void updateFinalGrade(Double finalGrade, Long gradeId);

    @Update("update grades set grade_status = #{gradeStatus} where grade_id = #{gradeId}")
    void audit(AuditGradeDTO auditGradeDTO);
}

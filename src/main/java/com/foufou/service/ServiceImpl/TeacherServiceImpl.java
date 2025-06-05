package com.foufou.service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foufou.common.constant.MessageConstant;
import com.foufou.common.exception.BaseException;
import com.foufou.domain.dto.AuditGradeDTO;
import com.foufou.domain.dto.TeacherLoginDTO;
import com.foufou.domain.entity.Grade;
import com.foufou.domain.entity.Student;
import com.foufou.domain.entity.Teacher;
import com.foufou.domain.vo.GradeVO;
import com.foufou.mapper.StudentMapper;
import com.foufou.mapper.TeacherMapper;
import com.foufou.service.ITeacherService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
@Slf4j
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(TeacherLoginDTO userLoginDTO) {
        // 查找是否存在用户
        Teacher tea = lambdaQuery()
                .eq(userLoginDTO.getTeacherId() != null, Teacher::getTeacherId, userLoginDTO.getTeacherId())
                .one();
        if (tea == null) {  // 判断用户是否存在
            throw new BaseException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 判断密码是否正确
        if (!tea.getPassword().equals(userLoginDTO.getPassword())) {
            throw new BaseException(MessageConstant.PASSWORD_ERROR);
        }

        return tea;
    }

    @Override
    public List<GradeVO> getGradeList(String gradeStatus, Long teaId) {
        Teacher tea = lambdaQuery()
                .eq(teaId != null, Teacher::getTeacherId, teaId)
                .one();

        if (!"教科办".equals(tea.getDepartment())) {
            throw new BaseException("权限不足，请联系管理员");
        }

        List<GradeVO> gradeVOList =  teacherMapper.searchGrades(convertGradeStatus(gradeStatus));

        gradeVOList.forEach((GradeVO gradeVO) -> {
                if (gradeVO.getFinalGrade() == null) {
                    gradeVO.setFinalGrade(0.2 * gradeVO.getMidtermGrade() + 0.4 * gradeVO.getThesisGrade() + 0.4 * gradeVO.getDefenseGrade());
                    teacherMapper.updateFinalGrade(gradeVO.getFinalGrade(), gradeVO.getGradeId());
                }
            gradeVO.setGradeStatus(reconvertGradeStatus(gradeVO.getGradeStatus()));
            }
        );

        return gradeVOList;
    }

    @Override
    public void auditGrade(List<AuditGradeDTO> auditGradeDTOList) {
        for (AuditGradeDTO auditGradeDTO : auditGradeDTOList) {
            auditGradeDTO.setGradeStatus(convertGradeStatus(auditGradeDTO.getGradeStatus()));
            teacherMapper.audit(auditGradeDTO);
        }
    }

    private String convertGradeStatus(String gradeStatus) {
        if ("待审批".equals(gradeStatus)) {
            return "pending";
        } else if ("已批准".equals(gradeStatus)) {
            return "approved";
        } else if ("已拒绝".equals(gradeStatus)){
            return "rejected";
        } else {
            return null;
        }
    }

    private String reconvertGradeStatus(String gradeStatus) {
        if ("pending".equals(gradeStatus)) {
            return "待审批";
        } else if ("approved".equals(gradeStatus)) {
            return "已批准";
        } else if ("rejected".equals(gradeStatus)){
            return "已拒绝";
        } else {
            return null;
        }
    }

}

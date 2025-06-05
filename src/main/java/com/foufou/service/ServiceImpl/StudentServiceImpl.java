package com.foufou.service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foufou.common.constant.MessageConstant;
import com.foufou.common.exception.BaseException;
import com.foufou.domain.dto.StudentLoginDTO;
import com.foufou.domain.entity.Student;
import com.foufou.domain.vo.StuGradeVO;
import com.foufou.mapper.StudentMapper;
import com.foufou.service.IStudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student login(StudentLoginDTO stuLoginDTO) {
        // 查找是否存在用户
        Student stu = lambdaQuery()
                .eq(stuLoginDTO.getStudentId() != null, Student::getStudentId, stuLoginDTO.getStudentId())
                .one();
        if (stu == null) {  // 判断用户是否存在
            throw new BaseException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 判断密码是否正确
        if (!stu.getPassword().equals(stuLoginDTO.getPassword())) {
            throw new BaseException(MessageConstant.PASSWORD_ERROR);
        }

        return stu;
    }

    @Override
    public StuGradeVO getGrade(Long id) {
        StuGradeVO stuGradeVO = studentMapper.getGrade(id);
        if ("pending".equals(stuGradeVO.getGradeStatus())) {
            throw new BaseException("成绩审核中，请等待");
        }
        stuGradeVO.setGradeStatus(reconvertGradeStatus(stuGradeVO.getGradeStatus()));
        return stuGradeVO;
    }

    private String reconvertGradeStatus(String gradeStatus) {
        if ("approved".equals(gradeStatus)) {
            return "已批准";
        } else if ("rejected".equals(gradeStatus)){
            return "已拒绝";
        } else {
            return null;
        }
    }
}

package com.foufou.service.ServiceImpl;

import com.foufou.common.exception.BaseException;
import com.foufou.domain.dto.ResetPDTO;
import com.foufou.domain.entity.Student;
import com.foufou.mapper.CommenMapper;
import com.foufou.service.CommenService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommenServiceImpl implements CommenService {

    @Resource
    private CommenMapper commenMapper;

    @Override
    public void resetPassword(ResetPDTO resetPDTO) {
        if (!resetPDTO.getNewPassword().equals(resetPDTO.getNewPasswordAgain())) {
            throw new BaseException("两次密码不相同");
        }

        Student stu = commenMapper.selectFromStu(resetPDTO.getId());

        if (stu != null) {
            commenMapper.resetStuPassword(resetPDTO.getId(), resetPDTO.getNewPassword());
        } else {
            commenMapper.resetTeaPassword(resetPDTO.getId(), resetPDTO.getNewPassword());
        }


    }
}

package com.foufou.controller;

import com.foufou.common.constant.JwtClaimsConstant;
import com.foufou.common.properties.JwtProperties;
import com.foufou.common.result.Result;
import com.foufou.domain.dto.StudentLoginDTO;
import com.foufou.domain.entity.Student;
import com.foufou.domain.vo.StuGradeVO;
import com.foufou.domain.vo.StudentLoginVO;
import com.foufou.service.IStudentService;
import com.foufou.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final IStudentService studentService;
    private final JwtProperties jwtProperties;
    private final StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<StudentLoginVO> login(@RequestBody StudentLoginDTO userLoginDTO) {
        Student stu = null;
        try {
            stu = studentService.login(userLoginDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.STU_ID, stu.getStudentId());
        String token = JwtUtil.createJWT(
                jwtProperties.getStuSecretKey(),
                jwtProperties.getStuTtl(),
                claims);

        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        operations.set(token,token,4, TimeUnit.HOURS);

        StudentLoginVO userLoginVO = StudentLoginVO.builder()
                .id(stu.getStudentId())
                .name(stu.getName())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        try {
            String token = request.getHeader(jwtProperties.getStuTokenName());
            stringRedisTemplate.delete(token);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/grades/published/{id}")
    public Result<StuGradeVO> getGrade(@PathVariable Long id) {
        StuGradeVO stuGradeVO = studentService.getGrade(id);
        return Result.success(stuGradeVO);
    }

}

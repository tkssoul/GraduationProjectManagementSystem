package com.foufou.controller;

import com.foufou.common.constant.JwtClaimsConstant;
import com.foufou.common.properties.JwtProperties;
import com.foufou.common.result.Result;
import com.foufou.domain.dto.AuditGradeDTO;
import com.foufou.domain.dto.StudentLoginDTO;
import com.foufou.domain.dto.TeacherLoginDTO;
import com.foufou.domain.entity.Student;
import com.foufou.domain.entity.Teacher;
import com.foufou.domain.vo.GradeVO;
import com.foufou.domain.vo.StudentLoginVO;
import com.foufou.domain.vo.TeacherLoginVO;
import com.foufou.service.IStudentService;
import com.foufou.service.ITeacherService;
import com.foufou.utils.JwtUtil;
import com.foufou.utils.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final ITeacherService teacherService;
    private final JwtProperties jwtProperties;
    private final StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<TeacherLoginVO> login(@RequestBody TeacherLoginDTO userLoginDTO) {
        Teacher tea = null;
        try {
            tea = teacherService.login(userLoginDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.TEACHER_ID, tea.getTeacherId());
        String authorization = JwtUtil.createJWT(
                jwtProperties.getTeaSecretKey(),
                jwtProperties.getTeaTtl(),
                claims);

        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        operations.set(authorization,authorization,4, TimeUnit.HOURS);

        TeacherLoginVO userLoginVO = TeacherLoginVO.builder()
                .id(tea.getTeacherId())
                .name(tea.getName())
                .Authorization(authorization)
                .build();

        return Result.success(userLoginVO);
    }

    @PostMapping("logout")
    public Result logout(HttpServletRequest request){
        try {
            String token = request.getHeader(jwtProperties.getTeaTokenName());
            stringRedisTemplate.delete(token);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/grades/pending")
    public Result<List<GradeVO>> getGradeList(HttpServletRequest request, @RequestParam String gradeStatus) {
        Claims claims = JwtUtil.parseJWT(jwtProperties.getTeaSecretKey(), request.getHeader(jwtProperties.getTeaTokenName()));
        Long teaId = Long.valueOf(claims.get(JwtClaimsConstant.TEACHER_ID).toString());
        List<GradeVO> gradeList = teacherService.getGradeList(gradeStatus, teaId);
        return Result.success(gradeList);
    }

    @PutMapping("/grades/audit")
    public Result auditGrade(@RequestBody List<AuditGradeDTO> auditGradeDTOList) {
        teacherService.auditGrade(auditGradeDTOList);
        return Result.success();
    }
}

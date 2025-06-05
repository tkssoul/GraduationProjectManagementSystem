package com.foufou.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("grades")
public class Grade {
    private Long gradeId;
    private Long studentId;
    private Long topicId;
    private Double midtermGrade;
    private Double thesisGrade;
    private Double defenseGrade;
    private Double finalGrade;
    private String gradeStatus;
    private Timestamp createdAt;
}

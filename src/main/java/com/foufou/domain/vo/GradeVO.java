package com.foufou.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeVO {
    private Long gradeId;
    private Long studentId;
    private String name;
    private String topicTitle;
    private String supervisor;
    private Double midtermGrade;
    private Double thesisGrade;
    private Double defenseGrade;
    private Double finalGrade;
    private String gradeStatus;
}

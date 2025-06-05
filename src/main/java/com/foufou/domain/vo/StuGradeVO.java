package com.foufou.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuGradeVO {
    private Long studentId;
    private String topicTitle;
    private Double midtermGrade;
    private Double thesisGrade;
    private Double defenseGrade;
    private Double finalGrade;
    private String gradeStatus;
}

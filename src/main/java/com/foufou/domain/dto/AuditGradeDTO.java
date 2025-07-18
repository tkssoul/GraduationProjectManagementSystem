package com.foufou.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditGradeDTO {
    private Long gradeId;
    private String gradeStatus;
}

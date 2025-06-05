package com.foufou.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("students")
public class Student {
    private Long studentId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String major;
    private Double gpa;
}

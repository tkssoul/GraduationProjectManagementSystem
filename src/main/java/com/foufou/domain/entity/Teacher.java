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
@TableName("teachers")
public class Teacher {
    private Long teacherId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String title;
}

package com.foufou.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherLoginVO {
    // 用户id
    private Long id;

    // 姓名
    private String name;

    // jwt令牌
    private String Authorization;
}

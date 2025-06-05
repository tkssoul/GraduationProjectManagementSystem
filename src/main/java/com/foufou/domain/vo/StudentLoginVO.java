package com.foufou.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentLoginVO {

    // 用户id
    private Long id;

    // 姓名
    private String name;

    // jwt令牌
    private String token;

}

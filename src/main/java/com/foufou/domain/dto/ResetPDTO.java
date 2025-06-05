package com.foufou.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPDTO {
    private Long id;
    private String newPassword;
    private String newPasswordAgain;
}

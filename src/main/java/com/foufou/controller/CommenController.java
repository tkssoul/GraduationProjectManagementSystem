package com.foufou.controller;

import com.foufou.common.result.Result;
import com.foufou.domain.dto.ResetPDTO;
import com.foufou.service.CommenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommenController {

    private final CommenService commenService;

    @PutMapping("/reset_password")
    public Result resetP(@RequestBody ResetPDTO resetPDTO) {
        commenService.resetPassword(resetPDTO);
        return Result.success();
    }
}

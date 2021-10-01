/*
 * DeparmentController.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.evotek.cache.model.dto.DepartmentDTO;
import com.evotek.cache.service.DepartmentService;
import lombok.RequiredArgsConstructor;

/**
 * 01/10/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deparment")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/get-one")
    public ResponseEntity<DepartmentDTO> getOne() {
        return new ResponseEntity<>(departmentService.getOne(), HttpStatus.OK);
    }
}

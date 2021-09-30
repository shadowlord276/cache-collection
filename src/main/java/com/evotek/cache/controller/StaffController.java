/*
 * StaffController.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.controller;

import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.evotek.cache.model.Staff;
import com.evotek.cache.service.StaffService;
import lombok.RequiredArgsConstructor;

/**
 * 29/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffService staffService;

    @GetMapping("/list")
    public ResponseEntity<List<Staff>> getList() {
        return new ResponseEntity<List<Staff>>(this.staffService.getList(), HttpStatus.OK);
    }
    
    @GetMapping("/set")
    public ResponseEntity<Set<Staff>> getSet() {
        return new ResponseEntity<Set<Staff>>(this.staffService.getSet(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getById (@PathVariable Integer id) {
        return new ResponseEntity<Staff> (this.staffService.getById(id), HttpStatus.OK);
    }
}

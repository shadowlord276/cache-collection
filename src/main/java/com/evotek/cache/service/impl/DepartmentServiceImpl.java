/*
 * DeparmentServiceImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.service.impl;

import org.springframework.stereotype.Service;
import com.evotek.cache.model.dto.DepartmentDTO;
import com.evotek.cache.repository.DepartmentRepository;
import com.evotek.cache.repository.StaffRepository;
import com.evotek.cache.service.DepartmentService;
import com.evotek.cache.service.mapper.DepartmentMapper;
import lombok.RequiredArgsConstructor;

/**
 * 01/10/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;
    
    private final StaffRepository staffRepository;

    @Override
    public DepartmentDTO getOne() {
        return departmentMapper.toDto(departmentRepository.getOne(), staffRepository);
    }

}

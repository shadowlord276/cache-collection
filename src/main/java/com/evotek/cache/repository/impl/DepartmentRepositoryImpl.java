/*
 * DepartmentRepositoryImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.repository.impl;

import org.springframework.stereotype.Repository;
import com.evotek.cache.model.Department;
import com.evotek.cache.repository.DepartmentRepository;

/**
 * 01/10/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Override
    public Department getOne() {
        return new Department(1, "Human Resource");
    }

}

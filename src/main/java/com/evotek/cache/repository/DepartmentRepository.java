/*
 * DepartmentRepository.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.repository;

import com.evotek.cache.model.Department;

/**
 * 01/10/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface DepartmentRepository {

    /**
     * @return
     */
    Department getOne();

}

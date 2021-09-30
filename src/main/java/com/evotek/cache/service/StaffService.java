/*
 * StaffService.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.service;

import java.util.List;
import com.evotek.cache.model.Staff;

/**
 * 30/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface StaffService {
    List<Staff> getAll();
    
    Staff getById(Integer id);
}

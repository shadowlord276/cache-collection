/*
 * StaffRepository.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.repository;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import com.evotek.cache.model.Staff;

/**
 * 29/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface StaffRepository {
    public static final String KEY = "key";
    /**
     * @return
     */
    @Cacheable(cacheNames = "staff", key = "#root.target.KEY")
    List<Staff> getAll();

    /**
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "staff", key = "#id")
//    @CacheUpdate(cacheNames = "staff", key = KEY)
    Staff findById(Integer id);
}

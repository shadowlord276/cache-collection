/*
 * StaffRepository.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.repository;

import java.util.List;
import java.util.Set;
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
    @Cacheable(cacheNames = "list", key = "#root.target.KEY")
    List<Staff> getList();

    @Cacheable(cacheNames = "set", key = "#root.target.KEY")
    Set<Staff> getSet();
    /**
     * @param id
     * @return
     */
    @Cacheable(cacheNames = {"list", "set" }, key = "#id")
//    @CacheUpdate(cacheNames = "staff", key = KEY)
    Staff findById(Integer id);
}

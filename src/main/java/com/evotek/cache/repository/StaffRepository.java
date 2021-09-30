/*
 * StaffRepository.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cache.annotation.Cacheable;
import com.evotek.cache.annotation.CacheCollection;
import com.evotek.cache.annotation.CacheMap;
import com.evotek.cache.annotation.CacheUpdate;
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
     * @return
     */
    @Cacheable(cacheNames = "map", key = "#root.target.KEY")
    Map<Integer, Staff> getMap();

    /**
     * @param id
     * @return
     */

    Staff findById(Integer id);

//     @CacheCollection(cacheNames = {"list", "set"}, key = KEY, compareProperties = "id", condition = "#returnValue.id > 4")
//     @CacheMap(cacheNames = "map", key = KEY, keyExpression = "#returnValue.id")
    @CacheUpdate(
                    collection = {
                                    @CacheCollection(cacheNames = {"list", "set"}, key = KEY, compareProperties = "id",
                                    condition = "#returnValue.id > 4")},
                    map = {
                                    @CacheMap(cacheNames = "map", key = KEY, keyExpression = "#returnValue.id")
                    })
    default Staff _findById(Integer id) {
        return findById(id);
    }
}

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
import com.evotek.cache.annotation.CacheAction;
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

    @Cacheable(cacheNames = "save", key = "{#staff.id, #staff.name}")
    default Staff save(Staff staff) {
        return staff;
    }
    /**
     * @param id
     * @return
     */

    Staff findById(Integer id);

//     @CacheCollection(cacheNames = {"list", "set"}, key = KEY, compareProperties = "id", condition = "#returnValue.id > 4")
//     @CacheMap(cacheNames = "map", key = KEY, keyMap = "#returnValue.id")
    @CacheUpdate(
                    collection = {
                                    @CacheCollection(cacheNames = {"list", "set", "save"}, key = "{#returnValue.id, #returnValue.name}", compareProperties = "id",
                                    condition = "#returnValue.id > 2")},
                    map = {
                                    @CacheMap(cacheNames = "map", key = "#target.KEY", keyMap = "#returnValue.id")
                    })
    default Staff _findById(Integer id) {
        return findById(id);
    }

    /**
     * @param id
     * @return
     */
    @CacheUpdate(
                    collection = {
                                    @CacheCollection(cacheNames = {"list", "set", "save"}, key = "#target.KEY", compareProperties = "id",
                                    condition = "#returnValue.id < 2", action = CacheAction.EVICT)},
                    map = {
                                    @CacheMap(cacheNames = "map", key = "#target.KEY", keyMap = "#returnValue.id", 
                                                    action = CacheAction.EVICT)
                    })
    default Staff deleteById(Integer id) {
        return new Staff(id);
    }

    /**
     * @return
     */
    List<Integer> getStaffIds();
}

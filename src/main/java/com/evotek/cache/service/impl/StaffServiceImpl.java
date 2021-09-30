/*
 * StaffService.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.evotek.cache.model.Staff;
import com.evotek.cache.repository.StaffRepository;
import com.evotek.cache.service.StaffService;
import lombok.RequiredArgsConstructor;

/**
 * 29/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@RequiredArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {
    public static final String KEY = "key";

    private final StaffRepository staffRepository;

    /**
     * @return
     */
    @Override
    public List<Staff> getList() {

        return this.staffRepository.getList();
    }


    /**
     * 
     */
    @Override
    public Set<Staff> getSet() {
        return this.staffRepository.getSet();
    }

    /**
     * 
     */
    @Override
    public Map<Integer, Staff> getMap() {
        return this.staffRepository.getMap();
    }

    /**
     * @param id
     * @return
     */
    // @CacheCollection(cacheNames = {"list", "set"}, key = KEY, compareProperties = "id", condition = "#returnValue.id > 4")
    // @CacheMap(cacheNames = "map", key = KEY, keyExpression = "#returnValue.id")
//    @CacheUpdate(
//                    collection = {
//                                    @CacheCollection(cacheNames = {"list", "set"}, key = KEY, compareProperties = "id",
//                                    condition = "#returnValue.id > 4")},
//                    map = {
//                                    @CacheMap(cacheNames = "map", key = KEY, keyExpression = "#returnValue.id")
//                    })
    @Override
    public Staff getById(Integer id) {
        return this.staffRepository._findById(id);
    }


}

/*
 * StaffRepositoryImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import com.evotek.cache.model.Staff;
import com.evotek.cache.repository.StaffRepository;

/**
 * 29/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Repository
public class StaffRepositoryImpl implements StaffRepository {

    @Override
    public List<Staff> getList() {
        List<Staff> results = new ArrayList<>();
        
        results.add(new Staff(1, "Spring"));
        results.add(new Staff(2, "Summer"));
        results.add(new Staff(3, "Autumn"));
        results.add(new Staff(4, "Winter"));
        
        return results;
    }


    /* 
     * (non-Javadoc)
     * @see com.evotek.cache.repository.StaffRepository#getSet()
     */
    @Override
    public Set<Staff> getSet() {
        Set<Staff> results = new HashSet<>();
        
        results.add(new Staff(1, "Spring"));
        results.add(new Staff(2, "Summer"));
        results.add(new Staff(3, "Autumn"));
        results.add(new Staff(4, "Winter"));
        
        return results;
    }
    
    @Override
    public Staff findById(Integer id) {
        // TODO Auto-generated method stub
        return new Staff(id, "Another Season" + String.valueOf(id));
    }


}

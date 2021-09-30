/*
 * StaffRepositoryImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.repository.impl;

import java.util.ArrayList;
import java.util.List;
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
    public List<Staff> getAll() {
        List<Staff> results = new ArrayList<>();
        
        results.add(new Staff(1, "Xuân"));
        results.add(new Staff(2, "Hạ"));
        results.add(new Staff(3, "Thu"));
        results.add(new Staff(4, "Đông"));
        
        return results;
    }

    @Override
    public Staff findById(Integer id) {
        // TODO Auto-generated method stub
        return new Staff(id, "adudu " + String.valueOf(id));
    }

}

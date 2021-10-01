/*
 * Department.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 01/10/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Data
@AllArgsConstructor
public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = 2874440992875390576L;
    
    private Integer id;

    private String name;
    
    private String code;
    
    private List<Integer> staffIds = new ArrayList<>();
}

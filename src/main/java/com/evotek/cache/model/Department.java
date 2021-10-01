/*
 * Department.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 01/10/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Data
@AllArgsConstructor
public class Department implements Serializable {
    private static final long serialVersionUID = 2874440992875390576L;
    
    private Integer id;

    private String name;
    
}

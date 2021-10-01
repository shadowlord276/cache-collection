/*
 * Staff.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 29/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Data
@AllArgsConstructor
public class Staff implements Serializable {
    /** The Constant serialVersionUID */
    private static final long serialVersionUID = -210681766206697311L;

    private Integer id;

    private String name;

    /**
     * Instantiates a new @param id
     */
    public Staff(Integer id) {
        super();
        this.id = id;
    }


}

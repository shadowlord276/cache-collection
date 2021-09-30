/*
 * CacheUpdate.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.evotek.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 29/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheUpdate {
    CacheCollection[] collection() default {};
    
    CacheMap[] map() default {};
}

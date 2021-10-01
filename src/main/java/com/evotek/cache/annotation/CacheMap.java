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
public @interface CacheMap {
    
    /**
     * The cache names which have used in @Cacheable
     * @return
     */
    String[] cacheNames();
    
    /**
     * The key of cache which have used in @Cacheable
     * @return
     */
    String key();
    
    /**
     * The key of the map create from Spring Expression Language (SpEL)
     */
    String keyMap();
    
    /**
     * The condition has been checked before update the cache. The condition uses Spring Expression Language (SpEL)
     * @return
     */
    String condition() default "";
    
    CacheAction action() default CacheAction.PUT;
}

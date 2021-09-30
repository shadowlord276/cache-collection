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
public @interface CacheCollection {
    
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
     * The properties have been used to compare 2 objects to find out the object will be update. If nothing to
     * be found, it means the new instance has created and it will be added to the cache.
     * @return
     */
    String[] compareProperties();
    
    /**
     * The condition has been checked before update the cache. The condition uses Spring Expression Language (SpEL)
     * @return
     */
    String condition() default "";
}

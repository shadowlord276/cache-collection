/*
 * CacheAspect.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.aop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import com.evotek.cache.annotation.CacheCollection;
import com.evotek.cache.util.ReflectionUtil;
import com.evotek.cache.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 29/09/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {
    private final CacheManager cacheManager;

    @AfterReturning(pointcut = "@annotation(cacheUpdate)", returning = "returnValue")
    public void cacheUpdate(final JoinPoint joinPoint, CacheCollection cacheUpdate,
                    Object returnValue) {
        _log.debug("cacheUpdate is called");

        String[] cacheNames = cacheUpdate.cacheNames();
        String key = cacheUpdate.key();
        String[] compareProperties = cacheUpdate.compareProperties();

        Class<?> _class = returnValue.getClass();

        for (String cacheName : cacheNames) {
            Cache cache = this.cacheManager.getCache(cacheName);

            if (cache == null) {
                continue;
            }

            ValueWrapper valueWrapper = cache.get(key);
            
            // if nothing in cache, do nothing
            if (valueWrapper == null) {
                return;
            }

            Object ob = valueWrapper.get();
            
            if (Collection.class.isAssignableFrom(ob.getClass().getSuperclass())) {
                Iterator<?> iterator = ((Collection<?>) ob).iterator();

                List<Object> newList = new ArrayList<>();

                while (iterator.hasNext()) {
                    Object c = iterator.next();
                    
                    if (!_class.isAssignableFrom(c.getClass())) {
                        break;
                    }

                    if (!compare(c, returnValue, compareProperties)) {
                        newList.add(c);

                        if (iterator.hasNext()) {
                            continue;
                        }
                    }

                    newList.add(returnValue);
                }
                
                cache.put(key, newList);
            }
        }
    }

    /**
     * @param object
     * @param returnValue
     * @param compareProperties
     * @return
     */
    private boolean compare(Object origin, Object returnValue, String[] compareProperties) {
        for (String property : compareProperties) {
            Object originPv = ReflectionUtil.getFieldValue(origin, property);

            Object returnPv = ReflectionUtil.getFieldValue(returnValue, property);

            if (!Validator.equals(originPv, returnPv)) {
                return false;
            }
        }

        return true;
    }
}

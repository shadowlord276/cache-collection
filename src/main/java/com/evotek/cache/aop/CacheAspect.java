/*
 * CacheAspect.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.aop;

import java.util.Collection;
import java.util.Iterator;
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
                    Object returnValue) throws Exception {
        _log.debug("cacheUpdate is called");

        String[] cacheNames = cacheUpdate.cacheNames();
        String key = cacheUpdate.key();
        String[] compareProperties = cacheUpdate.compareProperties();

        Class<?> _class = returnValue.getClass();

        for (String cacheName : cacheNames) {
            // get cache by cache name
            Cache cache = this.cacheManager.getCache(cacheName);

            // if cache is null, do nothing
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

                // Create new instance of object
                Collection<Object> newCollection = (Collection<Object>) ob.getClass().newInstance();

                while (iterator.hasNext()) {
                    Object c = iterator.next();
                    
                    if (!_class.isAssignableFrom(c.getClass())) {
                        break;
                    }

                    if (!compare(c, returnValue, compareProperties)) {
                        newCollection.add(c);

                        // if iterator has next element, continue the loop while. If not it means the returnValue is the
                        // new one. It will be add to the cache.
                        if (iterator.hasNext()) {
                            continue;
                        }
                    }

                    newCollection.add(returnValue);
                }
                
                cache.put(key, newCollection);
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

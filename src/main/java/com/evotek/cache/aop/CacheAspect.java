/*
 * CacheAspect.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and proprietary information of
 * Evotek
 */
package com.evotek.cache.aop;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import com.evotek.cache.annotation.CacheCollection;
import com.evotek.cache.annotation.CacheMap;
import com.evotek.cache.annotation.CacheUpdate;
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

    /**
     * Update collection type which store in cache like List, Set, ArrayList
     * @param joinPoint
     * @param cacheUpdate
     * @param returnValue
     * @throws Exception
     */
    @AfterReturning(pointcut = "@annotation(cacheCollection)", returning = "returnValue")
    public void cacheCollectionUpdate(final JoinPoint joinPoint, CacheCollection cacheCollection,
                    Object returnValue) throws Exception {
        _log.debug("cacheCollectionUpdate has been called");

        String[] cacheNames = cacheCollection.cacheNames();
        String key = cacheCollection.key();
        String[] compareProperties = cacheCollection.compareProperties();
        String condition = cacheCollection.condition();
        
        if (Validator.isNotNull(condition)) {
            ExpressionParser parser = new SpelExpressionParser();

            StandardEvaluationContext context = new StandardEvaluationContext(returnValue);
            
            context.setVariable("returnValue", returnValue);

            Expression exp = parser.parseExpression(condition);

            boolean conditionResult = exp.getValue(context, Boolean.class);

            if (!conditionResult) {
                return;
            }
        }

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

                boolean found = false;
                
                while (iterator.hasNext()) {
                    Object c = iterator.next();
                    
                    if (!_class.isAssignableFrom(c.getClass())) {
                        break;
                    }

                    if (!compare(c, returnValue, compareProperties)) {
                        newCollection.add(c);
                    } else {
                        found = true;
                        
                        newCollection.add(returnValue);
                    }
                }
                
                if (!found) {
                    newCollection.add(returnValue);
                }
                
                cache.put(key, newCollection);
            }
        }
    }

    @AfterReturning(pointcut = "@annotation(cacheMap)", returning = "returnValue")
    public void cacheMapUpdate(final JoinPoint joinPoint, CacheMap cacheMap,
                    Object returnValue) throws Exception {
        _log.debug("cacheMapUpdate has been called");
        
        String[] cacheNames = cacheMap.cacheNames();
        String key = cacheMap.key();
        String keyExpression = cacheMap.keyExpression();
        String condition = cacheMap.condition();
        
        ExpressionParser parser = new SpelExpressionParser();

        StandardEvaluationContext context = new StandardEvaluationContext(returnValue);
        
        context.setVariable("returnValue", returnValue);

        if (Validator.isNotNull(condition)) {
            Expression conditionExp = parser.parseExpression(condition);
            
            boolean conditionResult = conditionExp.getValue(context, Boolean.class);

            if (!conditionResult) {
                return;
            }
        }

        Expression keyExp = parser.parseExpression(keyExpression);
        
        Object keyValue = keyExp.getValue(context);

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
            
            if (Map.class.isAssignableFrom(ob.getClass().getSuperclass())) {

                Map<Object, Object> mapOb = (Map<Object, Object>) ob;
                
                mapOb.put(keyValue, returnValue);
                
                cache.put(key, mapOb);
            }
        }
    }
    
    @AfterReturning(pointcut = "@annotation(cacheUpdate)", returning = "returnValue")
    public void cacheUpdate(final JoinPoint joinPoint, CacheUpdate cacheUpdate,
                    Object returnValue) throws Exception {
        _log.debug("cacheUpdate has been called");
        
        CacheCollection[] collections = cacheUpdate.collection();
        CacheMap[] maps = cacheUpdate.map();
        
        for (CacheCollection collection: collections) {
            this.cacheCollectionUpdate(joinPoint, collection, returnValue);
        }
        
        for (CacheMap map: maps) {
            this.cacheMapUpdate(joinPoint, map, returnValue);
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

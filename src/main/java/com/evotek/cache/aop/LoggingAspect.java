package com.evotek.cache.aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(com.evotek.cache.controller..*) || within(com.evotek.cache.service..*)")
    public void logBeforeFunctionPointcut() {

    }

    @Before("logBeforeFunctionPointcut()")
    public void logBeforeFunctionAdvice(JoinPoint joinPoint) {
        _log.info("Class {}. Function {}() with argument[s] = {}", joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

}

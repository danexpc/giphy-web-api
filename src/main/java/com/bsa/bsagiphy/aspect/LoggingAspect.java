package com.bsa.bsagiphy.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void springServicePointcut() {}

    @AfterThrowing(pointcut = "springServicePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception: {} in {}.{}() with cause = {}",e.getMessage(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }
}

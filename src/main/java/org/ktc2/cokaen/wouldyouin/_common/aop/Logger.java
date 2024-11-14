package org.ktc2.cokaen.wouldyouin._common.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class Logger {

    @Pointcut("execution(* org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter.*(..))")
    public void springFilterExecution() {
    }

    @Pointcut("execution(* org.ktc2.cokaen.wouldyouin..*.*(..)) && !springFilterExecution()")
    public void all() {
    }

    @Around("all()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String packageName = joinPoint.getSignature().getDeclaringTypeName()
            .replace("org.ktc2.cokaen.wouldyouin.", "")
            .replaceAll("\\.[^\\.]+$", "");
        Object[] args = joinPoint.getArgs();
        long start = System.currentTimeMillis();
        long timeInMs;
        Object result;
        log.debug("{} : CALL {}", packageName, methodName);
        log.debug("{} :     with param = {}", packageName, args);

        try {
            result = joinPoint.proceed();
        } catch(Throwable t) {
            log.debug("{} :      {} throws {}", packageName, methodName, t.getClass().getSimpleName());
            throw t;
        } finally {
            timeInMs = System.currentTimeMillis() - start;
            log.debug("{} : EXIT {}", packageName, methodName);
            log.debug("{} :     with executeTime = {}ms", packageName, timeInMs);
        }

        log.debug("{} :     with return      = {}", packageName, result);

        return result;
    }
}

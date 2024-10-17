package org.ktc2.cokaen.wouldyouin.Image.api;

import java.lang.reflect.Field;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class GetImageService {

    @Around("@annotation(IdentifyImageDomain)")
    public Object getEntityType(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> imageServiceClass = joinPoint.getTarget().getClass();
        Object result = joinPoint.proceed();
        return imageServiceClass;
    }

    @Around("@annotation(GetSubPath)")
    public Object getSubPath(ProceedingJoinPoint joinPoint) throws Throwable {
        Field field = joinPoint.getTarget().getClass().getDeclaredField("subPath");
        field.setAccessible(true);
        String subPath = (String) field.get(joinPoint.getTarget());
        Object result = joinPoint.proceed();
        return subPath;
    }
}
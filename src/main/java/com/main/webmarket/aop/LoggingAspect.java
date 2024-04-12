package com.main.webmarket.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:MissingJavadocMethod"})
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("execution(* com.main.webmarket.controllers.UserController.*(..))")
    public void loggerUserController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("User Request: {}.{}", className, methodName);
    }

    @After("execution(* com.main.webmarket.controllers.ProductController.*(..))")
    public void loggerBlogController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Product Request: {}.{}", className, methodName);
    }

    @After("execution(* com.main.webmarket.aop.ErrorHandlingAspect.*(..))")
    public void logException(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.error("ERROR: {}.{}", className, methodName);
    }
}

package com.main.webmarket.aop;

import com.main.webmarket.services.RequestCounterService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:MissingJavadocMethod"})
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private final RequestCounterService requestCounterService;

    @Autowired
    public LoggingAspect(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

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

    @After("execution(* com.main.webmarket.services.RequestCounterService.incrementRequestsCount())")
    public void logRequestsCounter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        long counterValue = requestCounterService.getRequestsCount();
        logger.info("[NEW COUNTER VALUE]: {}.{} - Counter: {}", className, methodName, counterValue);
    }
}


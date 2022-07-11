package com.feng.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class MyAspect {

    private static final Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Pointcut("@annotation(com.feng.annotation.SystemLog)")
    public void LogPointCut() {
    }

    @Before("LogPointCut()")
    public void beforeAdvice(JoinPoint point) {
        logger.info("前置通知,连接点{}", point);
    }

    @AfterReturning("LogPointCut()")
    private void afterReturn(JoinPoint point) {
        logger.info("后置通知,连接点{}", point);
    }
}

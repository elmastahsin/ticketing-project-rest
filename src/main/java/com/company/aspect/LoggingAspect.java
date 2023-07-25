package com.company.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userdetails  = (SimpleKeycloakAccount) authentication.getDetails();
        return userdetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.company.controller.ProjectController.*(..)) || execution(* com.company.controller.TaskController.*(..))")
    private void anyProjectAndTaskControllerPC() {
    }

    @Before("anyProjectAndTaskControllerPC()")
    public void beforeProjectAndTaskControllerAdvice(JoinPoint joinPoint) {
        log.info("Before -> Method : {} ,User {}"
                , joinPoint.getSignature().toShortString()
                , getUserName());

    }
    @AfterReturning(pointcut = "anyProjectAndTaskControllerPC()", returning = "results")
    public void afterReturningProjectAndTaskControllerAdvice(JoinPoint joinPoint, Object results) {
        log.info("AfterReturning -> Method : {} ,User {},Result : {}"
                , joinPoint.getSignature().toShortString()
                , getUserName()
                , results);

    }
}


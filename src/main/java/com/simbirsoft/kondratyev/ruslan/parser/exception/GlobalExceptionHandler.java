package com.simbirsoft.kondratyev.ruslan.parser.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.logging.Logger;

@Aspect
public class GlobalExceptionHandler {

    @AfterThrowing(pointcut = "within(com.simbirsoft.kondratyev.ruslan.parser.service.impl..*)", throwing= "exception")
    public void exceptionHendler(JoinPoint joinPoint, Throwable exception) {
        System.out.println("|---------------------------------------------------------------------|\n"+
                "Exception -> "+exception.getMessage() + ", something wrong, check URL address or local file"+
                "|---------------------------------------------------------------------|\n");
    }
}
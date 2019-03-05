package com.simbirsoft.kondratyev.ruslan.pizzeria.aspects;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.logging.Logger;

@Aspect

public class WebServiceLogger {

    private final static Logger LOGGER = Logger.getLogger(WebServiceLogger.class.getName());

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(com.simbirsoft.kondratyev.ruslan.pizzeria.service..*)"  +
              " || within(com.simbirsoft.kondratyev.ruslan.pizzeria.controllers..*)"+
              " || within(com.simbirsoft.kondratyev.ruslan.pizzeria.repository..*)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @AfterReturning(pointcut = "springBeanPointcut()", returning= "result")
    public void logWebServiceCall(JoinPoint thisJoinPoint, Object result) {

        String methodName = thisJoinPoint.getSignature().getName();

        LOGGER.info("Call method " + methodName + " with result " + result);
    }
}
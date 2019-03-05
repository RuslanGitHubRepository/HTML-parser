package com.simbirsoft.kondratyev.ruslan.pizzeria.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.logging.Logger;

@Aspect
public class WebServiceLogger {
    private final Logger logger = Logger.getLogger("PizzaMakerLogger");

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(com.simbirsoft.kondratyev.ruslan.pizzeria.repository..*)"  +
                " || within(com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pizza)" +
                " || within(com.simbirsoft.kondratyev.ruslan.pizzeria.controllers..*)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @AfterReturning(pointcut = "springBeanPointcut()", returning= "result")
    public void logWebServiceCall(JoinPoint thisJoinPoint, Object result) {

        String methodName = thisJoinPoint.getSignature().getName();

        logger.info("Call method " + methodName + " with result " + result);
    }
}

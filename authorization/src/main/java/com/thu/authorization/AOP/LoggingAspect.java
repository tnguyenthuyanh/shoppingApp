package com.thu.authorization.AOP;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

//    @Before("com.thu.authorization.AOP.PointCuts.inControllerLayer()")
//    public void logStartTime(){
//        logger.info("From LoggingAspect.logStartTime in controller: " + System.currentTimeMillis()); // advice
//    }

    @After("com.thu.authorization.AOP.PointCuts.inActionOnOrder()")
    public void logEndTime(JoinPoint joinPoint){
        logger.info("From action on order in service: " + System.currentTimeMillis()  + ": " + joinPoint.getSignature());
    }

//    @AfterReturning(value = "com.thu.authorization.AOP.PointCuts.inPlaceNewOrder()", returning = "res")
//    public void logReturnObject(JoinPoint joinPoint, Object res){
//        System.out.println("hehehe1");
//        logger.info("From LoggingAspect.logReturnObject in DAO: " + res + ": " + joinPoint.getSignature());
//    }
//
//    @AfterThrowing(value = "com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inControllerLayer()", throwing = "ex")
//    public void logThrownException(JoinPoint joinPoint, Throwable ex){
//        logger.error("From LoggingAspect.logThrownException in controller: " + ex.getMessage() + ": " + joinPoint.getSignature());
//    }

//    @Around("com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inDAOLayer()")
//    public Demo logStartAndEndTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        // before
//        logger.info("From LoggingAspect.logStartAndEndTime: " + proceedingJoinPoint.getSignature());
//        logger.info("Start time: " + System.currentTimeMillis());
//
//        //Invoke the actual object
//        Demo demo = (Demo) proceedingJoinPoint.proceed();
//
//        // after
//        logger.info("End time: " + System.currentTimeMillis());
//        return demo;
//    }
}

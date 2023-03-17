package com.thu.authorization.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

//    @Pointcut("within(com.thu.authorization.controller.*)")
//    public void inControllerLayer(){}
//
//    @Pointcut("bean(*Service)")
//    public void inService(){}

    @Pointcut("execution(* com.thu.authorization.dao.OrderDao.*Order(..))")
    public void inActionOnOrder(){}


}

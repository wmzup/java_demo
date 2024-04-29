package com.example.java_spring_boot.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(* com.example.java_spring_boot.controller.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("before advice start");
        System.out.println(getMethodName(joinPoint));
        Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);
        System.out.println("before advice end");
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("after advice start");
        System.out.println("after advice end");
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturing(JoinPoint joinPoint, Object result) {
        System.out.println("afterReturning advice start");

        if (result != null) {
            System.out.println("result: " + result.toString());
        } else {
            System.out.println("result: null");
        }

        System.out.println("afterReturning advice end");
    }

    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("afterThrowing advice start");
        System.out.println(getMethodName(joinPoint));
        Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);
        System.out.println(ex.getMessage());
        System.out.println("afterThrowing advice end");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around advice start");
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long spendTime = System.currentTimeMillis() - startTime;
        System.out.println("Time spend: " + spendTime);
        System.out.println("around advice end");
        return result;
    }

    public String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getName();
    }
}
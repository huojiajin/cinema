package howard.cinema.terminal.config;//package hx.service.manage.config;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @name: MyAspectj
// * @description: 切面控制
// * @author: huojiajin
// * @time: 2020/5/25 15:07
// */
//@Configuration
//@Aspect
//public class MyAspectj {
//
//    @AfterThrowing(value="execution (hx.service.manage.*(..))",throwing="e")
//    public void afterReturningMethod(JoinPoint joinPoint, Exception e){
//        String methodName = joinPoint.getSignature().getName();
//        System.out.println("The method name:"+ methodName + " ends and result="+e);
//    }
//}

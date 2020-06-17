package com.example.javabaisc.aop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//aop切面注解,作用是把当前类标识为一个切面,供容器读取
@Aspect
@Component
public class MyAspect2 {

    //定义一个公用方法
    //参数意思是 公共的 任意返回类型 该类里的所有方法 任意输入参数
    //切入点，表示切入的点，即程序中通用的逻辑业务，这里是请求的路径
    @Pointcut("execution(public * com.example.javabaisc.aop.Mtest.*(..))")
    public void log() {
    }


    //前置通知  ，表示当前方法是在具体的请求方法之前执行  【权限控制（权限不足抛出异常）、记录方法调用信息日志 】
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //如果是controller层 可使用这两句获取 参数 ，当然如果是restful 参数得自己解析路径
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        //
        //获取请求的url
//        String url = request.getRequestURI();
        //获取请求的方法
//        String method =request.getMethod();
        //获取请求的ip
//        String ip = request.getRemoteAddr();
        //获取请求参数,返回来的是个object 数组
//        Object[] ogj = joinPoint.getArgs();
        //
        String ss = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        System.out.println(ss);
//        logger.info("{}",ss);
        System.out.println("我是注解Before，前置通知,好饿啊");
    }

    //异常抛出通知 【处理异常（一般不可预知），记录日志 】
    @AfterThrowing("log()")
    public void throwss(JoinPoint jp) {
        System.out.println("方法异常时执行.....");
    }

//    //环绕通知,环绕增强  【日志、缓存、权限、性能监控、事务管理 】
//    @Around("log()")
//    public Object arround(ProceedingJoinPoint pjp) {
//        System.out.println("方法环绕start.....");
//        try {
//            Object o = pjp.proceed();
//            System.out.println("方法环绕proceed，结果是 :" + o);
//            return o;
//        } catch (Throwable e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    //返回通知 ，在方法正常退出之后执行   【 如银行在存取款结束后的发送短信消息 】
    @AfterReturning(returning = "obj", pointcut = "log()")
    public void doAfterReturnig(Object obj) {
        System.out.println("====================");
        System.out.println("reponse参数：");
        System.out.println(obj);
        System.out.println("我是后置通知");
        System.out.println("====================");
    }


    //后置通知,不管是抛出异常或者正常退出都会执行 ，都会执行（类似于finally代码功能）  【释放资源 （关闭文件、 关闭数据库连接、 网络连接、 释放内存对象 】
    @After("log()")
    public void doAfter() {
        System.out.println("我是注解after，最终通知,吃饱了吧");

    }


}

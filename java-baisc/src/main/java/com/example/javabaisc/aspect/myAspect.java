package com.example.javabaisc.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


//aop切面注解,作用是把当前类标识为一个切面,供容器读取
@Aspect
@Component
//定义切面执行顺序，如果在同一个方法有多个切面，那么需要定义使用顺序，先开始执行的切面将最后结束，呈包含关系，数字越小，执行的越早。
@Order(1)
public class myAspect {


    //定义一个公用方法
//    切入点，表示切入的点，即程序中通用的逻辑业务，这里是请求的路径
    //
    //参数意思是 公共的 任意返回类型 该类里的所有方法 任意输入参数
    //参数public可要可不要
    @Pointcut("execution(public * com.example.javabaisc.controller.VVController.*(..))")
    public void log() {
    }


    //前置通知  ，表示当前方法是在具体的请求方法之前执行  【权限控制（权限不足抛出异常）、记录方法调用信息日志 】
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("====================");
        System.out.println("前置通知---before");
        System.out.println("====================");
        //如果是controller层 可使用这两句获取 参数 ，当然如果是restful 参数得自己解析路径
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        //
        //获取请求的url
//        String url = request.getRequestURI();
        //
        //获取请求的方法
//        String method =request.getMethod();
        //
        //获取请求的ip
//        String ip = request.getRemoteAddr();
        //
        //获取请求参数,返回来的是个object 数组
//        Object[] args = joinPoint.getArgs();
        //也可以转成 list
//        List<Object> args = Arrays.asList(joinPoint.getArgs());
        //
        //获取该切入点所在方法的路径，getDeclaringTypeName()获取类名，joinPoint.getSignature().getName()获取方法名
//        String ss = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//        System.out.println(ss);
        //打印com.example.javabaisc.controller.VVController.gg
        //
        //可以获取目标class
//        Class clazz = joinPoint.getSignature().getDeclaringType();


    }

    //异常通知 ：在目标方法出现异常时才会进行执行的代码。【处理异常（一般不可预知），记录日志 】
    //方法体Exception参数：用来接收连接点抛出的异常。Exception类匹配所有的异常，可以指定为特定的异常 例如NullPointerException类等
    @AfterThrowing(pointcut = "log()", throwing = "ex")
    public void throwss(JoinPoint joinPoint, Exception ex) {
        System.out.println("====================");
        System.out.println("异常通知.....afterthrowing");
        System.out.println("====================");
    }


    //返回通知： 在目标方法正常结束时，才执行的通知    【 如银行在存取款结束后的发送短信消息 】
    @AfterReturning(returning = "obj", pointcut = "log()")
    public void doAfterReturnig(JoinPoint joinPoint, Object obj) {
        //处理完请求，返回内容
        System.out.println("====================");
        System.out.println("返回通知，reponse参数：");
        System.out.println(obj);
        System.out.println("返回通知---afterreturening");
        System.out.println("====================");
    }


    //后置通知,不管是抛出异常或者正常退出都会执行 ，都会执行（类似于finally代码功能）  【释放资源 （关闭文件、 关闭数据库连接、 网络连接、 释放内存对象 】
    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("====================");
        System.out.println("后置通知---adfer");
        System.out.println("====================");
    }

    //环绕通知,环绕增强  【日志、缓存、权限、性能监控、事务管理 】
    @Around("log()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("====================");
        System.out.println("环绕通知--开始--在前置通知之前");
        Object result = null;
        //获取参数
//        String methodName = pjp.getSignature().getName();
//        List<Object> args = Arrays.asList(pjp.getArgs());
//        try {
            //获取结果
            result = pjp.proceed();
            System.out.println("环绕通知around，结果是 :" + result);
            System.out.println("环绕通知------在方法结束之后");
            System.out.println("====================");

//        } catch (Throwable e) {
//            System.out.println("环绕通知---在环绕通知的方法里的 异常捕获 导致服务降级，不再触发异常通知方法，也就是说如果需要使用环绕通知，不允许在这里使用 catch ,可使用throws Throwable即可");
//            e.printStackTrace();
//        }
        System.out.println("环绕通知---在后置通知之前  ");
        return result;
    }
}

/*
====================
环绕通知--开始--在前置通知之前
====================
前置通知---before
====================
=yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy
吃什么
=yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy
环绕通知around，结果是 :apple and tea
环绕通知------在方法结束之后
====================
环绕通知---在后置通知之前
====================
后置通知---adfer
====================
====================
返回通知，reponse参数：
apple and tea
返回通知---afterreturening
====================

 */


/*
====================
环绕通知--开始--在前置通知之前
====================
前置通知---before
====================
====================
后置通知---adfer
====================
====================
异常通知.....afterthrowing
====================
2020-06-12 02:29:49.895 ERROR 12976 --- [nio-8080-exec-3] o.a.c.c.C.[.[.[/].[dispatcherServlet]
java.lang.RuntimeException: null
 */

/*
====================
环绕通知--开始--在前置通知之前
====================
前置通知---before
====================
环绕通知---在环绕通知的方法里的 异常捕获 导致服务降级，不再触发异常通知方法，也就是说如果需要使用环绕通知，不允许在这里使用 catch ,可使用throws Throwable即可
环绕通知---在后置通知之前
====================
后置通知---adfer
====================
====================
返回通知，reponse参数：
null
返回通知---afterreturening
====================
java.lang.RuntimeException
	at com.example.javabaisc.controller.VVController.gg(VVController.java:20)
 */

/*
通知、增强处理（Advice） 就是你想要的功能，也就是上说的安全、事物、日子等。你给先定义好，然后再想用的地方用一下。包含Aspect的一段处理代码
连接点（JoinPoint） 这个就更好解释了，就是spring允许你是通知（Advice）的地方，那可就真多了，基本每个方法的钱、后（两者都有也行），或抛出异常是时都可以是连接点，spring只支持方法连接点。其他如AspectJ还可以让你在构造器或属性注入时都行，不过那不是咱们关注的，只要记住，和方法有关的前前后后都是连接点。
切入点（Pointcut） 上面说的连接点的基础上，来定义切入点，你的一个类里，有15个方法，那就有十几个连接点了对吧，但是你并不想在所有方法附件都使用通知（使用叫织入，下面再说），你只是想让其中几个，在调用这几个方法之前、之后或者抛出异常时干点什么，那么就用切入点来定义这几个方法，让切点来筛选连接点，选中那几个你想要的方法。
切面（Aspect） 切面是通知和切入点的结合。现在发现了吧，没连接点什么事，链接点就是为了让你好理解切点搞出来的，明白这个概念就行了。通知说明了干什么和什么时候干（什么时候通过方法名中的befor，after，around等就能知道），二切入点说明了在哪干（指定到底是哪个方法），这就是一个完整的切面定义。
引入（introduction） 允许我们向现有的类添加新方法属性。这不就是把切面（也就是新方法属性：通知定义的）用到目标类中吗
目标（target） 引入中所提到的目标类，也就是要被通知的对象，也就是真正的业务逻辑，他可以在毫不知情的情况下，被咋们织入切面。二自己专注于业务本身的逻辑。
代理（proxy） 怎么实现整套AOP机制的，都是通过代理，这个一会儿给细说。
织入（weaving） 把切面应用到目标对象来创建新的代理对象的过程。有三种方式，spring采用的是运行时，为什么是运行时，在上一文《Spring
 AOP开发漫谈之初探AOP及AspectJ的用法》中第二个标提到。
目标对象 – 项目原始的Java组件。
AOP代理  – 由AOP框架生成java对象。
AOP代理方法 = advice +　目标对象的方法。
————————————————
版权声明：本文为CSDN博主「Shawn_wg」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u013782203/article/details/51799427
 */
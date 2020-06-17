package com.example.javabaisc.proxy.mdynamic;

import com.example.javabaisc.proxy.mdynamic.mproxy.DynamicProxy;
import com.example.javabaisc.proxy.mdynamic.service.UserService;
import com.example.javabaisc.proxy.mdynamic.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * 动态代理 ，也叫jdk代理 、接口代理
 */
public class MDTest {

    @Test
    public void t(){

        //实例目标对象【类型是接口或实现类都可以，我喜欢用接口】
        UserService userService = new UserServiceImpl();
        //实例动态代理对象，注入目标对象，然后在内存创建该目标对象的代理对象，返回结果强转成目标对象一样的类型
        //这样好处就是只要增强操作是一样的，则可以统一使用这个动态代理类
        UserService userServiceProxy = (UserService)new DynamicProxy(userService).getProxyInstance();
        //执行代理对象，方法则与目标类的相同
        userServiceProxy.getname();



    }
}
/*
总结：
（1）动态代理又称为jdk代理、接口代理。
（2）动态代理解决静态代理的缺点，代理类不需要再与目标对象实现一样的接口。
（3）实例代理类后，注入目标类对象即可，然后调用代理类的方法会在内存动态的创建代理对象【使用Proxy.newProxyInstance】后将该代理对象返回，
然后强转成目标对象一样的类型即可按照目标类型方法调用，创建代理对象需要输入的参数 分别是 目标类的类加载器 、目标对象实现的接口的类型、事件处理器 ，
然后重写事件处理器里的方法，该方法内写自定义的增强业务，而目标对象的方法调用则固定使用 Object returnValue = method.invoke(target, args);完成 ，
因此，如果需要对调用的当前方法分别做不同的操作，可以使用method.getName()获取当前执行的方法名字后判断，然后执行不同的增强操作，
那么如果需要对不同的目标类做不同的操作呢？那么可以使用target.getClass().getName()获取当前注入的目标类对象的名字。
这么做的好处就是不需要创建大量动态代理类做不同的增强操作，这是与静态代理最大的区别，但是我感觉这样做会变不伦不类了。
这样做的流程是 实例代理类-》注入目标对象-》内存创建代理对象-》判断当前注入对象名称-》判断当前执行的方法-》做增强业务-》返回结果
（3）虽然代理类没有直接实现在目标类所实现的接口，但是在内存动态的创建代理对象 方法的注入参数需要 目标对象实现的接口的类型 ，
因此目标类必须要最少实现一个接口 ，否则无法使用动态代理。
（4）缺点：有些类不是业务层的，仅仅是一个单独的类 ，没有实现任何接口，是不能使用动态代理的。

 */
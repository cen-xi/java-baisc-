package com.example.javabaisc.ioc;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class IOCTest {
    @Test
    public void t() {
        //低级容器【被抛弃了，不建议使用】
        //初始化xml文件里的javabean
//        BeanFactory javabean = new XmlBeanFactory(new ClassPathResource("iocConfig.xml"));
        //高级容器
        //全局初始化xml文件里的javabean
        ApplicationContext javabean = new ClassPathXmlApplicationContext("iocConfig.xml");
        //实例容器类
        MContainer mContainer = (MContainer) javabean.getBean("mContainer");
        mContainer.eat();

    }
}

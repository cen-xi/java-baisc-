package com.example.javabaisc.aop;


import com.example.javabaisc.JavaBaiscApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 单元测试使用注解配置 AOP切面失败
 */
//单元测试，启动spring注解
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaBaiscApplication.class)
//@ComponentScan("com.example.javabaisc.aop.aspect.MyAspect2")
//@EnableAspectJAutoProxy()
//@ContextConfiguration("classpath*:com/example/javabaisc/aop/apsectConfig.xml")
public class Mtest {

//    @Autowired
//    private FoodService foodService;

    @Test
    public void t2() {
//        System.out.println(foodService.food());
        System.out.println("=============================================");
        System.out.println("=============================================");
        System.out.println("=============================================");
        System.out.println("6666666666");
        System.out.println("=============================================");
        System.out.println("=============================================");
        System.out.println("=============================================");

    }


}

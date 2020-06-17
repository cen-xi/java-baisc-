package com.example.javabaisc.log;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoggingTest {

    //    记录器
    Logger log = LoggerFactory.getLogger(getClass());
    {

    }
    @Test
    public void t() {
        //日志的级别；
        //由低到高 trace<debug<info<warn<error
        //可以调整输出的日志级别；日志就只会在这个级别之后的高级别生效
        log.trace("trace级别的日志--11111");
        log.debug("debug级别的日志--2222222");
        //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
        log.info("info级别的日志--33333");
        log.warn("warn级别的日志--44444444");
        log.error("error级别的日志--5555555");
    }

}

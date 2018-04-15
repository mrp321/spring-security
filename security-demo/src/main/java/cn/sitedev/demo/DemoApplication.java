package cn.sitedev.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description springboot启动类
 * @author QChen
 * @date 2018/4/13 0013 2:15
 *
 */
//SpringBootApplication启动时会默认扫描主类当前包及子包
//如果需要扫描主类当前包外的其他包,需要使用到scanBasePackages属性
@SpringBootApplication(scanBasePackages = ("cn.sitedev"))
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

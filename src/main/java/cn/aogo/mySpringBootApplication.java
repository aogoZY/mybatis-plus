package cn.aogo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //申明该类是一个springboot引导类
@MapperScan("cn.aogo.mapper")
public class mySpringBootApplication {
    public static void main(String[] args) {

        SpringApplication.run(mySpringBootApplication.class,args);
    }
}

package com.wdd.mybatch;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScans({@MapperScan("com.wdd.mybatch.core.mapper")})
@SpringBootApplication
public class MybatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatchApplication.class, args);
    }

}

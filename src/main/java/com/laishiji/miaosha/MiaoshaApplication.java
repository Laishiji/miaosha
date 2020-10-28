package com.laishiji.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.laishiji.miaosha"})//设置配置Spring时的扫描路径
@MapperScan("com.laishiji.miaosha.dao")//扫描dao
public class MiaoshaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }

}

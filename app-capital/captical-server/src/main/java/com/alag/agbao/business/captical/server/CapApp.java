package com.alag.agbao.business.captical.server;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableDubboConfiguration
@EnableAsync
public class CapApp {
    public static void main(String[] args) {
        SpringApplication.run(CapApp.class, args);
    }
}

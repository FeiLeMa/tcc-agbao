package com.alag.agbao.business.packet.server;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubboConfiguration
@SpringBootApplication
@EnableTransactionManagement
public class PacketApp {
    public static void main(String[] args) {
        SpringApplication.run(PacketApp.class, args);
    }
}

package com.alag.agbao.business.order.service;

import com.alag.agbao.business.core.common.ServerResponse;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
@EnableDubboConfiguration
public interface OrderService {
    ServerResponse alipayBack(Map<String, String> requestParamMap);
}

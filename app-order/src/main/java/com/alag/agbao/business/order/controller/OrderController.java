package com.alag.agbao.business.order.controller;


import com.alag.agbao.business.core.common.ServerResponse;
import com.alag.agbao.business.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/alipay_notify_call")
    public ServerResponse simulateReceive(@RequestBody Map<String, String> requestParamMap) {
        return orderService.alipayBack(requestParamMap);
    }
}

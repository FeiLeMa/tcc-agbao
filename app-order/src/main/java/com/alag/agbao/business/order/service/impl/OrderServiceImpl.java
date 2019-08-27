package com.alag.agbao.business.order.service.impl;

import com.alag.agbao.business.captical.api.service.CapService;
import com.alag.agbao.business.core.common.Const;
import com.alag.agbao.business.core.common.ServerResponse;
import com.alag.agbao.business.core.util.DateTimeUtil;
import com.alag.agbao.business.order.mapper.OrderMapper;
import com.alag.agbao.business.order.model.Order;
import com.alag.agbao.business.order.service.OrderService;
import com.alag.agbao.business.packet.api.service.RedPacketService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Reference
    private RedPacketService redPacketService;
    @Reference
    private CapService capService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse alipayBack(Map<String, String> requestParamMap) {
        final String orderNo = requestParamMap.get("out_trade_no");
        final String tradeStatus = requestParamMap.get("trade_status");
        final String gmtPaymentStr = requestParamMap.get("gmt_payment");
        Date gmtPayment = DateTimeUtil.strToDate(gmtPaymentStr);

        Order order = orderMapper.selectByOrderId(orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("不存在的订单");
        }

        if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createByErrorMessage("幂等判断，订单已经支付");
        }
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {

            //修改订单状态
            order.setUpdateTime(new Date());
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            order.setPaymentTime(gmtPayment);
            int row = orderMapper.updateByPrimaryKeySelective(order);

            if (row > 0) {
                //派送红包
                redPacketService.dispatchRedPacket(order.getOrderNo(),order.getUserId(),Const.RedPacket.RED_MONEY);
                //商户加款
                capService.addAmount(order.getUserId(), order.getOrderNo(), order.getPayment());
            } else {
                return ServerResponse.createByErrorMessage("order修改订单失败");
            }
        }
        return ServerResponse.createBySuccess("执行完毕");
    }
}

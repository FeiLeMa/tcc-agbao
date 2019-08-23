package com.alag.agbao.business.packet.server.service.impl;

import com.alag.agbao.business.core.common.ServerResponse;
import com.alag.agbao.business.core.util.BigDecimalUtil;
import com.alag.agbao.business.core.util.IDGenerator;
import com.alag.agbao.business.packet.api.model.RedAccount;
import com.alag.agbao.business.packet.api.model.RedTradeOrder;
import com.alag.agbao.business.packet.api.service.RedPacketService;
import com.alag.agbao.business.packet.server.mapper.RedAccountMapper;
import com.alag.agbao.business.packet.server.mapper.RedTradeOrderMapper;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Slf4j
@Component
@Service(interfaceClass = RedPacketService.class, timeout = 30000)
public class RedPacketServiceImpl implements RedPacketService {

    @Autowired
    private RedAccountMapper redAccountMapper;
    @Autowired
    private RedTradeOrderMapper redTradeOrderMapper;

    @Override
    @Transactional
    public ServerResponse dispatchRedPacket(String orderNo, String userId, BigDecimal redMoney) {
        RedTradeOrder redTradeOrder = RedTradeOrder.setReturn(rTOrder -> {
            rTOrder.setAmount(redMoney);
            rTOrder.setId(IDGenerator.sixStrID());
            rTOrder.setUserId(userId);
            rTOrder.setOrderNo(orderNo);
            return rTOrder;
        });
        int row = redTradeOrderMapper.insertSelective(redTradeOrder);
        if (row > 0) {
            RedAccount packetAccount = redAccountMapper.selectByUserId(userId);

            BigDecimal beforeAmount = packetAccount.getAmount();
            BigDecimal afterAmount = beforeAmount.add(redMoney);
            row = redAccountMapper.updateByIdAndVersion(packetAccount.getId(), packetAccount.getVersion(), afterAmount);
            if (row > 0) {
                return ServerResponse.createBySuccess();
            } else {
                this.reTryAddRedToAccount(userId,redMoney);
            }
        }
        return ServerResponse.createByError();
    }

    @Async
    public void reTryAddRedToAccount(String userId,BigDecimal redMoney) {
        log.info("正在重试...");
        int row = 0;
        while (true) {
            RedAccount packetAccount = redAccountMapper.selectByUserId(userId);

            BigDecimal beforeAmount = packetAccount.getAmount();
            BigDecimal afterAmount = beforeAmount.add(redMoney);

            row = redAccountMapper.updateByIdAndVersion(packetAccount.getId(), packetAccount.getVersion(), afterAmount);
            if (row > 0) {
                log.info("重试成功");
                return;
            }
        }
    }


}

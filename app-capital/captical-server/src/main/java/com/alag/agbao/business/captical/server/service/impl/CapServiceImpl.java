package com.alag.agbao.business.captical.server.service.impl;


import com.alag.agbao.business.captical.api.model.CapAccount;
import com.alag.agbao.business.captical.api.model.CapTradeOrder;
import com.alag.agbao.business.captical.api.service.CapService;
import com.alag.agbao.business.captical.server.mapper.CapAccountMapper;
import com.alag.agbao.business.captical.server.mapper.CapTradeOrderMapper;
import com.alag.agbao.business.core.common.Const;
import com.alag.agbao.business.core.common.ServerResponse;
import com.alag.agbao.business.core.util.BigDecimalUtil;
import com.alag.agbao.business.core.util.IDGenerator;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Slf4j
@Component
@Service(interfaceClass = CapService.class,timeout = 30000)
public class CapServiceImpl implements CapService {

    @Autowired
    private CapAccountMapper capAccountMapper;
    @Autowired
    private CapTradeOrderMapper capTradeOrderMapper;

    @Override
    @Transactional
    public ServerResponse addAmount(String userId, String orderNo, BigDecimal payment) {
        CapTradeOrder capTradeOrder = CapTradeOrder.setReturn(cTOrder -> {
            cTOrder.setAmount(payment);
            cTOrder.setId(IDGenerator.sixStrID());
            cTOrder.setOrderNo(orderNo);
            cTOrder.setUserId(userId);
            return cTOrder;
        });
        int row = capTradeOrderMapper.insertSelective(capTradeOrder);
        if (row > 0) {
            CapAccount capAccount = capAccountMapper.selectByPrimaryKey(Const.Merchant.MERCHANT_NO);

            BigDecimal beforeAmount = capAccount.getAmount();
            BigDecimal afterAmount = beforeAmount.add(payment);

            row = capAccountMapper.updateByIdAndVersion(capAccount.getAccountId(),capAccount.getVersion(),afterAmount);
            if (row > 0) {
                return ServerResponse.createBySuccess();
            } else {
                this.reTryAddMoneyToAccount(payment);
            }
        }
        return ServerResponse.createByError();
    }
    @Async
    public void reTryAddMoneyToAccount(BigDecimal payment) {
        log.info("正在重试...");
        int row = 0;
        while (true) {
            CapAccount capAccount = capAccountMapper.selectByPrimaryKey(Const.Merchant.MERCHANT_NO);

            BigDecimal beforeAmount = capAccount.getAmount();
            BigDecimal afterAmount = beforeAmount.add(payment);

            row = capAccountMapper.updateByIdAndVersion(capAccount.getAccountId(),capAccount.getVersion(),afterAmount);
            if (row > 0) {
                log.info("重试成功");
                return;
            }

        }
    }
}

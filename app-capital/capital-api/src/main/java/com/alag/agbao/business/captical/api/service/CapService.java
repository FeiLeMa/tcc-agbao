package com.alag.agbao.business.captical.api.service;

import com.alag.agbao.business.core.common.ServerResponse;
import org.dromara.hmily.annotation.Hmily;

import java.math.BigDecimal;

public interface CapService {
    @Hmily
    ServerResponse addAmount(String userId, String orderNo, BigDecimal payment);
}

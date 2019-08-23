package com.alag.agbao.business.captical.api.service;

import com.alag.agbao.business.core.common.ServerResponse;

import java.math.BigDecimal;

public interface CapService {
    ServerResponse addAmount(String userId, String orderNo, BigDecimal payment);
}

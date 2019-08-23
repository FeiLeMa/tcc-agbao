package com.alag.agbao.business.packet.api.service;

import java.math.BigDecimal;
import com.alag.agbao.business.core.common.ServerResponse;

public interface RedPacketService {
     ServerResponse dispatchRedPacket(String orderNo, String userId, BigDecimal redMoney);
}

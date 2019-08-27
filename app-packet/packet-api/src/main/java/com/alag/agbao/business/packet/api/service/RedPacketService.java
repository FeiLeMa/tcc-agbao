package com.alag.agbao.business.packet.api.service;

import java.math.BigDecimal;
import com.alag.agbao.business.core.common.ServerResponse;
import org.dromara.hmily.annotation.Hmily;

public interface RedPacketService {
     @Hmily
     ServerResponse dispatchRedPacket(String orderNo, String userId, BigDecimal redMoney);
}

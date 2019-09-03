package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrAdjustOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 会员自调酒业务层
 */
public interface MbrAdjustOrderService extends BaseService<MbrAdjustOrder, Long> {

    /**
     * 生成储酒订单
     *
     * @return  订单号 orderNo
     */
    public Map<String, Object> buildAdjustOrder(Long adjustId, Member member ,BigDecimal payTotalAmt);

}

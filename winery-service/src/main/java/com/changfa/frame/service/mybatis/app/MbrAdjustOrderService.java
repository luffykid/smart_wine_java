package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrAdjustOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.math.BigDecimal;
import java.util.Date;
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


    /**
     * 处理会员自调酒订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */
    void handleNotifyOfAdjustOrder(String outTradeNo, String transactionId, Date payDate);
}

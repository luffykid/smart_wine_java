package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.Date;

public interface MbrProdOrderService extends BaseService<MbrProdOrder, Long> {

    /**
     * 处理会员商品订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */
    void handleNotifyOfProdOrder(String outTradeNo, String transactionId, Date payDate);
}

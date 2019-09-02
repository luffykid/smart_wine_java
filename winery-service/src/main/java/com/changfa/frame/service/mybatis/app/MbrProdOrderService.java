package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.model.app.MbrProdOrderItem;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;
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

    /**
     * 用户下单
     * @param wineryId 酒庄酒id
     * @param mbrId 会员id
     * @param items 订单项列表
     * @return 状态为未支付的订单
     */
    MbrProdOrder placeAnOrder(Long wineryId, Long mbrId, List<MbrProdOrderItem> items);

}

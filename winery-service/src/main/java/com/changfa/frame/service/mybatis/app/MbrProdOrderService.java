package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.model.app.MbrProdOrderItem;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;
import java.util.Date;
import java.util.List;

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

    /**
     * 给订单添加会员地址和支付模式
     * @param mbrProdOrderId 产品订单id
     * @param mbrAddressId 会员地址id
     * @param payMode 支付模式
     */
    MbrProdOrder addMbrAddressInfoAndChoosePayMode(Long mbrProdOrderId, Long mbrAddressId, Integer payMode);

    /** 根据状态获取订单列表
     *
     * @param mbrId    会员ID
     * @param status   状态

     */
    List<MbrProdOrder> getListByStatus(Long mbrId, Integer status);
}

package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.model.app.MbrProdOrderItem;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MbrProdOrderService extends BaseService<MbrProdOrder, Long> {
    /**
     * 获取我的订单分类信息
     *
     * @return
     */
    PageInfo getListByType(Long mbrId, Integer orderStatus, PageInfo pageInfo);

    /**
     * 用户下单
     * @param items 订单项列表
     * @return 状态为未支付的订单
     */
    MbrProdOrder placeAnOrder(List<MbrProdOrderItem> items);

}

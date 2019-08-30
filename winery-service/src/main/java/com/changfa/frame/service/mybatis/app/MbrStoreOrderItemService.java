package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrStoreOrderItem;
import com.changfa.frame.service.mybatis.common.BaseService;

public interface MbrStoreOrderItemService extends BaseService<MbrStoreOrderItem, Long> {
    /**
     * 生成储酒订单项
     *
     * @return
     */
    void buildStoreOrderItem();
}

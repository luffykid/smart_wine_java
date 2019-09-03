/*
 * MbrProdOrderItemMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrProdOrderItem;

import java.util.List;

public interface MbrProdOrderItemMapper extends BaseMapper<MbrProdOrderItem, Long> {

    /**
     * 获取mbrProdOrderId下的所有订单项
     */
    List<MbrProdOrderItem> getByMbrProdOrderId(Long mbrProdOrderId);

}
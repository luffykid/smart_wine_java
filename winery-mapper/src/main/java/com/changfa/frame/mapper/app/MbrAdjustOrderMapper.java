/*
 * MbrAdjustOrderMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-31 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrAdjustOrder;

public interface MbrAdjustOrderMapper extends BaseMapper<MbrAdjustOrder, Long> {

    //根据订单号获取订单
    MbrAdjustOrder getByOrderNo(String outTradeNo);
}
/*
 * MbrWineCustomOrderMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrWineCustomOrder;

public interface MbrWineCustomOrderMapper extends BaseMapper<MbrWineCustomOrder, Long> {

    //根据订单号获取订单

    MbrWineCustomOrder getByOrderNo(String outTradeNo);
}
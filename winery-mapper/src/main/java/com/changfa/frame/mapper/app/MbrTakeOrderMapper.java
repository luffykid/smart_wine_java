/*
 * MbrTakeOrderMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrTakeOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MbrTakeOrderMapper extends BaseMapper<MbrTakeOrder, Long> {

    /**
     * 获取储酒提酒列表
     * @param mbrStoreOrderId
     * @return
     */
    public List<MbrTakeOrder> selectListByMbrStoreOrderId(@Param("mbrStoreOrderId") Long mbrStoreOrderId);
}
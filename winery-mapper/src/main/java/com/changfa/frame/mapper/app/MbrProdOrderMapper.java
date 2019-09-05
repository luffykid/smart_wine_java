/*
 * MbrProdOrderMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrProdOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MbrProdOrderMapper extends BaseMapper<MbrProdOrder, Long> {

    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return
     */
    MbrProdOrder getByOrderNo(String orderNo);
    /**
     * 根据会员和状态查询订单
     * @param mbrId 会员ID
     * @param status 订单状态
     */
    List<MbrProdOrder> getListByStatus(@Param("mbrId") Long mbrId, @Param("status") Integer status);
    /**
     * 根据会员查询订单
     * @param mbrId 会员ID
     * @return
     */
    List<MbrProdOrder> getListByMbrId(Long mbrId);
}
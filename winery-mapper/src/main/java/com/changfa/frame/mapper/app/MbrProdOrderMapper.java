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
import org.hibernate.validator.constraints.EAN;

import java.util.List;

public interface MbrProdOrderMapper extends BaseMapper<MbrProdOrder, Long> {

    /**
     * 查询订单列表
     * @param mbrId
     * @return
     */
    public List<MbrProdOrder> selectListByType(@Param("mbrId") Long mbrId,@Param("orderStatus") Integer orderStatus);
}
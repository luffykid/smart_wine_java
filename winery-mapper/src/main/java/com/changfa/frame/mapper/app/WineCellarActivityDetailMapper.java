/*
 * WineCellarActivityDetailMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-27 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineCellarActivityDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WineCellarActivityDetailMapper extends BaseMapper<WineCellarActivityDetail, Long> {
    /**
     * 获取活动关联商品
     * @param wineCellarActivityId
     * @return
     */
    List<Map> selectProdSkuList(@Param("wineCellarActivityId") Long wineCellarActivityId);

    /**
     * 活动订单预支付详情
     * @param id
     * @return
     */
    Map getPrePayDetail(@Param("id") Long id);

    /**
     * 获取商品详情
     * @param id
     * @return
     */
    List<Map> selectProdSkuDetail(@Param("id") Long id);
}
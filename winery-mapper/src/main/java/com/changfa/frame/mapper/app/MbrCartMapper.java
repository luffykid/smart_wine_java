/*
 * MbrCartMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-02 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrCart;

public interface MbrCartMapper extends BaseMapper<MbrCart, Long> {

    MbrCart getByProdSkuId(Long prodSkuId);

}
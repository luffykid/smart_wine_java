/*
 * ProdSkuMbrPriceMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.ProdSkuMbrPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdSkuMbrPriceMapper extends BaseMapper<ProdSkuMbrPrice, Long> {
    List<ProdSkuMbrPrice> getBySkuId(Long skuId);

    int deleteByProdSkuId(Long id);

    List<ProdSkuMbrPrice> findProdSkuMbrPriceListByProdSkuId(Long id);

    ProdSkuMbrPrice getBySkuIdAndMbrLevelId(@Param("id") Long id, @Param("mbrLevelId") Long mbrLevelId);

}
/*
 * ProdSkuMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.ProdSkuMbrPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdSkuMapper extends BaseMapper<ProdSku, Long> {

    List<ProdSku> getByProdId(Long id);

    int updateSkuStatusByProdId(@Param("id") Long id, @Param("status") Integer status);

    List<ProdSku> selectProdSkuStatusByProdId(Long id, Long status);

    int updateProdSkuStatus(@Param("id") Long id, @Param("skuStatus") Integer skuStatus);

    int deleteProdSku(Long id);

    int add(ProdSku prodSku);

    String getProdNameBySkuId(Long prodSkuId);

    ProdSku getProdNameBySkuIdLike(Long prodSkuId);
}
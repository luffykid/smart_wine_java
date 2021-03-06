/*
 * ProdMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.Prod;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdMapper extends BaseMapper<Prod, Long> {

    List<Prod> findProdList();

    int updateProdStatus(@Param("id") Long id,@Param("skuStatus") Integer skuStatus);

    int deleteByid(Long id);

    List<Prod> findProdByLikeName(String prodName, Long lableType);
}
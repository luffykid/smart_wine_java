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
import com.changfa.frame.model.app.ProdSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdMapper extends BaseMapper<Prod, Long> {

    List<Prod> findProdList();

    int updateProdStatus(@Param("id") Long id,@Param("status") Integer status);

    int deleteByid(Long id);

    List<Prod> findProdByLikeName(String prodName, Long lableType);

    int add(Prod prod);

    int updateProd(Prod prod);

    List<ProdSku> getProdNameList();

    List<Prod> selectListLikeName(Prod prod);
}
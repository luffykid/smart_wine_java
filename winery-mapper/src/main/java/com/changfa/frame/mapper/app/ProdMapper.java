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

import java.util.List;

public interface ProdMapper extends BaseMapper<Prod, Long> {

    List<Prod> findProdList();
}
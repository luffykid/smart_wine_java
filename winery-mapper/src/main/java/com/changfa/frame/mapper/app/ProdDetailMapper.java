/*
 * ProdDetailMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.ProdDetail;

import java.util.List;

public interface ProdDetailMapper extends BaseMapper<ProdDetail, Long> {

    List<ProdDetail> getProdImgTextByProdId(Long id);

    int updateByProdId(ProdDetail prodDetail);

    int deleteByProdId(Long id);

    int deleteProdDetailImg(Long id);
}
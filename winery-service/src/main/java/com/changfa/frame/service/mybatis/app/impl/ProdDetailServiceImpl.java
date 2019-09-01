package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.ProdDetail;
import com.changfa.frame.service.mybatis.app.ProdDetailService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("prodDetailServiceImpl")
public class ProdDetailServiceImpl extends BaseServiceImpl<ProdDetail, Long> implements ProdDetailService {
}

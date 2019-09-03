package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.MbrCart;
import com.changfa.frame.service.mybatis.app.MbrCartService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("mbrCartServiceImpl")
public class MbrCartServiceImpl extends BaseServiceImpl<MbrCart, Long> implements MbrCartService {
}

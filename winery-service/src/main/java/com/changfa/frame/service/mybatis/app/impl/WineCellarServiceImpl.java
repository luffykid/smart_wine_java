package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.WineCellar;
import com.changfa.frame.service.mybatis.app.WineCellarService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("wineCellarServiceImpl")
public class WineCellarServiceImpl extends BaseServiceImpl<WineCellar, Long> implements WineCellarService {
}

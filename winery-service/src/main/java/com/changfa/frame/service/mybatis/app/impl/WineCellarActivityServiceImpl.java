package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.WineCellarActivity;
import com.changfa.frame.service.mybatis.app.WineCellarActivityService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("wineCellarActivityServiceImpl")
public class WineCellarActivityServiceImpl extends BaseServiceImpl<WineCellarActivity, Long> implements WineCellarActivityService {
}

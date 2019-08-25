package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.Area;
import com.changfa.frame.service.mybatis.app.AreaService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {
}

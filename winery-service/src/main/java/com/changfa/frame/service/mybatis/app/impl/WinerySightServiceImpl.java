package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WinerySightMapper;
import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.service.mybatis.app.WinerySightService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class WinerySightServiceImpl extends BaseServiceImpl implements WinerySightService {

    @Autowired
    private WinerySightMapper winerySightMapper;
}

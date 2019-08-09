package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WinerySightMapper;
import com.changfa.frame.model.WinerySight;
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

    @Override
    public int addWinerySight(WinerySight winerySight) {

        return 0;
    }

    @Override
    public List<WinerySight> queryById(Long id) {
        return winerySightMapper.queryById(id);
    }
}

package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.WinerySight;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface WinerySightService extends BaseService {

    int addWinerySight(WinerySight winerySight);

    List<WinerySight> queryById(Long id);
}

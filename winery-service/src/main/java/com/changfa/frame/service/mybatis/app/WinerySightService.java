package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;
import java.util.Map;

public interface WinerySightService extends BaseService<WinerySight, Long> {

    void addWinerySight(WinerySight winerySight, AdminUser curAdmin );

    Map<String, Object> findWinerySight(Integer id);

    void updateWinerySight(WinerySight winerySight);

    void deleteWinerySight(Integer id);

    List<WinerySight> findWinerySightList();
}

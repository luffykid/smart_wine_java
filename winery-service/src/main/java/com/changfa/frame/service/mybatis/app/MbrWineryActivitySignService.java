package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineryActivitySign;
import com.changfa.frame.service.mybatis.common.BaseService;

public interface MbrWineryActivitySignService extends BaseService<MbrWineryActivitySign, Long> {
    /**
     * 活动报名
     *
     * @return
     */
    public boolean singUp(Long wineryActivityId, Long mbrId);

    /**
     * 活动签到
     *
     * @return
     */
    public boolean singIn(Long wineryActivityId, Long mbrId);

    /**
     * 活动签退
     *
     * @return
     */
    public boolean singOff(Long wineryActivityId, Long mbrId);
}

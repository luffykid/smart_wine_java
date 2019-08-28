package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineryActivitySign;
import com.changfa.frame.service.mybatis.common.BaseService;

public interface MbrWineryActivitySignService extends BaseService<MbrWineryActivitySign, Long> {
    /**
     * 活动报名
     *
     * @return
     */
    public boolean signUp(Long wineryActivityId, Long mbrId);

    /**
     * 活动签到
     *
     * @return
     */
    public boolean signIn(Long wineryActivityId, Long mbrId);

    /**
     * 活动签退
     *
     * @return
     */
    public boolean signOff(Long wineryActivityId, Long mbrId);
}

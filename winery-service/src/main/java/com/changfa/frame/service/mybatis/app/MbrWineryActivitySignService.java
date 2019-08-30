package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineryActivitySign;
import com.changfa.frame.service.mybatis.common.BaseService;

public interface MbrWineryActivitySignService extends BaseService<MbrWineryActivitySign, Long> {
    /**
     * 活动报名
     *
     * @return
     */
    public void signUp(Long wineryActivityId, Long mbrId) throws Exception;

    /**
     * 活动签到
     *
     * @return
     */
    public void signIn(Long wineryActivityId, Long mbrId) throws Exception;

    /**
     * 活动签退
     *
     * @return
     */
    public void signOff(Long wineryActivityId, Long mbrId) throws Exception;
}

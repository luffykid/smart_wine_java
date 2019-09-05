package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrSightSign;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.service.mybatis.common.BaseService;

/**
 * 会员景点签到服务层
 */
public interface MbrSightSignService extends BaseService<MbrSightSign, Long> {

    /**
     * 会员签到
     *
     * @param winerySight    景点
     * @param signMember     签到会员
     * @return 是否成功
     */
    Boolean mbrSightSignIn(WinerySight winerySight,Member signMember);
}

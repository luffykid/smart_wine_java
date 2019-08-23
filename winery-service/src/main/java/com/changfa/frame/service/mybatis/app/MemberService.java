package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.common.BaseService;

/**
 * 会员service
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
public interface MemberService extends BaseService<Member,Long> {

    /**
     * 根据手机号查询会员
     *
     * @param phone 手机号
     * @return
     */
    Member selectByPhone(String phone);
}

package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MemberMapper;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MemberService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 会员service实现
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member,Long> implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 根据手机号查询会员
     * @param phone 手机号
     * @return
     */
    @Override
    public Member selectByPhone(String phone) {
        return memberMapper.selectByPhone(phone);
    }
}

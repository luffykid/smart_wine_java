package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MemberAddressMapper;
import com.changfa.frame.model.app.MemberAddress;
import com.changfa.frame.service.mybatis.app.MemberAddressService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberAddressServiceImpl")
public class MemberAddressServiceImpl extends BaseServiceImpl<MemberAddress, Long> implements MemberAddressService {
    @Autowired
    private MemberAddressMapper memberAddressMapper;

    /**
     * 获取我的管理地址列表
     * @param mbrId
     * @return
     */
    @Override
    public List<MemberAddress> getList(Long mbrId) {
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setMbrId(mbrId);
        return memberAddressMapper.selectList(memberAddress);
    }


}

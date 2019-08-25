package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MemberAddress;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface MemberAddressService extends BaseService<MemberAddress, Long> {
    /**
     * 获取我的管理地址列表
     * @param mbrId
     * @return
     */
    public List<MemberAddress> getList(Long mbrId);
}

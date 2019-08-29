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

    /**
     * 添加管理地址
     * @param contact
     * @param phone
     * @param provinceCode
     * @param cityCode
     * @param countryCode
     * @param detailAddress
     * @param isDefault
     */
    public void add(String contact, String phone, String provinceCode, String cityCode,String countryCode,String detailAddress,Boolean isDefault);

    /**
     * 修改管理地址
     * @param contact
     * @param phone
     * @param provinceCode
     * @param cityCode
     * @param countryCode
     * @param detailAddress
     * @param isDefault
     */
    public void modify(Long id, String contact, String phone, String provinceCode, String cityCode,String countryCode,String detailAddress,Boolean isDefault);
}

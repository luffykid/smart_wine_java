package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrAddress;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface MbrAddressService extends BaseService<MbrAddress, Long> {
    /**
     * 获取我的管理地址列表
     * @param mbrId
     * @return
     */
    List<MbrAddress> getList(Long mbrId);

    /**
     * 添加管理地址
     * @param contact
     * @param phone
     * @param provinceCode
     * @param cityCode
     * @param countyCode
     * @param detailAddress
     * @param isDefault
     */
    void add(Long mbrId, Long wineryId,String contact, String phone, String provinceCode, String cityCode,String countyCode,String detailAddress,Boolean isDefault);

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
    void modify(Long id, String contact, String phone, String provinceCode, String cityCode,String countryCode,String detailAddress,Boolean isDefault);
}

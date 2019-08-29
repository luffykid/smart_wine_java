package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.AreaMapper;
import com.changfa.frame.mapper.app.MemberAddressMapper;
import com.changfa.frame.model.app.Area;
import com.changfa.frame.model.app.MemberAddress;
import com.changfa.frame.service.mybatis.app.MemberAddressService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("memberAddressServiceImpl")
public class MemberAddressServiceImpl extends BaseServiceImpl<MemberAddress, Long> implements MemberAddressService {
    @Autowired
    private MemberAddressMapper memberAddressMapper;
    @Autowired
    private AreaMapper areaMapper;
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
    @Override
    public void add(String contact, String phone, String provinceCode, String cityCode, String countryCode, String detailAddress, Boolean isDefault) {
        Area area = new Area();
        area.setCode(provinceCode);
        Area areaOfProvince = areaMapper.selectList(area).get(0);
        area.setCode(cityCode);
        Area areOfCity = areaMapper.selectList(area).get(0);
        area.setCode(countryCode);
        Area areaOfcountry = areaMapper.selectList(area).get(0);
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setId(IDUtil.getId());
        memberAddress.setContact(contact);
        memberAddress.setPhone(phone);
        memberAddress.setProvince(areaOfProvince.getId());
        memberAddress.setCity(areOfCity.getId());
        memberAddress.setCountry(areaOfcountry.getId());
        memberAddress.setDetailAddress(detailAddress);
        memberAddress.setIsDefault(isDefault);
        memberAddress.setFullAddress(areaOfProvince.getName()+areOfCity.getName()+areaOfcountry.getName()+detailAddress);
        memberAddress.setCreateDate(new Date());
        memberAddressMapper.save(memberAddress);
    }
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
    @Override
    public void modify(Long id, String contact, String phone, String provinceCode, String cityCode, String countryCode, String detailAddress, Boolean isDefault) {
        Area area = new Area();
        area.setCode(provinceCode);
        Area areaOfProvince = areaMapper.selectList(area).get(0);
        area.setCode(cityCode);
        Area areOfCity = areaMapper.selectList(area).get(0);
        area.setCode(countryCode);
        Area areaOfcountry = areaMapper.selectList(area).get(0);
        MemberAddress memberAddress = memberAddressMapper.getById(id);
        memberAddress.setContact(contact);
        memberAddress.setPhone(phone);
        memberAddress.setProvince(areaOfProvince.getId());
        memberAddress.setCity(areOfCity.getId());
        memberAddress.setCountry(areaOfcountry.getId());
        memberAddress.setDetailAddress(detailAddress);
        memberAddress.setIsDefault(isDefault);
        memberAddress.setFullAddress(areaOfProvince.getName()+areOfCity.getName()+areaOfcountry.getName()+detailAddress);
        memberAddress.setModifyDate(new Date());
        memberAddressMapper.update(memberAddress);
    }


}

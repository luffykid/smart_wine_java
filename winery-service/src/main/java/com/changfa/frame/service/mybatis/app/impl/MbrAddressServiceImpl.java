package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.AreaMapper;
import com.changfa.frame.mapper.app.MbrAddressMapper;
import com.changfa.frame.model.app.Area;
import com.changfa.frame.model.app.MbrAddress;
import com.changfa.frame.service.mybatis.app.MbrAddressService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("mbrAddressServiceImpl")
public class MbrAddressServiceImpl extends BaseServiceImpl<MbrAddress, Long> implements MbrAddressService {
    @Autowired
    private MbrAddressMapper mbrAddressMapper;
    @Autowired
    private AreaMapper areaMapper;
    /**
     * 获取我的管理地址列表
     * @param mbrId
     * @return
     */
    @Override
    public List<MbrAddress> getList(Long mbrId) {
        MbrAddress mbrAddress = new MbrAddress();
        mbrAddress.setMbrId(mbrId);
        List<MbrAddress> mbrAddressList = mbrAddressMapper.selectList(mbrAddress);
        for(MbrAddress entity :mbrAddressList){
            Area province = areaMapper.getAreaByCode(entity.getProvince());
            if(province!=null){
                entity.setProvinceName(province.getName());
            }
            Area city = areaMapper.getAreaByCode(entity.getCity());
            if(province!=null){
                entity.setProvinceName(city.getName());
            }
            Area country = areaMapper.getAreaByCode(entity.getCounty());
            if(province!=null){
                entity.setProvinceName(country.getName());
            }
        }
        return mbrAddressList;
    }
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
    @Override
    public void add(Long mbrId, Long wineryId, String contact, String phone, String provinceCode, String cityCode, String countyCode, String detailAddress, Boolean isDefault) {
        Area area = new Area();
        area.setCode(provinceCode);
        Area areaOfProvince = areaMapper.selectList(area).get(0);
        area.setCode(cityCode);
        Area areOfCity = areaMapper.selectList(area).get(0);
        area.setCode(countyCode);
        Area areaOfcountry = areaMapper.selectList(area).get(0);
        MbrAddress mbrAddress = new MbrAddress();
        mbrAddress.setId(IDUtil.getId());
        mbrAddress.setMbrId(mbrId);
        mbrAddress.setWineryId(wineryId);
        mbrAddress.setContact(contact);
        mbrAddress.setPhone(phone);
        mbrAddress.setProvince(areaOfProvince.getId());
        mbrAddress.setCity(areOfCity.getId());
        mbrAddress.setCounty(areaOfcountry.getId());
        mbrAddress.setDetailAddress(detailAddress);
        mbrAddress.setIsDefault(isDefault);
        mbrAddress.setFullAddress(areaOfProvince.getName()+areOfCity.getName()+areaOfcountry.getName()+detailAddress);
        mbrAddress.setCreateDate(new Date());
        mbrAddressMapper.save(mbrAddress);
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
        MbrAddress mbrAddress = mbrAddressMapper.getById(id);
        mbrAddress.setContact(contact);
        mbrAddress.setPhone(phone);
        mbrAddress.setProvince(areaOfProvince.getId());
        mbrAddress.setCity(areOfCity.getId());
        mbrAddress.setCounty(areaOfcountry.getId());
        mbrAddress.setDetailAddress(detailAddress);
        mbrAddress.setIsDefault(isDefault);
        mbrAddress.setFullAddress(areaOfProvince.getName()+areOfCity.getName()+areaOfcountry.getName()+detailAddress);
        mbrAddress.setModifyDate(new Date());
        mbrAddressMapper.update(mbrAddress);
    }


}

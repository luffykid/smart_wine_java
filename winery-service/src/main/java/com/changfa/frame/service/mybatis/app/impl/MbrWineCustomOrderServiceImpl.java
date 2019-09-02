package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.MbrAddress;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("mbrWineCustomOrderServiceImpl")
public class MbrWineCustomOrderServiceImpl extends BaseServiceImpl<MbrWineCustomOrder, Long> implements MbrWineCustomOrderService {

    @Autowired
    private WineCustomMapper wineCustomMapper;

    @Autowired
    private MbrWineCustomOrderRecordMapper mbrWineCustomOrderRecordMapper;

    @Autowired
    private MbrWineCustomOrderMapper mbrWineCustomOrderMapper;

    @Autowired
    private MbrWineCustomMapper mbrWineCustomMapper;

    @Autowired
    private MbrWineCustomDetailMapper mbrWineCustomDetailMapper;

    @Autowired
    private WineCustomElementContentMapper wineCustomElementContentMapper;

    @Autowired
    private MbrAddressMapper mbrAddressMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 保存会员定制信息
     *
     * @param mbrWineCustomDetails 会员白酒定制详情集合
     * @param member               会员
     * @return
     */
    @Override
    public boolean saveMbrCustomInfo(List<MbrWineCustomDetail> mbrWineCustomDetails, Member member) {

        return false;
    }

    @Transactional
    @Override
    public void addShipInfoForTheOrder(Long mbrId, Long memberAddressId, Long mbrWineCustomOrderId) {

        Member member = memberMapper.getById(mbrId);

        MbrAddress address = mbrAddressMapper.getById(memberAddressId);

        MbrWineCustomOrder order = mbrWineCustomOrderMapper.getById(mbrWineCustomOrderId);

        checkValidate(order, member, address);

        addShipInfoForTheOrder(order, address);

        mbrWineCustomOrderMapper.update(order);

    }

    private void addShipInfoForTheOrder(MbrWineCustomOrder order, MbrAddress address) {
        order.setShippingDetailAddr(address.getDetailAddress());
        order.setShippingProvinceId(address.getProvince());
        order.setShippingCityId(address.getCity());
        order.setShippingCountyId(address.getCountry());
        order.setShippingPersonName(address.getContact());
        order.setShippingPersonPhone(address.getPhone());
        order.setModifyDate(new Date());

    }

    private void checkValidate(MbrWineCustomOrder order, Member member, MbrAddress address) {

        if (order == null)
            throw new IllegalArgumentException("the mbrWineCustomOrderId don't exsit!");

        if (member == null)
            throw new IllegalArgumentException("the member don't exsit!");

        if (address == null)
            throw new IllegalArgumentException("the address don't exsit!");

        if (!address.getMbrId().equals(member.getId()))
            throw new IllegalArgumentException("the address don't belong to the member!");

        if (order.getOrderStatus() != MbrWineCustomOrder.ORDER_STATUS_ENUM.UNPAID.getValue())
            throw new IllegalStateException("the operation don't allowed for the current mbrWineCustomOrder state!");

    }
}

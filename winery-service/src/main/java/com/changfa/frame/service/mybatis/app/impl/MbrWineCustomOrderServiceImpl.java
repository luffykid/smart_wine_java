package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("mbrWineCustomOrderServiceImpl")
public class MbrWineCustomOrderServiceImpl extends BaseServiceImpl<MbrWineCustomOrder, Long>
        implements MbrWineCustomOrderService {

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
    private MemberAddressMapper memberAddressMapper;

    @Autowired
    private MemberMapper memberMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public MbrWineCustomOrder placeAnOrder(Long wineryId,
                                           Long mbrId,
                                           Long wineCustomId,
                                           Integer quantity,
                                           List<MbrWineCustomDetail> details) {

        checkValidate(wineryId, mbrId, wineCustomId, quantity, details);

        WineCustom wineCustom = wineCustomMapper.getById(wineCustomId);

        MbrWineCustom mbrWineCustom = constructMbrWineCustom(wineryId, wineCustom, quantity, mbrId);


        // 创建定制酒订单
        MbrWineCustomOrder order = MbrWineCustomOrder.createOrder(wineryId,
                                                                  mbrWineCustom,
                                                                  IDUtil.getId(),
                                                                  OrderNoUtil.get());

        addMbrWineCustomOrderRecordToRepository(order);

        mbrWineCustom.setMbrWineCustomOrderId(order.getId());


        //将details中的每个MbrWineCustomDetail对象填充完全
        completeMbrWineCustomDetails(details, mbrWineCustom.getId(), mbrWineCustom.getMbrId());

        saveToRepository(order, mbrWineCustom, details);

        return order;
    }

    @Transactional
    @Override
    public void addShipInfoForTheOrder(Long mbrId, Long memberAddressId, Long mbrWineCustomOrderId) {

        Member member = memberMapper.getById(mbrId);

        MemberAddress address = memberAddressMapper.getById(memberAddressId);

        MbrWineCustomOrder order = mbrWineCustomOrderMapper.getById(mbrWineCustomOrderId);

        checkValidate(order, member, address);

        addShipInfoForTheOrder(order, address);

        mbrWineCustomOrderMapper.update(order);

    }

    private void addMbrWineCustomOrderRecordToRepository(MbrWineCustomOrder order) {
        mbrWineCustomOrderMapper.update(order);

        MbrWineCustomOrderRecord record = new MbrWineCustomOrderRecord();
        record.setId(IDUtil.getId());
        record.setMbrWineCustomOrderId(order.getId());
        record.setOrderStatus(Long.valueOf(order.getOrderStatus()));
        record.setStatusDate(new Date());
        record.setCreateDate(new Date());
        record.setModifyDate(new Date());

        mbrWineCustomOrderRecordMapper.save(record);
    }

    private void addShipInfoForTheOrder(MbrWineCustomOrder order, MemberAddress address) {

        order.setShippingDetailAddr(address.getDetailAddress());
        order.setShippingProvinceId(address.getProvince());
        order.setShippingCityId(address.getCity());
        order.setShippingCountyId(address.getCountry());
        order.setShippingPersonName(address.getContact());
        order.setShippingPersonPhone(address.getPhone());
        order.setModifyDate(new Date());

    }

    private void checkValidate(MbrWineCustomOrder order, Member member, MemberAddress address) {

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

    private void saveToRepository(MbrWineCustomOrder order,
                                  MbrWineCustom mbrWineCustom,
                                  List<MbrWineCustomDetail> details) {

        mbrWineCustomOrderMapper.save(order);

        mbrWineCustomMapper.save(mbrWineCustom);

        details.stream().forEach(mbrWineCustomDetailMapper::save);

    }

    private MbrWineCustom constructMbrWineCustom(Long wineryId, WineCustom wineCustom, Integer quantity, Long mbrId) {

        MbrWineCustom mbrWineCustom = new MbrWineCustom();
        mbrWineCustom.setCreateDate(new Date());
        mbrWineCustom.setModifyDate(new Date());
        mbrWineCustom.setWineryId(wineryId);
        mbrWineCustom.setCustomName(wineCustom.getCustomName());
        mbrWineCustom.setWineryId(wineCustom.getWineryId());
        mbrWineCustom.setSkuName(wineCustom.getSkuName());
        mbrWineCustom.setCustomPrice(wineCustom.getCustomPrice());
        mbrWineCustom.setCustomCnt(quantity);
        mbrWineCustom.setCustomTotalAmt(wineCustom.getCustomPrice()
                .multiply(BigDecimal.valueOf(quantity)));
        mbrWineCustom.setMbrId(mbrId);
        mbrWineCustom.setWineCustomId(wineCustom.getId());
        mbrWineCustom.setId(IDUtil.getId());

        return mbrWineCustom;

    }

    private void checkValidate(Long wineryId, Long mbrId, Long wineCustomId, Integer quantity, List<MbrWineCustomDetail> details) {

        if (wineryId == null)
            throw new NullPointerException("wineryId must not be null!");

        if (mbrId == null)
            throw new NullPointerException("mbrId must not be null!");

        if (wineCustomId == null)
            throw new NullPointerException("wineCustomId must not be null!");

        if (quantity == null)
            throw new NullPointerException("quantity must not be null");

        if (quantity <= 0)
            throw new NullPointerException("quantity must more than 0!");

        if (details == null)
            throw new NullPointerException("details must not be null!");

        if (details.size() == 0)
            throw new IllegalArgumentException("quantity of details must be more than 0");

    }

    private void completeMbrWineCustomDetails(List<MbrWineCustomDetail> details,
                                              Long mbrWineCustomId,
                                              Long mbrId) {

        details.stream().forEach(detail -> {

            WineCustomElementContent wineCustomElementContent =
                    wineCustomElementContentMapper.getById(detail.getWineCustomElementContentId());

            detail.setId(IDUtil.getId());
            detail.setWineCustomId(wineCustomElementContent.getWineCustomId());
            detail.setWineCustomElementId(wineCustomElementContent.getWineCustomElementId());
            detail.setMbrWineCustomId(mbrWineCustomId);
            detail.setMbrId(mbrId);
            detail.setBgImg(wineCustomElementContent.getBgImg());
            detail.setMaskImg(wineCustomElementContent.getMaskImg());
            detail.setBottomImg(wineCustomElementContent.getBottomImg());
            detail.setCreateDate(new Date());
            detail.setModifyDate(new Date());

        });

    }


}

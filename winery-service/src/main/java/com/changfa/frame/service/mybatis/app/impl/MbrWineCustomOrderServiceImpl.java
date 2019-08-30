package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.model.event.DomainEventPublisher;
import com.changfa.frame.model.event.DomainEventSubscriber;
import com.changfa.frame.model.event.order.OrderCreatedEvent;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MbrWineCustomOrder PlaceAnOrder(Long wineryId,
                                           Long mbrId,
                                           Long wineCustomId,
                                           Integer quantity,
                                           List<MbrWineCustomDetail> details) {

        checkValidate(wineryId, mbrId, wineCustomId, quantity, details);

        WineCustom wineCustom = wineCustomMapper.getById(wineCustomId);

        MbrWineCustom mbrWineCustom = constructMbrWineCustom(wineryId, wineCustom, quantity, mbrId);


        //订阅新建定制酒订单事件
        DomainEventPublisher publisher = DomainEventPublisher.newInstance();
        publisher.subcribe(new DomainEventSubscriber<OrderCreatedEvent>() {

            @Override
            public void handleEvent(OrderCreatedEvent orderCreatedEvent) {

                MbrWineCustomOrderRecord record = new MbrWineCustomOrderRecord();
                record.setId(IDUtil.getId());
                record.setMbrWineCustomOrderId(orderCreatedEvent.getMbrWineCustomOrderId());
                record.setOrderStatus(Long.valueOf(MbrWineCustomOrder.Status.NEW_ORDER.getValue()));
                record.setStatusDate(orderCreatedEvent.occurredOn());
                record.setCreateDate(new Date());
                record.setModifyDate(new Date());

                mbrWineCustomOrderRecordMapper.save(record);

            }

            @Override
            public Class<OrderCreatedEvent> subscribedToEventType() {
                return OrderCreatedEvent.class;
            }
        });

        // 创建定制酒订单
        MbrWineCustomOrder order = MbrWineCustomOrder.createOrder(publisher,
                                                                  wineryId,
                                                                  mbrWineCustom,
                                                                  IDUtil.getId(),
                                                                  OrderNoUtil.get());

        mbrWineCustom.setMbrWineCustomOrderId(order.getId());

        //将details中的每个MbrWineCustomDetail对象填充完全
        completeMbrWineCustomDetails(details, mbrWineCustom.getId(), mbrWineCustom.getMbrId());

        SaveToRepository(order, mbrWineCustom, details);

        return order;
    }

    private void SaveToRepository(MbrWineCustomOrder order,
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

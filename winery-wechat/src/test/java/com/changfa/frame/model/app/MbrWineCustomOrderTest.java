package com.changfa.frame.model.app;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.model.event.DomainEventPublisher;
import com.changfa.frame.model.event.order.OrderCreatedEvent;
import com.changfa.frame.service.mybatis.common.IDUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;

public class MbrWineCustomOrderTest {


    @Test
    public void testPlaceAnWineCustomOrder() {

        DomainEventPublisher publisher = Mockito.mock(DomainEventPublisher.class);


        MbrWineCustom mbrWineCustom = getFixtureMbrWineCustom();

        MbrWineCustomOrder order = MbrWineCustomOrder.createOrder(publisher,
                                                                  mbrWineCustom,
                                                                  IDUtil.getId(),
                                                                  OrderNoUtil.get());

        Assert.assertNotNull(order);
        Assert.assertNotNull(order.getId());
        Assert.assertNotNull(order.getOrderNo());
        Assert.assertNotNull(order.getCreateDate());
        Assert.assertNotNull(order.getModifyDate());
        Assert.assertNull(order.getShippingCityId());
        Assert.assertNull(order.getShippingProvinceId());
        Assert.assertNull(order.getShippingProvinceId());
        Assert.assertNull(order.getShippingPersonName());
        Assert.assertNull(order.getShippingPersonPhone());
        Assert.assertNull(order.getShippingDetailAddr());
        Assert.assertEquals(order.getPayRealAmt(), order.getPayTotalAmt());
        Assert.assertEquals(mbrWineCustom.getCustomTotalAmt(), order.getPayTotalAmt());
        Assert.assertEquals(mbrWineCustom.getMbrId(), order.getMbrId());
        Assert.assertEquals(0, order.getOrderStatus().intValue());



    }

    @Test
    public void testPublishOrderCreatedEventAfterPlaceAnOrder() {

        DomainEventPublisher publisher = Mockito.mock(DomainEventPublisher.class);


        MbrWineCustom mbrWineCustom = getFixtureMbrWineCustom();

       MbrWineCustomOrder.createOrder(publisher,
                                      mbrWineCustom,
                                      IDUtil.getId(),
                                      OrderNoUtil.get());

        Mockito.verify(publisher).publish(Mockito.any(OrderCreatedEvent.class));

    }

    private MbrWineCustom getFixtureMbrWineCustom() {

        MbrWineCustom mbrWineCustom = new MbrWineCustom();
        mbrWineCustom.setWineCustomId(Long.valueOf(1234));
        mbrWineCustom.setWineryId(Long.valueOf(1234));
        mbrWineCustom.setMbrId(Long.valueOf(1234));
        mbrWineCustom.setCustomCnt(3);
        mbrWineCustom.setCustomPrice(BigDecimal.valueOf(100));
        mbrWineCustom.setCustomTotalAmt(BigDecimal.valueOf(300));
        mbrWineCustom.setCustomName("fixture_name");
        mbrWineCustom.setSkuName("fixture_sku_name");
        mbrWineCustom.setCreateDate(new Date());
        mbrWineCustom.setModifyDate(new Date());

        return mbrWineCustom;
    }

}
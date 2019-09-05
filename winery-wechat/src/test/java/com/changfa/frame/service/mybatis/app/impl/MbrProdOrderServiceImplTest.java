package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrProdOrderItemMapper;
import com.changfa.frame.mapper.app.MbrProdOrderRecordMapper;
import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.model.app.MbrProdOrderItem;
import com.changfa.frame.model.app.MbrProdOrderRecord;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.service.mybatis.app.MbrProdOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MbrProdOrderServiceImplTest {

    @Autowired
    private MbrProdOrderService mbrProdOrderService;

    @Autowired
    private MbrProdOrderRecordMapper mbrProdOrderRecordMapper;

    @Autowired
    private MbrProdOrderItemMapper mbrProdOrderItemMapper;

    @Test
    public void testPlaceAnOrder() {

        Long wineryId = Long.valueOf(1);
        Long mbrId = Long.valueOf(1);
        List<MbrProdOrderItem> fixtureItems = getFixtureOrderItem();

        MbrProdOrder order = mbrProdOrderService.placeAnOrder(wineryId, mbrId, fixtureItems);
        BigDecimal payAmount = fixtureItems
                                .stream()
                                .map(item -> item.getSkuSellPrice()
                                             .multiply(BigDecimal.valueOf(item.getProdSkuCnt())))
                                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        Integer prodTotalCnt = fixtureItems.stream().collect(Collectors.reducing(0,
                                                                MbrProdOrderItem::getProdSkuCnt,
                                                                Integer::sum));

        Assert.assertNotNull(order);
        Assert.assertNotNull(order.getId());
        Assert.assertNotNull(order.getOrderNo());
        Assert.assertEquals(wineryId, order.getWineryId());
        Assert.assertEquals(mbrId, order.getMbrId());
        Assert.assertEquals("订单商品总数错误", prodTotalCnt, order.getProdTotalCnt());
        Assert.assertEquals("订单状态错误", MbrProdOrder.ORDER_STATUS_ENUM.PAY_NOT.getValue(),
                            order.getOrderStatus());
        Assert.assertEquals("订单支付总额错误", payAmount, order.getPayTotalAmt());
        Assert.assertNotNull(order.getCreateDate());
        Assert.assertNotNull(order.getModifyDate());
        Assert.assertEquals(fixtureItems.get(0).getIsIntegral(), order.getIsIntegral());
        BigDecimal totalIntegralCnt = fixtureItems.stream().collect(Collectors.reducing(BigDecimal.ZERO,
                MbrProdOrderItem::getIntegralCnt,
                BigDecimal::add
        ));

        BigDecimal totalIntegralAmt = fixtureItems.stream().collect(Collectors.reducing(BigDecimal.ZERO,
                MbrProdOrderItem::getIntegralAmt,
                BigDecimal::add));

        Assert.assertEquals(totalIntegralAmt, order.getPayRealAmt());
        Assert.assertEquals(totalIntegralCnt, order.getPayIntegralCnt());


        MbrProdOrder orderSaved = mbrProdOrderService.getById(order.getId());

        Assert.assertNotNull(orderSaved);
//        Assert.assertNotNull(orderSaved.getMbrProdOrderItems());

        List<MbrProdOrderRecord> records = mbrProdOrderRecordMapper.getByOrderId(order.getId());

        Assert.assertEquals("新增订单记录一条", 1, records.size());



    }

    @Test
    public void testAddMbrAddressInfoAndChoosePayMode() {

        Long mbrAddressId = Long.valueOf(447316456124710912L);
        Integer payMode = MbrProdOrder.PAY_MODE_ENUM.WX_MINI_INTEGRAL_MODE.getValue();
        Long orderId = Long.valueOf(448413559320215552L);

        mbrProdOrderService.addMbrAddressInfoAndChoosePayMode(orderId, mbrAddressId, payMode);

        MbrProdOrder order = mbrProdOrderService.getById(orderId);

        Assert.assertNotNull(order);
        Assert.assertEquals(payMode, order.getPayMode());
        Assert.assertNotNull(order.getShippingDetailAddr());
        Assert.assertNotNull(order.getShippingProvinceId());
        Assert.assertNotNull(order.getShippingCityId());
        Assert.assertNotNull(order.getShippingCountyId());
        Assert.assertNotNull(order.getShippingPersonName());
        Assert.assertNotNull(order.getShippingPersonPhone());

    }

    @Test
    public void testPayWithWxMiniIntegralMode() {

        Long mbrAddressId = Long.valueOf(447316456124710912L);
        Integer payMode = MbrProdOrder.PAY_MODE_ENUM.WX_MINI_INTEGRAL_MODE.getValue();
        Long orderId = Long.valueOf(448413559320215552L);

        mbrProdOrderService.addMbrAddressInfoAndChoosePayMode(orderId, mbrAddressId, payMode);

        MbrProdOrder order = mbrProdOrderService.getById(orderId);

        List<MbrProdOrderItem> items = mbrProdOrderItemMapper.getByMbrProdOrderId(orderId);

        BigDecimal totalIntegralCnt = items.stream().collect(Collectors.reducing(BigDecimal.ZERO,
                MbrProdOrderItem::getIntegralCnt,
                BigDecimal::add
        ));

        BigDecimal totalIntegralAmt = items.stream().collect(Collectors.reducing(BigDecimal.ZERO,
                MbrProdOrderItem::getIntegralAmt,
                BigDecimal::add));

        Assert.assertEquals(totalIntegralAmt, order.getPayRealAmt());
        Assert.assertEquals(totalIntegralCnt, order.getPayIntegralCnt());

    }

    @Test
    public void testPayWithoutWxMiniIntegralMode() {

        Long mbrAddressId = Long.valueOf(447316456124710912L);
        Integer payMode = MbrProdOrder.PAY_MODE_ENUM.WX_MINI_MODE.getValue();
        Long orderId = Long.valueOf(448379339088592896L);

        mbrProdOrderService.addMbrAddressInfoAndChoosePayMode(orderId, mbrAddressId, payMode);

        MbrProdOrder order = mbrProdOrderService.getById(orderId);


        Assert.assertEquals(order.getPayTotalAmt(), order.getPayRealAmt());
        Assert.assertEquals(BigDecimal.ZERO, order.getPayIntegralCnt());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlaceAnOrderFailureWithOrderItemsWhichDifferentPayMode() {

        Long wineryId = Long.valueOf(1);
        Long mbrId = Long.valueOf(1);
        List<MbrProdOrderItem> differentPayModeItems = getFixtureOrderItemsWhichDifferentPayMode();

        try {

            mbrProdOrderService.placeAnOrder(wineryId, mbrId, differentPayModeItems);

        } catch (IllegalArgumentException e) {

            Assert.assertEquals("the order items with different pay mode!",
                                e.getMessage());
            throw e;

        }




    }

    private List<MbrProdOrderItem> getFixtureOrderItemsWhichDifferentPayMode() {

        MbrProdOrderItem item = new MbrProdOrderItem();
        item.setProdSkuCnt(2);
        item.setProdSkuId(Long.valueOf(445525426773688320L));

        MbrProdOrderItem item2 = new MbrProdOrderItem();
        item2.setProdSkuCnt(2);
        item2.setProdSkuId(Long.valueOf(445544192899284992L));

        return Arrays.asList(item, item2);

    }

    private List<MbrProdOrderItem> getFixtureOrderItem() {

        MbrProdOrderItem item = new MbrProdOrderItem();
        item.setProdSkuCnt(2);
        item.setProdSkuId(Long.valueOf(445525426773688320L));

        return Arrays.asList(item);

    }

}
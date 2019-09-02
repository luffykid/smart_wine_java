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


        MbrProdOrder orderSaved = mbrProdOrderService.getById(order.getId());

        Assert.assertNotNull(orderSaved);
//        Assert.assertNotNull(orderSaved.getMbrProdOrderItems());

        List<MbrProdOrderRecord> records = mbrProdOrderRecordMapper.getByOrderId(order.getId());

        Assert.assertEquals("新增订单记录一条", 1, records.size());



    }

    private List<MbrProdOrderItem> getFixtureOrderItem() {

        MbrProdOrderItem item = new MbrProdOrderItem();
        item.setProdSkuCnt(2);
        item.setProdSkuId(Long.valueOf(445525426773688320L));

        return Arrays.asList(item);

    }

}
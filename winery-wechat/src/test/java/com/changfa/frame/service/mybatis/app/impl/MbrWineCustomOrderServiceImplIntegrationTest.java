package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrWineCustomOrderMapper;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MbrWineCustomOrderServiceImplIntegrationTest {

    @Autowired
    private MbrWineCustomOrderService mbrWineCustomOrderService;

    @Autowired
    private MbrWineCustomOrderMapper mbrWineCustomOrderMapper;


    @Test
    public void testPlaceAnOrder() {

        Long mbrId = Long.valueOf(1234);
        Long wineCustomId = Long.valueOf(446283389016735745L);
        Integer quantity = 4;
        Long wineryId = Long.valueOf(9999);
        List<MbrWineCustomDetail> fixtureMbrWineCustomDetails = getFixtureMbrWineCustomDetails();

//        MbrWineCustomOrder order = mbrWineCustomOrderService.placeAnOrder(wineryId, mbrId, wineCustomId, quantity, fixtureMbrWineCustomDetails);

//        Assert.assertNotNull(order);

//        MbrWineCustomOrder orderSaved = mbrWineCustomOrderMapper.getById(order.getId());

//        Assert.assertNotNull(orderSaved);

    }

    @Test
    public void testAddShipInfoForTheOrder() {

        Long mbrWineCustomOrderId = Long.valueOf(447185632331038720L);
//
//        mbrWineCustomOrderService.addShipInfoForTheOrder(Long.valueOf(1), Long.valueOf(446980587345936384L),
//                                                                                mbrWineCustomOrderId);


        MbrWineCustomOrder orderUpdated = mbrWineCustomOrderMapper.getById(mbrWineCustomOrderId);

        Assert.assertNotNull(orderUpdated);
        Assert.assertNotNull(orderUpdated.getShippingDetailAddr());

    }

    private List<MbrWineCustomDetail> getFixtureMbrWineCustomDetails() {

        List<MbrWineCustomDetail> mbrWineCustomDetails = new ArrayList<>();

        MbrWineCustomDetail detail = new MbrWineCustomDetail();
        detail.setWineCustomElementContentId(Long.valueOf(1234));
        detail.setPreviewImg("http://test.file.jiuqixing.cn/card_bg.png");

        mbrWineCustomDetails.add(detail);

        return mbrWineCustomDetails;

    }
}

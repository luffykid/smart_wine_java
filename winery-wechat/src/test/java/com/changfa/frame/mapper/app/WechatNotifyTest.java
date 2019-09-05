package com.changfa.frame.mapper.app;

import com.alibaba.fastjson.JSONObject;
import com.changfa.frame.model.app.MbrWineCustom;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.service.mybatis.app.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wyy
 * @date 2019-09-01 19:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WechatNotifyTest {

    @Resource(name = "mbrProdOrderServiceImpl")
    private MbrProdOrderService mbrProdOrderService;

    @Resource(name = "mbrRechargeOrderServiceImpl")
    private MbrRechargeOrderService mbrRechargeOrderService;

    @Resource(name = "mbrAdjustOrderServiceImpl")
    private MbrAdjustOrderService mbrAdjustOrderService;

    @Resource(name = "mbrStoreOrderServiceImpl")
    private MbrStoreOrderService mbrStoreOrderService;

    @Resource(name = "mbrWineCustomOrderServiceImpl")
    private MbrWineCustomOrderService mbrWineCustomOrderService;


    @Test
    public void test() {

        mbrProdOrderService.handleNotifyOfProdOrder("1", "1", new Date());

        mbrRechargeOrderService.handleNotifyOfRechargeOrder("1", "1", new Date());

        mbrAdjustOrderService.handleNotifyOfAdjustOrder("1", "1", new Date());

        mbrStoreOrderService.handleNotifyOfStoreOrder("1", "1", new Date());

        mbrWineCustomOrderService.handleNotifyOfWineCustomOrder("1", "1", new Date());
    }

    @Test
    public void testCustomInfo() {
// 保存定制信息接口参数
        MbrWineCustom mbrWineCustom = new MbrWineCustom();
        List<MbrWineCustomDetail> MbrWineCustomDetails = new ArrayList<>();
        MbrWineCustomDetail mbrWineCustomDetail = new MbrWineCustomDetail();
        mbrWineCustomDetail.setWineCustomElementId(333L);
        mbrWineCustomDetail.setBottomImg("http://test.file.jiuqixing.cn/card_bg.png");
        mbrWineCustomDetail.setBgImg("http://test.file.jiuqixing.cn/card_bg.png");
        mbrWineCustomDetail.setPreviewImg("http://test.file.jiuqixing.cn/card_bg.png");
        mbrWineCustomDetail.setMaskImg("http://test.file.jiuqixing.cn/card_bg.png");
        MbrWineCustomDetails.add(mbrWineCustomDetail);

        mbrWineCustom.setMbrWineCustomDetails(MbrWineCustomDetails);
        mbrWineCustom.setWineCustomId(2L);
        System.out.println(JSONObject.toJSON(mbrWineCustom));

    }

}

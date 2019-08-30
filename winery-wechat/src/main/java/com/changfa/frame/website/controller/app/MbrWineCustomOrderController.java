package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.MbrWineCustom;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(value = "定制酒订单接口", tags = "定制酒订单接口")
@RestController("mbrWineCustomOrderController")
@RequestMapping("/wxMini/MbrWineCustomOrder/")
public class MbrWineCustomOrderController extends BaseController {

    @Resource(name = "mbrWineCustomOrderServiceImpl")
    private MbrWineCustomOrderService mbrWineCustomOrderServiceImpl;

    @ApiOperation(value = "用户定制酒下单")
    @PostMapping
    public Map<String, Object> placeAnOrder(@RequestBody MbrWineCustom mbrWineCustom) {

        MbrWineCustomOrder order =
            mbrWineCustomOrderServiceImpl.PlaceAnOrder(mbrWineCustom.getWineryId(),
                    mbrWineCustom.getMbrId(),
                    mbrWineCustom.getWineCustomId(),
                    mbrWineCustom.getCustomCnt(),
                    mbrWineCustom.getMbrWineCustomDetails());

        return getResult(order);

    }

}

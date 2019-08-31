package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.model.app.MbrWineCustom;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "定制酒订单接口", tags = "定制酒订单接口")
@RestController("mbrWineCustomOrderController")
@RequestMapping("/wxMini/auth/MbrWineCustomOrder/")
public class MbrWineCustomOrderController extends BaseController {

    @Resource(name = "mbrWineCustomOrderServiceImpl")
    private MbrWineCustomOrderService mbrWineCustomOrderServiceImpl;

    @ApiOperation(value = "用户定制酒下单")
    @ApiImplicitParam(required = true, paramType = "MbrWineCustom")
    @PostMapping
    public Map<String, Object> placeAnOrder(@RequestBody MbrWineCustom mbrWineCustom,
                                            HttpServletRequest request) {


        Member member = getCurMember(request);

        MbrWineCustomOrder order =
            mbrWineCustomOrderServiceImpl.placeAnOrder(mbrWineCustom.getWineryId(),
                    member.getId(),
                    mbrWineCustom.getWineCustomId(),
                    mbrWineCustom.getCustomCnt(),
                    mbrWineCustom.getMbrWineCustomDetails());

        return getResult(order);

    }

    @PutMapping("{id}/member/{mbrId}/memberAddress/{memberAddressId}")
    public Map<String, Object> payForOrder(@PathVariable("id") Long mbrWineCustomOrderId,
                                           @PathVariable("memberAddressId") Long memberAddressId,
                                           HttpServletRequest request) {

        Member member = getCurMember(request);

        mbrWineCustomOrderServiceImpl.payForOrder(member.getId(),
                                                                        memberAddressId, mbrWineCustomOrderId);

       /* Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(order.getOrderNo(),
                                                                            order.getPayRealAmt(),
                                                                            order.getOpenId(),
                                                                            "",
                                                                            "定制酒订单",
                                                                            request);

        order.setUnifiedOrderReturnMap(returnMap);*/

        return getResult(new HashMap<>());

    }

}

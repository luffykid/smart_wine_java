package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.model.app.MbrProdOrderItem;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrProdOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.ptg.MemErrPtg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员商品订单接口
 *
 */
@Api(value = "会员商品订单接口", tags = "会员商品订单接口")
@RestController("wxMbrProdOrderControllerr")
@RequestMapping("/wxMini/auth/mbrProdOrder")
public class MbrProdOrderController extends BaseController {

    @Resource(name = "mbrProdOrderServiceImpl")
    private MbrProdOrderService mbrProdOrderServiceImpl;

    /**
     * 获取我的订单分类信息
     *
     * @return
     */
    @ApiOperation(value = "获取我的订单分类信息", notes = "获取我的订单分类信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "status", value = "订单状态", dataType = "Integer"))
    @RequestMapping(value = "/getListByStatus", method = RequestMethod.GET)
    public Map<String, Object> getListByTypes(Integer status ,HttpServletRequest request) {
        Member member = getCurMember(request);

        List<MbrProdOrder> orderList = mbrProdOrderServiceImpl.getListByStatus(member.getId(),status);

        return getResult(orderList);
    }

    /**
     * 直接下单
     */
    @ApiOperation(value = "酒庄酒直接下单", notes = "酒庄酒直接下单")
    @ApiImplicitParam(name = "items", value = "订单项列表", dataType = "List")
    @PostMapping("/placeAnOrder")
    public Map<String, Object> placeAnOrder(@RequestBody List<MbrProdOrderItem> items,
                                            HttpServletRequest request) {

        Member member = getCurMember(request);
        Long wineryId = getCurWineryId();
        return getResult(mbrProdOrderServiceImpl.placeAnOrder(wineryId, member.getId(), items));

    }

    /**
     * 支付订单
     */
    @ApiOperation(value = "酒庄酒订单支付", notes = "酒庄酒订单支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbrProdOrderId", value = "酒庄酒订单id", dataType = "Long"),
            @ApiImplicitParam(name = "mbrAddressId", value = "会员地址id", dataType = "Integer"),
            @ApiImplicitParam(name = "payMode", value = "支付模式", dataType = "Integer")
    })
    @PostMapping("/pay")
    public Map<String, Object> pay(Long mbrProdOrderId,
                                   Long mbrAddressId,
                                   Integer payMode,
                                   HttpServletRequest request) {

        checkValidate(payMode);

        MbrProdOrder order = mbrProdOrderServiceImpl.addMbrAddressInfoAndChoosePayMode(mbrProdOrderId,
                                                                                        mbrAddressId,
                                                                                        payMode);


        Member member = getCurMember(request);
        checkMemberIntegralMoreThanTheOrderShouldBePay(member, order);

        /*Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(order.getOrderNo(),
                                                                            order.getPayRealAmt(),
                                                                            member.getOpenId(),
                                                                            "",
                                                                            "酒庄酒订单",
                                                                            request);*/

        return getResult(order);
    }

    private void checkMemberIntegralMoreThanTheOrderShouldBePay(Member member, MbrProdOrder order) {

        if(member.getTotalIntegral().compareTo(order.getPayIntegralCnt()) < 0)
            throw new IllegalStateException("integral of the member don't enough!");

    }

    private void checkValidate(Integer payMode) {

        if (payMode != MbrProdOrder.PAY_MODE_ENUM.WX_MINI_INTEGRAL_MODE.getValue()
            && payMode != MbrProdOrder.PAY_MODE_ENUM.INTEGRAL_MODE.getValue()
            && payMode != MbrProdOrder.PAY_MODE_ENUM.WX_MINI_MODE.getValue())
            throw new IllegalArgumentException("the pay mode don't support!");

    }


}

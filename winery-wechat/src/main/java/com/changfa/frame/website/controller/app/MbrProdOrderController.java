package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.model.app.MbrProdOrderItem;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrProdOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.MemberIntegralNotEnough;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会员商品订单接口
 */
@Api(value = "会员商品订单接口", tags = "会员商品订单接口")
@RestController("wxMiniMbrProdOrderController")
@RequestMapping("/wxMini/auth/mbrProdOrder")
public class MbrProdOrderController extends BaseController {

    @Resource(name = "mbrProdOrderServiceImpl")
    private MbrProdOrderService mbrProdOrderService;

    /**
     * 获取我的订单分类信息
     *
     * @return
     */
    @ApiOperation(value = "获取我的订单分类信息", notes = "获取我的订单分类信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "status", value = "订单状态", dataType = "Integer"))
    @RequestMapping(value = "/getListByStatus", method = RequestMethod.GET)
    public Map<String, Object> getListByTypes(Integer status, HttpServletRequest request) {
        Member member = getCurMember(request);

        List<MbrProdOrder> orderList = null;
        if (status == null || "".equals(status)) {
            orderList = mbrProdOrderService.getListByMbrId(member.getId());
        } else {
            orderList = mbrProdOrderService.getListByStatus(member.getId(), status);
        }

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
        return getResult(mbrProdOrderService.placeAnOrder(wineryId, member.getId(), items));

    }

    /**
     * 支付订单
     */
    @ApiOperation(value = "酒庄酒订单支付", notes = "酒庄酒订单支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbrProdOrderId", value = "酒庄酒订单id", dataType = "Long"),
            @ApiImplicitParam(name = "mbrAddressId", value = "会员地址id", dataType = "Long"),
            @ApiImplicitParam(name = "payMode", value = "支付模式", dataType = "Integer")
    })
    @PostMapping("/pay")
    public Map<String, Object> pay(@RequestParam("mbrProdOrderId") Long mbrProdOrderId,
                                   @RequestParam("mbrAddressId") Long mbrAddressId,
                                   @RequestParam("payMode") Integer payMode,
                                   HttpServletRequest request) {

        checkValidate(payMode);

        MbrProdOrder order = mbrProdOrderService.addMbrAddressInfoAndChoosePayMode(mbrProdOrderId,
                mbrAddressId,
                payMode);


        Member member = getCurMember(request);
        checkMemberIntegralMoreThanTheOrderShouldBePay(member, order);

        Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(order.getOrderNo(),
                                                                            order.getPayRealAmt(),
                                                                            member.getOpenId(),
                                                                            "/paymentNotify/async_notify/PRODUCT_ORDER.jhtml",
                                                                            "酒庄酒订单",
                                                                            request);

        return getResult(returnMap);
    }

    /**
     * 依据订单ID统一下单
     */
    @ApiOperation(value = "依据订单ID统一下单", notes = "依据订单ID统一下单")
    @ApiImplicitParams({@ApiImplicitParam(name = "mbrProdOrderId", value = "会员商品订单ID", dataType = "Long"),})
    @PostMapping("/unifiedOrder")
    public Map<String, Object> unifiedOrderOfProdOrder(Long mbrProdOrderId, HttpServletRequest request) {
        // 校验参数
        if (mbrProdOrderId == null) {
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }

        // 查询商品订单信息
        MbrProdOrder mbrProdOrder = mbrProdOrderService.getById(mbrProdOrderId);

        // 微信小程序支付预下单
        Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(mbrProdOrder.getOrderNo(), mbrProdOrder.getPayRealAmt(),
                getCurMember(request).getOpenId(),
                "/paymentNotify/async_notify/PRODUCT_ORDER.jhtml",
                "酒庄酒订单",
                request);

        return getResult(returnMap);
    }

    private void checkMemberIntegralMoreThanTheOrderShouldBePay(Member member, MbrProdOrder order) {

        if (member.getTotalIntegral().compareTo(order.getPayIntegralCnt()) < 0)
            throw new MemberIntegralNotEnough(RESPONSE_CODE_ENUM.MBR_INTEGRAL_NOT_ENOUGH);

    }

    private void checkValidate(Integer payMode) {

        if (payMode != MbrProdOrder.PAY_MODE_ENUM.WX_MINI_INTEGRAL_MODE.getValue()
                && payMode != MbrProdOrder.PAY_MODE_ENUM.INTEGRAL_MODE.getValue()
                && payMode != MbrProdOrder.PAY_MODE_ENUM.WX_MINI_MODE.getValue())
            throw new IllegalArgumentException("the pay mode don't support!");

    }


}

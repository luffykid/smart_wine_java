package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.model.app.MbrStoreOrderItem;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员储酒订单接口
 *
 */
@Api(value = "会员储酒订单接口", tags = "会员储酒订单接口")
@RestController("wxMiniMbrStoreOrderController")
@RequestMapping("/wxMini/auth/mbrStoreOrder")
public class MbrStoreOrderController extends BaseController {

    @Resource(name = "mbrStoreOrderServiceImpl")
    private MbrStoreOrderService mbrStoreOrderServiceImpl;
    /**
     * 获取我的储酒列表
     *
     * @return
     */
    @ApiOperation(value = "获取我的储酒列表", notes = "获取我的储酒列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(HttpServletRequest request) {
        Member member = getCurMember(request);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("totalStoreRemain", member.getTotalStoreRemain());
        returnMap.put("totalStoreIncrement", member.getTotalStoreIncrement());
        returnMap.put("list", mbrStoreOrderServiceImpl.getStoreList(member.getId()));
        return getResult(returnMap);
    }

    /**
     * 获取储酒订单详情
     *
     * @return
     */
    @ApiOperation(value = "获取储酒订单详情", notes = "获取储酒订单详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "储酒订单id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(Long id, HttpServletRequest request) {
        Member member = getCurMember(request);
        MbrStoreOrder mbrStoreOrder = mbrStoreOrderServiceImpl.getById(id);
        List<MbrStoreOrderItem> list = mbrStoreOrderServiceImpl.getMbrStoreOrderItemByStoreId(id);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("mbrStoreOrder", mbrStoreOrder);
        returnMap.put("MbrStoreOrderItem", list);
        return getResult(returnMap);
    }

    /**
     * 生成储酒订单
     *
     * @return
     */
    @ApiOperation(value = "生成储酒订单", notes = "生成储酒订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "云酒窖活动id", dataType = "Long"),
            @ApiImplicitParam(name = "skuId", value = "商品skuId", dataType = "Long"),
            @ApiImplicitParam(name = "prodTotalCnt", value = "商品数量", dataType = "Integer")
    })
    @RequestMapping(value = "/buildStoreOrder", method = RequestMethod.POST)
    public Map<String, Object> buildStoreOrder(Long activityId, Long skuId,
                                          @RequestParam(required = false,defaultValue ="1") Integer prodTotalCnt,
                                          HttpServletRequest request) {
        if(activityId ==null || skuId==null ){
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }
        Member member = getCurMember(request);
        try{
            Map<String, Object> orderDetail = mbrStoreOrderServiceImpl.buildStoreOrder(activityId, skuId, prodTotalCnt, member,request);
            String orderNo = (String) orderDetail.get("orderNo");
            BigDecimal payTotalAmt = (BigDecimal) orderDetail.get("payTotalAmt");

            //微信预下单
            Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(orderNo,
                    payTotalAmt, member.getOpenId(),
                    "/paymentNotify/async_notify/MBR_STORE_ORDER.jhtml", "会员储酒订单", request);
            return getResult(returnMap);
        }catch (Exception e){
            log.info("此处有错误:{}", "创建订单失败");
            throw new CustomException(RESPONSE_CODE_ENUM.CREATE_ORDER_ERROR);
        }
    }


}

package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrAdjustOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 会员自调酒订单接口
 *
 */
@Api(value = "会员自调酒订单接口", tags = "会员自调酒订单接口")
@RestController("wxMiniMbrAdjustOrderController")
@RequestMapping("/wxMini/auth/mbrAdjustOrder")
public class MbrAdjustOrderController extends BaseController {

    @Resource(name = "mbrAdjustOrderServiceImpl")
    private MbrAdjustOrderService mbrAdjustOrderServiceImpl;

    /**
     * 生成储酒订单
     *
     * @return
     */
    @ApiOperation(value = "生成自调酒订单", notes = "生成自调酒订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自调酒id", dataType = "Long"),
            @ApiImplicitParam(name = "payTotalAmt", value = "支付总金额", dataType = "BigDecimal")
    })
    @RequestMapping(value = "/buildAdjustOrder", method = RequestMethod.POST)
    public Map<String, Object> buildAdjustOrder(Long id, BigDecimal payTotalAmt, HttpServletRequest request) {
        if(id ==null){
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }
        Member member = getCurMember(request);
        try{
            Map<String, Object> orderDetail = mbrAdjustOrderServiceImpl.buildAdjustOrder(id, member,payTotalAmt);
            String orderNo = (String) orderDetail.get("orderNo");
//            BigDecimal payTotalAmt = (BigDecimal) orderDetail.get("payTotalAmt");

            //微信预下单
            Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(orderNo,
                    payTotalAmt, member.getOpenId(),
                    "/paymentNotify/async_notify/MBR_ADJUST_ORDER.jhtml", "自调酒订单", request);
            return getResult(returnMap);
        }catch (Exception e){
            log.info("此处有错误:{}", "创建订单失败");
            throw new CustomException(RESPONSE_CODE_ENUM.CREATE_ORDER_ERROR);
        }
    }


}

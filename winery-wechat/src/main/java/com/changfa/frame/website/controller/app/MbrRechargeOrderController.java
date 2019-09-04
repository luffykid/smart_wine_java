package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrRechargeOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 会员充值接口
 *
 */
@Api(value = "会员充值接口", tags = "会员充值接口")
@RestController("wxMiniMbrRechargeOrderController")
@RequestMapping("/wxMini/auth/mbrRechargeOrder")
public class MbrRechargeOrderController extends BaseController {

    @Resource(name = "mbrRechargeOrderServiceImpl")
    private MbrRechargeOrderService mbrRechargeOrderServiceImpl;
    /**
     * 会员新建充值订单
     *
     * @return
     */
    @ApiOperation(value = "会员新建充值订单", notes = "会员新建充值订单")
    @RequestMapping(value = "/unifiedOrder", method = RequestMethod.POST)
    public Map<String, Object> unifiedOrder(BigDecimal payAmt, HttpServletRequest request) {
        Member member = getCurMember(request);
        Long wineryId = getCurWineryId();
        try{
            Map<String, Object> orderDetail = mbrRechargeOrderServiceImpl.unifiedOrder(member.getId(), wineryId, payAmt);
            String orderNo = (String) orderDetail.get("orderNo");
//            BigDecimal payTotalAmt = (BigDecimal) orderDetail.get("payTotalAmt");

            //微信预下单
            Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(orderNo,
                    payAmt, member.getOpenId(),
                    "/paymentNotify/async_notify/MBR_RECHARGE_ORDER.jhtml", "会员新建充值订单", request);
            return getResult(returnMap);
        }catch (Exception e){
            log.info("此处有错误:{}", "创建订单失败");
            throw new CustomException(RESPONSE_CODE_ENUM.CREATE_ORDER_ERROR);
        }
    }

}

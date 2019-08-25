package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrRechargeOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员充值接口
 *
 */
@Api(value = "会员充值接口", tags = "会员充值接口")
@RestController("wxMiniMbrRechargeOrderController")
@RequestMapping("/wxMini/auth/mbrRechargeOrder")
public class MbrRechargeOrderController extends BaseController {

    @Autowired
    private MbrRechargeOrderService mbrRechargeOrderServiceImpl;
    /**
     * 会员新建充值订单
     *
     * @return
     */
    @ApiOperation(value = "会员新建充值订单", notes = "会员新建充值订单")
    @RequestMapping(value = "/recharge ", method = RequestMethod.GET)
    public Map<String, Object> recharge(Long wineryId, BigDecimal payTotalAmt, BigDecimal payRealAmt, HttpServletRequest request) {
        Member member = getCurMember(request);
        try{
            mbrRechargeOrderServiceImpl.recharge(member.getId(), wineryId, payTotalAmt, payRealAmt);
        }catch (Exception e){
            log.info("此处有错误:{}", "创建订单失败");
            throw new CustomException(RESPONSE_CODE_ENUM.CREATE_ORDER_ERROR);
        }
        return getResult(new HashMap<>());
    }

}

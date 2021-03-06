package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员商品订单接口
 *
 */
@Api(value = "会员商品订单接口", tags = "会员商品订单接口")
@RestController("wxMiniMbrStoreOrderController")
//@RequestMapping("/wxMini/auth/member")
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

}

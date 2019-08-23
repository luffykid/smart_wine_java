package com.changfa.frame.website.controller.app;

import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 会员商品订单接口
 *
 */
@Api(value = "会员商品订单接口", tags = "会员商品订单接口")
@RestController("wxMiniMbrStoreOrderController")
//@RequestMapping("/wxMini/auth/member")
public class MbrStoreOrderController extends BaseController {

    /**
     * 获取我的储酒列表
     *
     * @return
     */
    @ApiOperation(value = "获取我的储酒列表", notes = "获取我的储酒列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList() {
        return null;
    }

}

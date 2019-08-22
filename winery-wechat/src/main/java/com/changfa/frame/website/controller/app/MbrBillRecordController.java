package com.changfa.frame.website.controller.app;

import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 会员账单接口
 *
 */
@Api(value = "会员账单接口", tags = "会员账单接口")
@RestController("wxMiniMbrBillRecordController")
@RequestMapping("/wxMini/auth/mbrBillRecord")
public class MbrBillRecordController extends BaseController {
    /**
     * 获取优惠券列表
     *
     * @return
     */
    @ApiOperation(value = "获取优惠券列表", notes = "获取优惠券列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList() {
        return null;
    }

}

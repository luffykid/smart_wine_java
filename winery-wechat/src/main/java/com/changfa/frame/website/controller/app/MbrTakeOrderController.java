package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 会员商品订单接口
 *
 */
@Api(value = "会员取酒订单接口", tags = "会员取酒订单接口")
@RestController("wxMiniMbrTakeOrderController")
@RequestMapping("/wxMini/auth/mbrTakeOrder")
public class MbrTakeOrderController extends BaseController {
    /**
     * 获取荣誉庄主列表
     *
     * @param mbrStoreOrderId 会员储酒订单ID
     * @return
     */
    @ApiOperation(value = "获取存酒取酒订单列表", notes = "获取存酒取酒订单列表")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "mbrStoreOrderId", value = "会员储酒订单ID", dataType = "Long"))
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(Long mbrStoreOrderId) {
        return null;
    }

    /**
     * 获取存酒取酒订单详细信息
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "获取存酒取酒订单详细信息", notes = "获取存酒取酒订单详细信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(Long id) {

        return null;
    }

    /**
     * 自提
     *
     * @param mbrStoreOrderId 我的储酒订单Id
     * @param takeWeight 取酒重量
     * @return
     */
    @ApiOperation(value = "自提", notes = "自提")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbrStoreOrderId", value = "我的储酒订单Id", dataType = "Long"),
            @ApiImplicitParam(name = "takeWeight", value = "id", dataType = "BigDecimal")
    })
    @RequestMapping(value = "/takeInPerson", method = RequestMethod.GET)
    public Map<String, Object> takeInPerson(HttpServletRequest request, Long mbrStoreOrderId, BigDecimal takeWeight) {
        Member member = getCurMember(request);

        return null;
    }
}

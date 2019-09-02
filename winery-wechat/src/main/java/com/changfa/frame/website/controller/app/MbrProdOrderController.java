package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrProdOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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



}

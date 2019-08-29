package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.model.app.MbrStoreOrderItem;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员商品订单接口
 *
 */
@Api(value = "会员商品订单接口", tags = "会员商品订单接口")
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
            @ApiImplicitParam(name = "dId", value = "酒庄活动详情id", dataType = "Long"),
            @ApiImplicitParam(name = "sId", value = "商品skuId", dataType = "Long"),
            @ApiImplicitParam(name = "prodTotalCnt", value = "商品数量", dataType = "Integer")
    })
    @RequestMapping(value = "/buildOrder", method = RequestMethod.GET)
    public Map<String, Object> buildOrder(Long dId, Long sId, Integer prodTotalCnt, HttpServletRequest request) {
        Member member = getCurMember(request);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("totalStoreRemain", member.getTotalStoreRemain());
        returnMap.put("totalStoreIncrement", member.getTotalStoreIncrement());
        returnMap.put("list", mbrStoreOrderServiceImpl.getStoreList(member.getId()));
        return getResult(returnMap);
    }


}

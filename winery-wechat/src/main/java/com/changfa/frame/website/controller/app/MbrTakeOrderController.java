package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.MbrTakeOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrTakeOrderService;
import com.changfa.frame.service.mybatis.app.impl.MbrTakeOrderServiceImpl;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.github.pagehelper.PageInfo;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员商品订单接口
 *
 */
@Api(value = "会员取酒订单接口", tags = "会员取酒订单接口")
@RestController("wxMiniMbrTakeOrderController")
@RequestMapping("/wxMini/auth/mbrTakeOrder")
public class MbrTakeOrderController extends BaseController {

    @Resource(name = "mbrTakeOrderServiceImpl")
    private MbrTakeOrderService mbrTakeOrderServiceImpl;
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
    public Map<String, Object> getList(Long mbrStoreOrderId, PageInfo pageInfo) {
        PageInfo<MbrTakeOrder> list = mbrTakeOrderServiceImpl.getListByMbrStoreOrderId(mbrStoreOrderId, pageInfo);

        return getResult(list);
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

        MbrTakeOrder mbrTakeOrder = mbrTakeOrderServiceImpl.getById(id);
        if (mbrTakeOrder == null){
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        return getResult(mbrTakeOrder);
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
        try{
            mbrTakeOrderServiceImpl.takeInPerson(mbrStoreOrderId, takeWeight);
        }catch (Exception e){
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult(new HashMap<>());
    }
}

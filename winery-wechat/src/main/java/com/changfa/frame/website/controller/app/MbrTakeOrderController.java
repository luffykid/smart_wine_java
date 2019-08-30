package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.util.DateUtil;
import com.changfa.frame.model.app.MbrStoreOrderItem;
import com.changfa.frame.model.app.MbrTakeOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.service.mybatis.app.MbrTakeOrderService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 会员商品订单接口
 */
@Api(value = "会员取酒订单接口", tags = "会员取酒订单接口")
@RestController("wxMiniMbrTakeOrderController")
@RequestMapping("/wxMini/auth/mbrTakeOrder")
public class MbrTakeOrderController extends BaseController {

    @Resource(name = "mbrTakeOrderServiceImpl")
    private MbrTakeOrderService mbrTakeOrderServiceImpl;
    @Resource
    private MbrStoreOrderService mbrStoreOrderServiceImpl;

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
    public Map<String, Object> getList(Long mbrStoreOrderId, int pageSize,int pageNum) {
        // 查询数据集合
        Map<String, List<MbrTakeOrder>> returnMap = new LinkedHashMap<>();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        PageInfo<MbrTakeOrder> orderPageInfo = mbrTakeOrderServiceImpl.getListByMbrStoreOrderId(mbrStoreOrderId, pageInfo);
        List<MbrTakeOrder> takeOrders = orderPageInfo.getList();
        if (CollectionUtils.isEmpty(takeOrders)) {
            return getResult(null);
        }

        // 按照时间格式返回列表数据
        for (MbrTakeOrder takeOrder : takeOrders) {
            Date createDate = takeOrder.getCreateDate();
            String dateStr = DateUtil.convertDateToStr(createDate, "yyyy年MM月dd日");
            returnMap.put(dateStr, new ArrayList<MbrTakeOrder>());
        }
        for (String dateStrKey : returnMap.keySet()) {
            ArrayList<MbrTakeOrder> mbrTakeOrders = new ArrayList<>();
            for (MbrTakeOrder takeOrder : takeOrders) {
                Date createDate = takeOrder.getCreateDate();
                String dateStr = DateUtil.convertDateToStr(createDate, "yyyy年MM月dd日");
                if (StringUtils.equalsIgnoreCase(dateStr, dateStrKey)) {
                    mbrTakeOrders.add(takeOrder);
                }
                continue;
            }
            returnMap.put(dateStrKey, mbrTakeOrders);
        }

        return getResult(returnMap);
    }

    /**
     * 获取存酒取酒订单详细信息
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "获取存酒取酒订单详细信息", notes = "获取存酒取酒订单详细信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "取酒订单id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(Long id) {

        MbrTakeOrder mbrTakeOrder = mbrTakeOrderServiceImpl.getById(id);
        if (mbrTakeOrder == null) {
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        List<MbrStoreOrderItem> list = mbrStoreOrderServiceImpl.getMbrStoreOrderItemByStoreId(mbrTakeOrder.getMbrStoreOrderId());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("mbrTakeOrder", mbrTakeOrder);
        returnMap.put("sku_name", list.get(0).getSkuName());
        return getResult(returnMap);
    }

    /**
     * 自提
     *
     * @param mbrStoreOrderId 我的储酒订单Id
     * @param takeWeight      取酒重量
     * @return
     */
    @ApiOperation(value = "自提", notes = "自提")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbrStoreOrderId", value = "我的储酒订单Id", dataType = "Long"),
            @ApiImplicitParam(name = "takeWeight", value = "id", dataType = "BigDecimal")
    })
    @RequestMapping(value = "/takeInPerson", method = RequestMethod.POST)
    public Map<String, Object> takeInPerson(HttpServletRequest request, Long mbrStoreOrderId, BigDecimal takeWeight) {
        Member member = getCurMember(request);
        Long takeOrderId;
        try {
            takeOrderId = mbrTakeOrderServiceImpl.takeInPerson(mbrStoreOrderId, takeWeight);
        } catch (Exception e) {
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult(takeOrderId);
    }
}

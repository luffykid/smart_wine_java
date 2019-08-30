package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrTakeOrder;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

public interface MbrTakeOrderService extends BaseService<MbrTakeOrder, Long> {

    /**
     * 获取储酒提酒列表
     * @param mbrStoreOrderId
     * @return
     */
    PageInfo getListByMbrStoreOrderId(Long mbrStoreOrderId, PageInfo pageInfo);
    /**
     * 自提
     *
     * @param mbrStoreOrderId 我的储酒订单Id
     * @param takeWeight 取酒重量
     * @return
     */
    Long takeInPerson(Long mbrStoreOrderId, BigDecimal takeWeight) throws Exception;
}

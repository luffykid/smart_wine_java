package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.model.app.MbrStoreOrderItem;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.common.BaseService;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MbrStoreOrderService extends BaseService<MbrStoreOrder, Long> {
    /**
     * 我的储酒
     * @param mbrId
     * @return
     */
    public List<MbrStoreOrder> getStoreList(@Param("mbrId") Long mbrId);

    /**
     * 获取储酒订单项
     * @param mbrStoreOrderId
     * @return
     */
    public List<MbrStoreOrderItem> getMbrStoreOrderItemByStoreId(Long mbrStoreOrderId);


    /**
     * 生成储酒订单
     *
     * @return  订单号 orderNo
     */
    public Map<String, Object> buildStoreOrder(Long activityId, Long skuId, Integer prodTotalCnt , Member member, HttpServletRequest request);
}

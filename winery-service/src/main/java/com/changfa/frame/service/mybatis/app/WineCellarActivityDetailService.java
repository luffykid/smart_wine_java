package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCellarActivityDetail;
import com.changfa.frame.service.mybatis.common.BaseService;


import java.util.List;
import java.util.Map;

public interface WineCellarActivityDetailService extends BaseService<WineCellarActivityDetail, Long> {

    /**
     * 获取活动关联商品
     * @param wineCellarActivityId
     * @return
     */
    public List<Map> getProdSkuList(Long wineCellarActivityId);

    /**
     * 活动订单预支付详情
     * @param id
     * @return
     */
    public Map getPrePayDetail(Long id);

    /**
     * 获取商品详情
     * @param id
     * @return
     */
    public Map getProdSkuDetail(Long id);
}

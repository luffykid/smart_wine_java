package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

public interface MbrProdOrderService extends BaseService<MbrProdOrder, Long> {
    /**
     * 获取我的订单分类信息
     *
     * @return
     */
    public PageInfo getListByType(Integer orderStatus, PageInfo pageInfo);
}

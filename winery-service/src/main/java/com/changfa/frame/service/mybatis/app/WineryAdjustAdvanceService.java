package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineryAdjustAdvance;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface WineryAdjustAdvanceService extends BaseService<WineryAdjustAdvance, Long> {

    /**
     * 获取根据order排序的预制酒列表
     * @return
     */
    List<WineryAdjustAdvance> selectListByAdjustIdAndStatusOrderBySort(Long AdjustId, Integer status);
}

package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineryAdjustAdvanceMapper;
import com.changfa.frame.model.app.WineryAdjustAdvance;
import com.changfa.frame.service.mybatis.app.WineryAdjustAdvanceService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wineryAdjustAdvanceServiceImpl")
public class WineryAdjustAdvanceServiceImpl extends BaseServiceImpl<WineryAdjustAdvance, Long> implements WineryAdjustAdvanceService {

    @Autowired
    private WineryAdjustAdvanceMapper wineryAdjustAdvanceMapper;
    /**
     * 获取根据order排序的预制酒列表
     * @return
     */
    @Override
    public List<WineryAdjustAdvance> selectListByAdjustIdAndStatusOrderBySort(Long AdjustId, Integer status) {
        return wineryAdjustAdvanceMapper.selectListByAdjustIdAndStatusOrderBySort( AdjustId, status);

    }
}

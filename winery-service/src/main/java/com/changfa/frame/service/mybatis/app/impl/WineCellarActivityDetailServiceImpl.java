package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineCellarActivityDetailMapper;
import com.changfa.frame.model.app.WineCellarActivityDetail;
import com.changfa.frame.service.mybatis.app.WineCellarActivityDetailService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("wineCellarActivityDetailServiceImpl")
public class WineCellarActivityDetailServiceImpl extends BaseServiceImpl<WineCellarActivityDetail, Long> implements WineCellarActivityDetailService {
    @Autowired
    private WineCellarActivityDetailMapper wineCellarActivityDetailMapper;
    /**
     * 获取活动关联商品
     * @param wineCellarActivityId
     * @return
     */
    @Override
    public List<Map> getProdSkuList(Long wineCellarActivityId) {
        return wineCellarActivityDetailMapper.selectProdSkuList(wineCellarActivityId);
    }
}

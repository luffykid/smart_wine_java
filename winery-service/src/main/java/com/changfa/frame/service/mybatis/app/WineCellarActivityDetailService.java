package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCellarActivityDetail;
import com.changfa.frame.service.mybatis.common.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WineCellarActivityDetailService extends BaseService<WineCellarActivityDetail, Long> {

    /**
     * 获取活动关联商品
     * @param wineCellarActivityId
     * @return
     */
    public List<Map> getProdSkuList(@Param("wineCellarActivityId") Long wineCellarActivityId);
}

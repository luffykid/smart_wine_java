package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.service.mybatis.common.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MbrStoreOrderService extends BaseService<MbrStoreOrder, Long> {
    /**
     * 我的储酒
     * @param mbrId
     * @return
     */
    public List<Map> getStoreList(@Param("mbrId") Long mbrId);
}

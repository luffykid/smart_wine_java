/*
 * MbrBillRecordMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrBillRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MbrBillRecordMapper extends BaseMapper<MbrBillRecord, Long> {

    /**
     * 获取消费流水记录
     * @param mbrId
     * @return
     */
    public List<MbrBillRecord> selectFlowList(@Param("mbrId") Long mbrId);
}
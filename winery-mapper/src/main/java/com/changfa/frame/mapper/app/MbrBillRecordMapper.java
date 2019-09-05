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

import java.math.BigDecimal;
import java.util.List;

public interface MbrBillRecordMapper extends BaseMapper<MbrBillRecord, Long> {

    /**
     * 获取消费流水记录
     * @param mbrId
     * @return
     */
    List<MbrBillRecord> selectFlowList(@Param("mbrId") Long mbrId);

    /**
     * 根据会员ID和账单类型查询消费总额
     *
     * @param mbrId 会员ID
     * @param types 账单类型
     * @return
     */
    BigDecimal getCustomAmtByType(Long mbrId, List<Integer> types);
}
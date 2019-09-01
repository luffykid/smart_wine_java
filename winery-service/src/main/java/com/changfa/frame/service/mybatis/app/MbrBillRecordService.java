package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrBillRecord;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

public interface MbrBillRecordService extends BaseService<MbrBillRecord, Long> {
    /**
     * 获取消费流水记录
     *
     * @param mbrId
     * @return
     */
    PageInfo<MbrBillRecord> getFlowList(Long mbrId, PageInfo pageInfo);

    /**
     * 根据会员ID和账单类型查询消费总额
     *
     * @param mbrId 会员ID
     * @param types 账单类型
     * @return
     */
    BigDecimal getCustomAmtByType(Long mbrId, List<Integer> types);
}

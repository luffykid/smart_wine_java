package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrBillRecord;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MbrBillRecordService extends BaseService<MbrBillRecord, Long> {
    /**
     * 获取消费流水记录
     * @param mbrId
     * @return
     */
    PageInfo<MbrBillRecord> getFlowList(Long mbrId, PageInfo pageInfo);
}

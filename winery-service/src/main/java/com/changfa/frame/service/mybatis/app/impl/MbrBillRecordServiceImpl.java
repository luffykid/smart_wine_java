package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrBillRecordMapper;
import com.changfa.frame.model.app.MbrBillRecord;
import com.changfa.frame.service.mybatis.app.MbrBillRecordService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MbrBillRecordServiceImpl")
public class MbrBillRecordServiceImpl extends BaseServiceImpl<MbrBillRecord, Long> implements MbrBillRecordService {

    @Autowired
    private MbrBillRecordMapper mbrBillRecordMapper;

    /**
     * 获取消费流水记录
     * @param mbrId
     * @return
     */
    @Override
    public PageInfo<MbrBillRecord> getFlowList(Long mbrId, PageInfo pageInfo) {

        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(mbrBillRecordMapper.selectFlowList(mbrId));
    }
}

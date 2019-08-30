package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrWineryVoucherMapper;
import com.changfa.frame.model.app.MbrWineryVoucher;
import com.changfa.frame.service.mybatis.app.MbrWineryVoucherService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mbrWineryVoucherServiceImpl")
public class MbrWineryVoucherServiceImpl extends BaseServiceImpl<MbrWineryVoucher, Long> implements MbrWineryVoucherService {

    @Autowired
    private MbrWineryVoucherMapper mbrWineryVoucherMapper;

    /**
     * 获取可用优惠券的张数
     * @param mbrId
     * @return
     */
    @Override
    public Integer getEnableVoucherCount(Long mbrId) {
        return mbrWineryVoucherMapper.selectEnableVoucherCount(mbrId);
    }

    /**
     * 获取可以使用的优惠券列表
     * @return
     */
    @Override
    public List<MbrWineryVoucher> getEnableVoucherList(Long mbrId) {
        return mbrWineryVoucherMapper.selectEnableVoucherList(mbrId);
    }
}

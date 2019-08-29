package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineryVoucher;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface MbrWineryVoucherService extends BaseService<MbrWineryVoucher, Long> {

    /**
     * 获取可用优惠券的张数
     * @param mbrId
     * @return
     */
    Integer getEnableVoucherCount(Long mbrId);

    /**
     * 获取可以使用的优惠券列表
     * @return
     */
    List<MbrWineryVoucher> getEnableVoucherList(Long mbrId);
}

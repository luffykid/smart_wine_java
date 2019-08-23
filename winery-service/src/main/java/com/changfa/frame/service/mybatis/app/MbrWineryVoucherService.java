package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineryVoucher;
import com.changfa.frame.service.mybatis.common.BaseService;

public interface MbrWineryVoucherService extends BaseService<MbrWineryVoucher, Long> {

    /**
     * 获取可用优惠券的张数
     * @param mbrId
     * @return
     */
    Integer getEnableVoucherCount(Long mbrId);
}

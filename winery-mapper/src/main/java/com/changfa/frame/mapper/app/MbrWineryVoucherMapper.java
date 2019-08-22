/*
 * MbrWineryVoucherMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrWineryVoucher;
import org.apache.ibatis.annotations.Param;

public interface MbrWineryVoucherMapper extends BaseMapper<MbrWineryVoucher, Long> {

    /**
     *获取可以使用的优惠券数量
     * @return
     */
    Integer selectEnableVoucherCount(@Param("mbrId") Long mbrId, @Param("voucherCnt") Integer voucherCnt);
}
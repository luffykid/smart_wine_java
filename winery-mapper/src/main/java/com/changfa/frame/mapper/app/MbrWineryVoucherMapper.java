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

import java.util.Date;
import java.util.List;

public interface MbrWineryVoucherMapper extends BaseMapper<MbrWineryVoucher, Long> {

    /**
     * 根据会员id和时间查询有效优惠券
     *
     * @param nowDate
     * @return
     */
    List<MbrWineryVoucher> selectEffectByMbrId(Long mbrId, Date nowDate);

}
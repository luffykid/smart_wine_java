package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineryVoucher;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.Date;
import java.util.List;

public interface MbrWineryVoucherService extends BaseService<MbrWineryVoucher, Long> {

    /**
     *
     * @return
     */
    List<MbrWineryVoucher> selectListByMbrId(Date date);

}

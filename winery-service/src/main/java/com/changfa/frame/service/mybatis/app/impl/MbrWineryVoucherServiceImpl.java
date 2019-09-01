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

}

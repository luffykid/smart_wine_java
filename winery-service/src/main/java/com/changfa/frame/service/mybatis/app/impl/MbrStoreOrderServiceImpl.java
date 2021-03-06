package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrStoreOrderMapper;
import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("mbrStoreOrderServiceImpl")
public class MbrStoreOrderServiceImpl extends BaseServiceImpl<MbrStoreOrder, Long> implements MbrStoreOrderService {

    @Autowired
    private MbrStoreOrderMapper mbrStoreOrderMapper;
    /**
     * 我的储酒
     * @param mbrId
     * @return
     */
    @Override
    public List<Map> getStoreList(Long mbrId) {

        return mbrStoreOrderMapper.selectStoreList(mbrId);
    }
}

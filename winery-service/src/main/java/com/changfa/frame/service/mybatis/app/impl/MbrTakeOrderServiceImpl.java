package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.model.app.MbrTakeOrder;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.service.mybatis.app.MbrTakeOrderService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("mbrTakeOrderServiceImpl")
public class MbrTakeOrderServiceImpl extends BaseServiceImpl<MbrTakeOrder, Long> implements MbrTakeOrderService {

    @Autowired
    private MbrStoreOrderService mbrStoreOrderServiceImpl;
    /**
     * 自提
     *
     * @param mbrStoreOrderId 我的储酒订单Id
     * @param takeWeight 取酒重量
     * @return
     */
    @Override
    public boolean takeInPerson(Long mbrStoreOrderId, BigDecimal takeWeight) {
        MbrStoreOrder mbrStoreOrder = mbrStoreOrderServiceImpl.getById(mbrStoreOrderId);

        return false;
    }
}

package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrTakeOrderMapper;
import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.model.app.MbrTakeOrder;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.service.mybatis.app.MbrTakeOrderService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("mbrTakeOrderServiceImpl")
public class MbrTakeOrderServiceImpl extends BaseServiceImpl<MbrTakeOrder, Long> implements MbrTakeOrderService {

    @Autowired
    private MbrTakeOrderMapper mbrTakeOrderMapper;
    @Autowired
    private MbrStoreOrderService mbrStoreOrderServiceImpl;

    /**
     * 获取储酒提酒列表
     * @param mbrStoreOrderId
     * @return
     */
    @Override
    public PageInfo getListByMbrStoreOrderId(Long mbrStoreOrderId, PageInfo pageInfo) {

        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(mbrTakeOrderMapper.selectListByMbrStoreOrderId(mbrStoreOrderId));
    }

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
        BigDecimal totalEnableWeight = mbrStoreOrder.getTotalRemainWeight().add(mbrStoreOrder.getTotalIncreaseCapacity());
        BigDecimal totalEnableCapacity = mbrStoreOrder.getTotalRemainWeight().add(mbrStoreOrder.getTotalIncreaseCapacity()).divide(new BigDecimal(1)).multiply(new BigDecimal(500));
        BigDecimal takeWeightCapacity = takeWeight.divide(new BigDecimal(1)).multiply(new BigDecimal(500));
        if (totalEnableWeight.compareTo(takeWeight) == -1)
            return false;
        if (mbrStoreOrder.getTotalRemainWeight().compareTo(takeWeight) == -1){
            mbrStoreOrder.setTotalRemainCapacity(new BigDecimal(0));
            mbrStoreOrder.setTotalRemainWeight(new BigDecimal(0));
            mbrStoreOrder.setTotalIncreaseWeight(totalEnableWeight.subtract(takeWeight));
            mbrStoreOrder.setTotalIncreaseWeight(totalEnableCapacity.subtract(takeWeightCapacity));
        }else{
            mbrStoreOrder.setTotalRemainCapacity(mbrStoreOrder.getTotalRemainCapacity().subtract(takeWeightCapacity));
            mbrStoreOrder.setTotalRemainWeight(mbrStoreOrder.getTotalRemainWeight().subtract(takeWeight));
        }
        mbrStoreOrderServiceImpl.update(mbrStoreOrder);
        return true;
    }
}

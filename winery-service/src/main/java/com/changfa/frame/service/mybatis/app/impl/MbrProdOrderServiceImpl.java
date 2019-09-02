package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.MbrProdOrderMapper;
import com.changfa.frame.mapper.app.MbrProdOrderRecordMapper;
import com.changfa.frame.mapper.app.ProdSkuMapper;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrProdOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("mbrProdOrderServiceImpl")
public class MbrProdOrderServiceImpl extends BaseServiceImpl<MbrProdOrder, Long> implements MbrProdOrderService {
    @Autowired
    private MbrProdOrderMapper mbrProdOrderMapper;

    @Autowired
    private MbrProdOrderRecordMapper mbrProdOrderRecordMapper;

    @Autowired
    private ProdSkuMapper prodSkuMapper;

    /**
     * 获取我的订单分类信息
     *
     * @return
     */
    @Override
    public PageInfo getListByType(Long mbrId, Integer orderStatus, PageInfo pageInfo){
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(mbrProdOrderMapper.selectListByType(mbrId, orderStatus));
    }

    @Override
    public MbrProdOrder placeAnOrder(Long wineryId, Long mbrId, List<MbrProdOrderItem> items) {

        checkValidate(items);

        MbrProdOrder order = new MbrProdOrder();
        order.setId(IDUtil.getId());
        order.setMbrId(mbrId);
        order.setWineryId(wineryId);
        order.setOrderNo(OrderNoUtil.get());
        order.setCreateDate(new Date());
        order.setModifyDate(new Date());

        completeMbrProdOrderItems(order, items);

        return null;
    }

    private void completeMbrProdOrderItems(MbrProdOrder order, List<MbrProdOrderItem> items) {

        items.stream().forEach(mbrProdOrderItem -> {

            ProdSku sku = prodSkuMapper.getById(mbrProdOrderItem.getId());
            mbrProdOrderItem.setMbrProdOrderId(order.getId());
            mbrProdOrderItem.setWineryId(order.getWineryId());
            mbrProdOrderItem.setMbrId(order.getMbrId());

        });

    }

    private void addMbrProdOrderRecordToRepository(MbrProdOrder order) {

        MbrProdOrderRecord record = new MbrProdOrderRecord();
        record.setId(IDUtil.getId());
        record.setMbrProdOrderId(order.getId());
        record.setOrderStatus(order.getOrderStatus());
        record.setCreateDate(new Date());
        record.setModifyDate(new Date());

        mbrProdOrderRecordMapper.save(record);

    }

    private void checkValidate(List<MbrProdOrderItem> items) {

        if (items == null)
            throw new NullPointerException("order items must not be null!");

        if (items.size() == 0)
            throw new IllegalArgumentException("order items at less one line");

    }


}

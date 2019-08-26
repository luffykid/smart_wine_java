package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrProdOrderMapper;
import com.changfa.frame.model.app.MbrProdOrder;
import com.changfa.frame.service.mybatis.app.MbrProdOrderService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mbrProdOrderServiceImpl")
public class MbrProdOrderServiceImpl extends BaseServiceImpl<MbrProdOrder, Long> implements MbrProdOrderService {
    @Autowired
    private MbrProdOrderMapper mbrProdOrderMapper;
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
}

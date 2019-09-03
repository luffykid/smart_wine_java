package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.MbrAdjustOrderMapper;
import com.changfa.frame.mapper.app.MbrAdjustOrderRecordMapper;
import com.changfa.frame.mapper.app.WineryAdjustWineMapper;
import com.changfa.frame.model.app.MbrAdjustOrder;
import com.changfa.frame.model.app.MbrAdjustOrderRecord;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineryAdjustWine;
import com.changfa.frame.service.mybatis.app.MbrAdjustOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员自调酒业务实现
 *
 * @author wyy
 * @date 2019-08-31 01:19
 */
@Service("mbrAdjustOrderServiceImpl")
public class MbrAdjustOrderServiceImpl extends BaseServiceImpl<MbrAdjustOrder, Long> implements MbrAdjustOrderService {

    @Autowired
    private MbrAdjustOrderMapper mbrAdjustOrderMapper;

    @Autowired
    private MbrAdjustOrderRecordMapper mbrAdjustOrderRecordMapper;

    @Autowired
    private WineryAdjustWineMapper wineryAdjustWineMapper;
    /**
     * 生成自调酒订单
     *
     * @return  订单号 orderNo
     */
    @Transactional
    @Override
    public Map<String, Object> buildAdjustOrder(Long adjustId, Member member,BigDecimal payTotalAmt){

        //1.获取相关信息
        WineryAdjustWine wineryAdjustWine = wineryAdjustWineMapper.getById(adjustId);
        String orderNo = OrderNoUtil.get();
        //2.保存MbrAdjustOrder
        MbrAdjustOrder mbrAdjustOrder = new MbrAdjustOrder();
        //四个id
        mbrAdjustOrder.setId(IDUtil.getId());
        mbrAdjustOrder.setWineryAdjustWineId(adjustId);
        mbrAdjustOrder.setMbrId(member.getId());
        mbrAdjustOrder.setWineryId(wineryAdjustWine.getWineryId());
        mbrAdjustOrder.setPayMode(MbrAdjustOrder.PAY_MODE_ENUM.WX_MINI_MODE.getValue());//微信支付
        mbrAdjustOrder.setPayTotalAmt(payTotalAmt);
        mbrAdjustOrder.setPayRealAmt(mbrAdjustOrder.getPayTotalAmt());
        //mbrAdjustOrder.setPrintImg();
        mbrAdjustOrder.setOrderStatus(MbrAdjustOrder.ORDER_STATUS_ENUM.PAY_NOT.getValue());//未支付
        mbrAdjustOrder.setOrderNo(orderNo);
        //mbrAdjustOrder.setPayDate();//支付时间
        mbrAdjustOrder.setCreateDate(new Date());
        mbrAdjustOrderMapper.save(mbrAdjustOrder);

        //3.保存MbrAdjustOrderRecord
        MbrAdjustOrderRecord mbrAdjustOrderRecord = new MbrAdjustOrderRecord();
        mbrAdjustOrderRecord.setId(IDUtil.getId());
        mbrAdjustOrderRecord.setMbrAdjustOrderId(mbrAdjustOrder.getId());
        mbrAdjustOrderRecord.setWineryId(wineryAdjustWine.getWineryId());
        mbrAdjustOrderRecord.setPayTotalAmt(payTotalAmt);
        mbrAdjustOrderRecord.setPayRealAmt(mbrAdjustOrderRecord.getPayTotalAmt());
        mbrAdjustOrderRecord.setOrderStatus(MbrAdjustOrderRecord.ORDER_STATUS_ENUM.PAY_NOT.getValue());
        mbrAdjustOrderRecord.setCreateDate(new Date());


        // 4.返回订单号和金额 用于微信预下单
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("orderNo",orderNo);
        returnMap.put("payTotalAmt",mbrAdjustOrder.getPayTotalAmt());

        return returnMap;
    }
}

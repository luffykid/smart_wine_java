package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("mbrWineCustomOrderServiceImpl")
public class MbrWineCustomOrderServiceImpl extends BaseServiceImpl<MbrWineCustomOrder, Long> implements MbrWineCustomOrderService {

    @Autowired
    private WineCustomMapper wineCustomMapper;

    @Autowired
    private MbrWineCustomOrderRecordMapper mbrWineCustomOrderRecordMapper;

    @Autowired
    private MbrWineCustomOrderMapper mbrWineCustomOrderMapper;

    @Autowired
    private MbrWineCustomMapper mbrWineCustomMapper;

    @Autowired
    private MbrWineCustomDetailMapper mbrWineCustomDetailMapper;

    @Autowired
    private WineCustomElementContentMapper wineCustomElementContentMapper;

    @Autowired
    private MbrAddressMapper mbrAddressMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MbrBillRecordMapper mbrBillRecordMapper;

    /**
     * 保存会员定制信息
     *
     * @param mbrWineCustomDetails 会员白酒定制详情集合
     * @param mbrWineCustom        会员白酒定制
     * @param member               会员
     * @return
     */
    @Transactional
    @Override
    public Map<String, Object> saveMbrCustomInfo(List<MbrWineCustomDetail> mbrWineCustomDetails, MbrWineCustom mbrWineCustom, Member member) {
        // 初始化返回数据
        Map<String, Object> returnMap = new HashMap();
        Long mbrWineCustomId = IDUtil.getId();
        try {
            // 1、依据定制ID查询会员白酒定制并封装会员定制对象
            WineCustom wineCustom = wineCustomMapper.getById(mbrWineCustom.getWineCustomId());
            MbrWineCustom mbrWineCustomOfSave = new MbrWineCustom();
            mbrWineCustomOfSave.setId(mbrWineCustomId);
            mbrWineCustomOfSave.setWineCustomId(mbrWineCustom.getWineCustomId());
            mbrWineCustomOfSave.setWineryId(member.getWineryId());
            mbrWineCustomOfSave.setMbrId(member.getId());
            // 定制数量
            Integer customCnt = mbrWineCustom.getCustomCnt();
            mbrWineCustomOfSave.setCustomCnt(customCnt);
            // 定制价格
            BigDecimal customPrice = wineCustom.getCustomPrice();
            mbrWineCustomOfSave.setCustomPrice(customPrice);

            // 定制总金额
            BigDecimal customTotalPrice = customPrice.multiply(new BigDecimal(customCnt)).setScale(2, BigDecimal.ROUND_HALF_UP);
            mbrWineCustomOfSave.setCustomTotalAmt(customTotalPrice);
            mbrWineCustomOfSave.setCustomName(wineCustom.getCustomName());
            mbrWineCustomOfSave.setCreateDate(new Date());
            mbrWineCustomOfSave.setModifyDate(new Date());
            mbrWineCustomMapper.save(mbrWineCustomOfSave);

            // 2、根据酒庄ID查询白酒定制元素内容
            WineCustomElementContent wineCustomElementContent = new WineCustomElementContent();
            wineCustomElementContent.setWineryId(member.getWineryId());
            wineCustomElementContent.setWineCustomId(mbrWineCustom.getWineCustomId());
            List<WineCustomElementContent> elementContents = wineCustomElementContentMapper.selectList(wineCustomElementContent);

            // 3、封装会员定制详情
            List<MbrWineCustomDetail> mbrCustomDetails = new ArrayList();
            for (MbrWineCustomDetail detail : mbrWineCustomDetails) {
                MbrWineCustomDetail mbrWineCustomDetail = new MbrWineCustomDetail();
                mbrWineCustomDetail.setId(IDUtil.getId());
                mbrWineCustomDetail.setWineCustomId(mbrWineCustom.getWineCustomId());
                mbrWineCustomDetail.setWineCustomElementId(detail.getWineCustomElementId());
                mbrWineCustomDetail.setMbrWineCustomId(mbrWineCustomId);
                mbrWineCustomDetail.setMbrId(member.getId());
                // 设置背景图、蒙版、底图、预置图
                for (WineCustomElementContent elementContent : elementContents) {
                    if (elementContent.getWineCustomElementId().equals(detail.getWineCustomElementId())) {
                        mbrWineCustomDetail.setBgImg(elementContent.getBgImg());
                        mbrWineCustomDetail.setBottomImg(elementContent.getBottomImg());
                        mbrWineCustomDetail.setMaskImg(elementContent.getMaskImg());
                        mbrWineCustomDetail.setCreateDate(new Date());
                        mbrWineCustomDetail.setModifyDate(new Date());
                        // 获取预览图并设置
                        String previewImg = FileUtil.copyNFSByFileName(detail.getPreviewImg(), FilePathConsts.TEST_FILE_PATH);
                        mbrWineCustomDetail.setPreviewImg(previewImg);
                        //获取合成图并设置
                        String printImg = FileUtil.getUrlOfComposite(elementContent.getBottomImg(), previewImg, new BigDecimal(0), new BigDecimal(0), 1, FilePathConsts.FILE_PREVIEW_PATH);
                        mbrWineCustomDetail.setPrintImg(printImg);

                    }
                }
                mbrCustomDetails.add(mbrWineCustomDetail);
            }
            mbrWineCustomDetailMapper.saveOfBatch(mbrCustomDetails);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            returnMap.put("result", false);
            return returnMap;
        }

        // 如果成功，则返回会员定制ID
        returnMap.put("result", true);
        returnMap.put("mbrWineCustomId", mbrWineCustomId);
        return returnMap;
    }



    /**
     * 处理会员白酒定制订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */

    @Transactional
    @Override
    public void handleNotifyOfWineCustomOrder(String outTradeNo, String transactionId, Date payDate) {
        // 根据订单号查询订单信息
        MbrWineCustomOrder dbOrder = mbrWineCustomOrderMapper.getByOrderNo(outTradeNo);
        if (dbOrder == null) {
            return;
        }

        // 如果订单已经处理则不在处理订单
        if (dbOrder.getOrderStatus().equals(MbrWineCustomOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue())) {
            return;
        }

        // 更新商品订单
        MbrWineCustomOrder updateOrder = new MbrWineCustomOrder();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setOrderStatus(MbrWineCustomOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        updateOrder.setTransactionNo(transactionId);
        updateOrder.setPayDate(payDate);
        updateOrder.setModifyDate(new Date());
        mbrWineCustomOrderMapper.update(updateOrder);

        // 插入订单记录
        MbrWineCustomOrderRecord saveOrderRecord = new MbrWineCustomOrderRecord();
        saveOrderRecord.setId(IDUtil.getId());
        saveOrderRecord.setMbrWineCustomOrderId(dbOrder.getId());
        saveOrderRecord.setOrderStatus(MbrWineCustomOrderRecord.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        saveOrderRecord.setCreateDate(new Date());
        saveOrderRecord.setModifyDate(new Date());
        mbrWineCustomOrderRecordMapper.save(saveOrderRecord);

        // 插入会员账户流水信息
        MbrBillRecord mbrBillRecord = new MbrBillRecord();
        mbrBillRecord.setId(IDUtil.getId());
        mbrBillRecord.setPkId(dbOrder.getId());
        mbrBillRecord.setWineryId(dbOrder.getWineryId());
        mbrBillRecord.setSignType(0);
        mbrBillRecord.setBillType(MbrBillRecord.BILL_TYPE_ENUM.WINE_CUSTOM.getValue());
        mbrBillRecord.setBillRemark("会员白酒定制消费");
        mbrBillRecord.setBillAmt(dbOrder.getPayRealAmt());
        mbrBillRecord.setCreateDate(new Date());
        mbrBillRecord.setModifyDate(new Date());
        mbrBillRecordMapper.save(mbrBillRecord);

    }

    /**
     * 处理会员定制预下单业务
     *
     * @param member          会员
     * @param mbrWineCustomId 会员定制ID
     * @param mbrAddressId    会员地址ID
     * @param customCnt       定制数量
     * @return
     */
    @Transactional
    @Override
    public Map<String, Object> handleCustomPreOrder(Member member, Long mbrWineCustomId, Long mbrAddressId, Integer customCnt) {
        // 初始化返回数据
        Map<String, Object> returnMap = new HashMap();
        Long mbrWineCustomOrderId = IDUtil.getId();
        String orderNo = OrderNoUtil.get();

        // 获取会员地址
        MbrAddress mbrAddress = mbrAddressMapper.getById(mbrAddressId);

        // 计算总金额
        MbrWineCustom mbrWineCustom = mbrWineCustomMapper.getById(mbrWineCustomId);
        BigDecimal payTotalAmt = mbrWineCustom.getCustomPrice().multiply(new BigDecimal(customCnt)).setScale(2, BigDecimal.ROUND_HALF_UP);

        try {
            // 1、更新会员白酒定制
            mbrWineCustom.setMbrWineCustomOrderId(mbrWineCustomOrderId);
            mbrWineCustom.setCustomCnt(customCnt);
            mbrWineCustom.setCustomTotalAmt(payTotalAmt);
            mbrWineCustomMapper.update(mbrWineCustom);

            // 2、保存订单信息
            MbrWineCustomOrder customOrder = new MbrWineCustomOrder();
            customOrder.setId(mbrWineCustomOrderId);
            customOrder.setWineryId(member.getWineryId());
            customOrder.setMbrId(member.getId());
            customOrder.setCustomTotalCnt(customCnt);
            customOrder.setPayTotalAmt(payTotalAmt);
            customOrder.setPayRealAmt(payTotalAmt);
            customOrder.setOrderStatus(MbrWineCustomOrder.ORDER_STATUS_ENUM.UNPAID.getValue());
            customOrder.setShippingDetailAddr(mbrAddress.getDetailAddress());
            customOrder.setShippingProvinceId(mbrAddress.getProvince());
            customOrder.setShippingCityId(mbrAddress.getCity());
            customOrder.setShippingCountyId(mbrAddress.getCounty());
            customOrder.setOrderNo(orderNo);
            customOrder.setCreateDate(new Date());
            customOrder.setModifyDate(new Date());
            mbrWineCustomOrderMapper.save(customOrder);

            // 3、保存订单记录信息
            MbrWineCustomOrderRecord customOrderRecord = new MbrWineCustomOrderRecord();
            customOrderRecord.setId(IDUtil.getId());
            customOrderRecord.setMbrWineCustomOrderId(mbrWineCustomOrderId);
            customOrderRecord.setOrderStatus(MbrWineCustomOrderRecord.ORDER_STATUS_ENUM.UNPAID.getValue());
            customOrderRecord.setStatusDate(new Date());
            customOrderRecord.setCreateDate(new Date());
            customOrderRecord.setModifyDate(new Date());
            mbrWineCustomOrderRecordMapper.save(customOrderRecord);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            returnMap.put("result", false);
            return returnMap;
        }

        // 如果成功，则返回会员定制ID
        returnMap.put("result", true);
        returnMap.put("orderNo", orderNo);
        returnMap.put("payTotalAmt", payTotalAmt);
        return returnMap;
    }
}

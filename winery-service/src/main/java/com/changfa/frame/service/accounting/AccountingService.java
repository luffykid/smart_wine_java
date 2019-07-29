package com.changfa.frame.service.accounting;

import com.changfa.frame.data.dto.saas.AccountingDTO;
import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.repository.deposit.DepositOrderRepository;
import com.changfa.frame.data.repository.order.OrderRepository;
import com.changfa.frame.data.repository.point.PointToVoucherRepository;
import com.changfa.frame.service.dict.DictService;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountingService {

    private static Logger log = LoggerFactory.getLogger(AccountingService.class);

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PointToVoucherRepository pointToVoucherRepository;

    @Autowired
    private DictService dictService;


    public Map<String, Object> getDepositSum(AdminUser adminUser, String beginTime, String endTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime!=null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        List<Object[]> depositSumList = new ArrayList<>();
        BigDecimal sendVoucherMoney = new BigDecimal(0);
        if (beginTime != null && !beginTime.equals("")) {
            depositSumList = depositOrderRepository.findDepositMoneySumByTime(adminUser.getWineryId(), beginTime, endTime);
            sendVoucherMoney = depositOrderRepository.findSendVoucherMoneyByTime(adminUser.getWineryId(), beginTime, endTime);
        } else {
            depositSumList = depositOrderRepository.findDepositMoneySum(adminUser.getWineryId());
            sendVoucherMoney = depositOrderRepository.findSendVoucherMoney(adminUser.getWineryId());
        }
        Map<String, Object> map = new HashMap<>();
        if (depositSumList != null) {
            for (Object[] depositSum : depositSumList) {
                map.put("depositMoney", depositSum[0] == null ? 0 : depositSum[0]);
                map.put("totalPrice", depositSum[0] == null ? 0 : depositSum[0]);
                map.put("rewardMonye", depositSum[1] == null ? 0 : depositSum[1]);
                map.put("sendVoucherSum", sendVoucherMoney == null ? new BigDecimal(0) : sendVoucherMoney);
            }
        } else {
            map.put("depositMoney", 0);
            map.put("totalPrice", 0);
            map.put("rewardMonye", 0);
            map.put("sendVoucherSum", 0);
        }
        return map;
    }


    public List<AccountingDTO> findDepositDetail(AdminUser adminUser, String beginTime, String endTime, String orderNoLike) throws ParseException {
        List<Object[]> depositDetail = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime!=null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if(beginTime!=null && orderNoLike!=null){
            depositDetail = depositOrderRepository.findDepositDetailByOrderNoAndTime(adminUser.getWineryId(),beginTime,endTime,orderNoLike);
        }
        if (beginTime!=null && orderNoLike==null){
            depositDetail = depositOrderRepository.findDepositDetailByTime(adminUser.getWineryId(),beginTime,endTime);
        }
        if (beginTime==null && orderNoLike!=null){
            depositDetail = depositOrderRepository.findDepositDetailByOrderNo(adminUser.getWineryId(),orderNoLike);
        }
        if (beginTime==null && orderNoLike==null){
            depositDetail = depositOrderRepository.findDepositDetail(adminUser.getWineryId());
        }
        List<AccountingDTO> accountingDTOList = new ArrayList<>();
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (depositDetail != null && depositDetail.size() > 0) {
            for (Object[] objects : depositDetail) {
                AccountingDTO accountingDTO = new AccountingDTO();
                accountingDTO.setOrderNo(objects[0].toString()==null?null:objects[0].toString());
                accountingDTO.setTotalPrice(objects[8]==null?new BigDecimal(objects[2].toString()):new BigDecimal(objects[8].toString()));
                accountingDTO.setDepositMoney(objects[2].toString()==null?new BigDecimal(0):new BigDecimal(objects[2].toString()));
                accountingDTO.setRewardMonye(objects[3] == null ? new BigDecimal(0) : new BigDecimal(objects[3].toString()));
                accountingDTO.setNickName(objects[1] == null? null : EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[1].toString())));
                accountingDTO.setPayType(objects[6]==null?null:objects[6].toString());
                accountingDTO.setCreateTime(objects[7]==null?null:format.format(Timestamp.valueOf(objects[7].toString())));
                accountingDTO.setPoint(objects[5] == null ? 0 : Integer.valueOf(objects[5].toString()));
                accountingDTO.setVoucherName(objects[4] == null ? "无" : objects[4].toString());
                accountingDTO.setCome("第三方");
                accountingDTOList.add(accountingDTO);
            }

        }
        return accountingDTOList;
    }


    public Map findConsumeSum(AdminUser adminUser, String beginTime, String endTime) throws ParseException {
        List<Object[]> orderSum = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime!=null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if(beginTime!=null && !beginTime.equals("")) {
            orderSum = orderRepository.findOrderSumTime(adminUser.getWineryId(), beginTime, endTime);
        }else{
            orderSum = orderRepository.findOrderSum(adminUser.getWineryId());
        }
        Map<String, Object> map = new HashMap<>();
        if (orderSum != null) {
            for (Object[] objects : orderSum) {
                map.put("finalPrice", objects[0] == null ? 0 : objects[0]);
                map.put("totalPrice", objects[1] == null ? 0 : objects[1]);
                map.put("GiftSum", objects[2] == null ? 0 : objects[2]);
                map.put("useDeopsit", objects[3] == null ? 0 : objects[3]);
                map.put("voucherMoney", objects[4] == null ? 0 : objects[4]);
            }
        } else {
            map.put("finalPrice", 0);
            map.put("totalPrice", 0);
            map.put("GiftSum", 0);
            map.put("useDeopsit", 0);
            map.put("voucherMoney", 0);
        }
        return map;
    }


    public List<AccountingDTO> findConsumeList(AdminUser adminUser, String beginTime, String endTime, String orderNoLike) throws ParseException {
        List<Object[]> orderDetailList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime!=null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if(beginTime!=null && orderNoLike!=null){
            orderDetailList = orderRepository.findOrderDetailByTimeAndOrderNo(adminUser.getWineryId(),beginTime,endTime,orderNoLike);
        }
        if (beginTime!=null && orderNoLike==null){
            orderDetailList = orderRepository.findOrderDetailByTime(adminUser.getWineryId(),beginTime,endTime);
        }
        if (beginTime==null && orderNoLike!=null){
            orderDetailList = orderRepository.findOrderDetailByOrderNo(adminUser.getWineryId(),orderNoLike);
        }
        if (beginTime==null && orderNoLike==null){
            orderDetailList = orderRepository.findOrderDetail(adminUser.getWineryId());
        }
        List<AccountingDTO> accountingDTOList = new ArrayList<>();
        if (orderDetailList != null && orderDetailList.size() > 0) {
            for (Object[] objects : orderDetailList) {
                AccountingDTO accountingDTO = new AccountingDTO();
                accountingDTO.setNickName(objects[0] == null ? null : EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[0].toString())));
                accountingDTO.setOrderNo(objects[1] == null ? null : objects[1].toString());
                accountingDTO.setType("消费");
                accountingDTO.setFinalMoney(objects[2] == null ? new BigDecimal(0) : new BigDecimal(objects[2].toString()));
                accountingDTO.setTotalPrice(objects[3] == null ? new BigDecimal(0) : new BigDecimal(objects[3].toString()));
                accountingDTO.setDepositMoney(objects[4] == null ? new BigDecimal(0) : new BigDecimal(objects[4].toString()));
                accountingDTO.setVoucherMoney(objects[5] == null ? new BigDecimal(0) : new BigDecimal(objects[5].toString()));
                accountingDTO.setVoucherName(objects[6] == null ? null : objects[6].toString());
                accountingDTOList.add(accountingDTO);
            }
        }
        return accountingDTOList;
    }


    public List<AccountingDTO> findConsumeDetailList(AdminUser adminUser, String beginTime, String endTime, String orderNoLike,String payType) throws ParseException {
        List<Object[]> orderDetailList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime!=null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (beginTime != null && orderNoLike != null && payType != null) {
            orderDetailList = orderRepository.findOrderProdDetailByTimeAndOrderNoLikeAndType(adminUser.getWineryId(),orderNoLike,beginTime,endTime,payType);
        }
        if (beginTime == null && orderNoLike == null && payType == null) {
            orderDetailList = orderRepository.findOrderProdDetail(adminUser.getWineryId());
        }
        if (beginTime != null && orderNoLike != null && payType == null) {
            orderDetailList = orderRepository.findOrderProdDetailByTimeAndOrderNoLike(adminUser.getWineryId(),orderNoLike,beginTime,endTime);
        }
        if (beginTime != null && payType != null && orderNoLike == null) {
            orderDetailList = orderRepository.findOrderProdDetailByTimeAndType(adminUser.getWineryId(),beginTime,endTime,payType);
        }
        if (payType != null && orderNoLike != null && beginTime == null) {
            orderDetailList = orderRepository.findOrderProdDetailByOrderNoLikeAndType(adminUser.getWineryId(),orderNoLike,payType);
        }
        if (payType == null && orderNoLike == null && beginTime != null) {
            orderDetailList = orderRepository.findOrderProdDetailByTime(adminUser.getWineryId(),beginTime,endTime);
        }
        if (payType == null && orderNoLike != null && beginTime == null) {
            orderDetailList = orderRepository.findOrderProdDetailByOrderNoLike(adminUser.getWineryId(),orderNoLike);
        }
        if (payType != null && orderNoLike == null && beginTime == null) {
            orderDetailList = orderRepository.findOrderProdDetailByType(adminUser.getWineryId(),payType);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<AccountingDTO> accountingDTOList = new ArrayList<>();
        if (orderDetailList != null && orderDetailList.size() > 0) {
            for (Object[] objects : orderDetailList) {
                AccountingDTO accountingDTO = new AccountingDTO();
                accountingDTO.setId(objects[0] == null ? null : Integer.valueOf(objects[0].toString()));
                accountingDTO.setPoint(objects[8] == null ? null : Integer.valueOf(objects[8].toString()));
                accountingDTO.setNickName(objects[1] == null ? null : EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[1].toString())));
                accountingDTO.setOrderNo(objects[2] == null ? null : objects[2].toString());
                accountingDTO.setType("商品售卖");
                accountingDTO.setPayType(objects[5] == null ? null : objects[5].toString());
                accountingDTO.setFinalMoney(objects[3] == null ? new BigDecimal(0) : new BigDecimal(objects[3].toString()));
                accountingDTO.setTotalPrice(objects[4] == null ? new BigDecimal(0) : new BigDecimal(objects[4].toString()));
                accountingDTO.setDepositMoney(objects[6] == null ? new BigDecimal(0) : new BigDecimal(objects[6].toString()));
                accountingDTO.setTime(objects[9] == null ? null : format.format(Timestamp.valueOf(objects[9].toString())));
                accountingDTOList.add(accountingDTO);
            }
        }
        return accountingDTOList;
    }


    public List<String> findOrderProdName(Integer orderId) {
        List<String> name = orderRepository.findOrderProdName(orderId);
        return name;
    }


    public List<AccountingDTO> findDepositList(AdminUser adminUser, String beginTime, String endTime, String orderNo, String phone) throws ParseException {
        List<Object[]> depositList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime!=null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (beginTime != null && orderNo != null && phone != null) {
            depositList = depositOrderRepository.findDepositByTimeAndLikeAndPhone(adminUser.getWineryId(), phone, orderNo, beginTime, endTime);
        }
        if (beginTime == null && orderNo == null && phone == null) {
            depositList = depositOrderRepository.findDeposit(adminUser.getWineryId());
        }
        if (beginTime != null && orderNo != null && phone == null) {
            depositList = depositOrderRepository.findDepositByTimeAndLike(adminUser.getWineryId(), orderNo, beginTime, endTime);
        }
        if (beginTime != null && phone != null && orderNo == null) {
            depositList = depositOrderRepository.findDepositByTimeAndPhone(adminUser.getWineryId(), phone, beginTime, endTime);
        }
        if (phone != null && orderNo != null && beginTime == null) {
            depositList = depositOrderRepository.findDepositByLikeAndPhone(adminUser.getWineryId(), phone, orderNo);
        }
        if (phone == null && orderNo == null && beginTime != null) {
            depositList = depositOrderRepository.findDepositByTime(adminUser.getWineryId(), beginTime, endTime);
        }
        if (phone == null && orderNo != null && beginTime == null) {
            depositList = depositOrderRepository.findDepositByLike(adminUser.getWineryId(), orderNo);
        }
        if (phone != null && orderNo == null && beginTime == null) {
            depositList = depositOrderRepository.findDepositByPhone(adminUser.getWineryId(), phone);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<AccountingDTO> accountingDTOList = new ArrayList<>();
        if (depositList != null && depositList.size() > 0) {
            for (Object[] objects : depositList) {
                AccountingDTO accountingDTO = new AccountingDTO();
                accountingDTO.setNickName(objects[1] == null ? null : EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[1].toString())));
                accountingDTO.setOrderNo(objects[0] == null ? null : objects[0].toString());
                accountingDTO.setType("充值");
                accountingDTO.setTotalPrice(objects[4] == null ? new BigDecimal(objects[2].toString()) : new BigDecimal(objects[4].toString()));
                accountingDTO.setDepositMoney(objects[2] == null ? new BigDecimal(0) : new BigDecimal(objects[2].toString()));
                accountingDTO.setTime(objects[3] == null ? null : format.format(Timestamp.valueOf(objects[3].toString())));
                accountingDTOList.add(accountingDTO);
            }
        }
        return accountingDTOList;
    }


    /* *
     * 积分换礼详情
     * @Author        zyj
     * @Date          2018/11/20 17:17
     * @Description
     * */
    public List<AccountingDTO> pointToGiftDetail(AdminUser adminUser, String beginTime, String endTime, String orderNo, String phone) throws ParseException {
        List<Object[]> depositList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime!=null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (beginTime != null && orderNo != null && phone != null) {
            depositList = pointToVoucherRepository.findByTimeAndOrderNoAndPhone(adminUser.getWineryId(), phone, orderNo, beginTime, endTime);
        }
        if (beginTime == null && orderNo == null && phone == null) {
            depositList = pointToVoucherRepository.findByWineryId(adminUser.getWineryId());
        }
        if (beginTime != null && orderNo != null && phone == null) {
            depositList = pointToVoucherRepository.findByTimeAndOrderNo(adminUser.getWineryId(), orderNo, beginTime, endTime);
        }
        if (beginTime != null && phone != null && orderNo == null) {
            depositList = pointToVoucherRepository.findByTimeAndPhone(adminUser.getWineryId(), phone, beginTime, endTime);
        }
        if (phone != null && orderNo != null && beginTime == null) {
            depositList = pointToVoucherRepository.findByOrderNoAndPhone(adminUser.getWineryId(), phone, orderNo);
        }
        if (phone == null && orderNo == null && beginTime != null) {
            depositList = pointToVoucherRepository.findByTime(adminUser.getWineryId(), beginTime, endTime);
        }
        if (phone == null && orderNo != null && beginTime == null) {
            depositList = pointToVoucherRepository.findByOrderNo(adminUser.getWineryId(), orderNo);
        }
        if (phone != null && orderNo == null && beginTime == null) {
            depositList = pointToVoucherRepository.findByPhone(adminUser.getWineryId(), phone);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<AccountingDTO> accountingDTOList = new ArrayList<>();
        if (depositList != null && depositList.size() > 0) {
            for (Object[] objects : depositList) {
                AccountingDTO accountingDTO = new AccountingDTO();
                accountingDTO.setNickName(objects[1] == null ? null : EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[1].toString())));
                accountingDTO.setOrderNo(objects[0] == null ? null : objects[0].toString());
                accountingDTO.setCreateTime(objects[2] == null ? null : format.format(Timestamp.valueOf(objects[2].toString())));
                accountingDTO.setPoint(objects[3] == null ? 0 : Integer.valueOf(objects[3].toString()));
                accountingDTO.setVoucherName(objects[4] == null ? null : objects[4].toString() + "*1");
                accountingDTO.setName(objects[5] == null ? null : objects[5].toString());
                accountingDTO.setCome("微信");
                accountingDTO.setType("积分换礼");
                accountingDTOList.add(accountingDTO);
            }
        }
        return accountingDTOList;
    }


    public List<String> getPayType(){
        List<Dict> dictList = dictService.findByTableNameAndColumnName("order_pay","pay_type");
        if (dictList!=null && dictList.size()>0){
            List<String> payTypeList = new ArrayList<>();
            for (Dict dict :  dictList){
                payTypeList.add(dict.getStsWords());
            }
            return payTypeList;
        }
        return null;
    }
}

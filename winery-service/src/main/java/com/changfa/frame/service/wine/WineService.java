package com.changfa.frame.service.wine;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.message.SmsTemp;
import com.changfa.frame.data.entity.point.PointRewardRule;
import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.entity.user.ActivationCodeSMS;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.MemberUser;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.wine.*;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import com.changfa.frame.data.repository.message.SmsTempRepository;
import com.changfa.frame.data.repository.point.PointRewardRuleRepository;
import com.changfa.frame.data.repository.point.UserPointRepository;
import com.changfa.frame.data.repository.user.ActivationCodeSMSRepository;
import com.changfa.frame.data.repository.user.AdminUserRepository;
import com.changfa.frame.data.repository.user.MemberUserRepository;
import com.changfa.frame.data.repository.user.UserRepository;
import com.changfa.frame.data.repository.wine.*;
import com.changfa.frame.service.util.SMSUtil;
import com.querydsl.core.util.MathUtils;
import org.apache.commons.lang.StringUtils;
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
public class WineService {

    private static Logger log = LoggerFactory.getLogger(WineService.class);

    @Autowired
    private UserWineDetailRepository userWineDetailRepository;

    @Autowired
    private WineOrderRepository wineOrderRepository;

    @Autowired
    private UserWineRepository userWineRepository;

    @Autowired
    private WineOrderPriceRepository wineOrderPriceRepository;

    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private MemberUserRepository memberUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsTempRepository smsTempRepository;

    @Autowired
    private PointRewardRuleRepository pointRewardRuleRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private ActivationCodeSMSRepository activationCodeSMSRepository;

    public String addWineOrder(AdminUser adminUser, Integer userId, Integer wineId, String type, Integer quantity,BigDecimal storageAmount,BigDecimal donationAmount,String descri, String phone, String code) throws ClientException {
        if(StringUtils.isNotBlank(phone)){
            List<User> userList = userRepository.findUserByPhoneAndWinery(phone, adminUser.getWineryId());
            if(null == userList || userList.size()<=0){
                return "该手机号不是会员，请先添加会员";
            }
            if(null == userId && userList.size()>0){
                userId = userList.get(0).getId();
            }
            wineId = adminUser.getWineryId();

            Date date = new Date();
            System.out.println(date);
            ActivationCodeSMS activationCodeSMS = activationCodeSMSRepository.findByPhoneAndType(phone, "A");
            if (activationCodeSMS == null || !activationCodeSMS.getCode().equals(code)) {
                return "验证码错误";
            } else {
                Date afterDate = new Date(activationCodeSMS.getCreateTime().getTime() + 300000);
                if (new Date().compareTo(afterDate) > 0) {
                    return "验证码失效";
                }
            }
        }

        if (type.equals("C")) {
            UserWine userWine = userWineRepository.findByUserIdAndWineId(userId, wineId);
            if (userWine == null) {
                return "该用户库存不足";
            } else {
                if (userWine.getQuantity() < quantity) {
                    return "该用户库存不足";
                }
            }
        }
        Wine wine = wineRepository.findOne(wineId);
        //酒订单
        WineOrder wineOrder = new WineOrder();
        wineOrder.setWineryId(adminUser.getWineryId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String tempUserId = String.format("%02d", userId);
        String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
        wineOrder.setOrderNo(format);
        wineOrder.setUserId(userId);
        wineOrder.setWineId(wineId);
        wineOrder.setCreateUserId(adminUser.getId());
        wineOrder.setType(type);
        wineOrder.setWineQuantity(quantity);
        wineOrder.setStorageAmount(storageAmount);
        wineOrder.setDonationAmount(donationAmount);
        wineOrder.setTotalPrice(wine.getPrice().multiply(new BigDecimal(quantity)));
        wineOrder.setDescri(descri);
        wineOrder.setCreateTime(new Date());
        //酒订单价格详情
        WineOrderPrice wineOrderPrice = new WineOrderPrice();
        WineOrder wineOrderSave = wineOrderRepository.saveAndFlush(wineOrder);
        wineOrderPrice.setWineOrderId(wineOrderSave.getId());
        wineOrderPrice.setWineOrigPrice(wine.getPrice());
        wineOrderPrice.setFinalPrice(wineOrderSave.getTotalPrice());
        wineOrderPrice.setTotalPrice(wineOrderSave.getTotalPrice());
        wineOrderPriceRepository.saveAndFlush(wineOrderPrice);
        //用户酒库存
        UserWine userWine = userWineRepository.findByUserIdAndWineId(userId, wineId);
        if (userWine == null) {
            userWine = new UserWine();
            userWine.setWineId(wineId);
            userWine.setUserId(userId);
        }
        if (type.equals("D")) {
            userWine.setQuantity(userWine.getQuantity() == null ? 0 + quantity : userWine.getQuantity() + quantity);
        } else {
            userWine.setQuantity(userWine.getQuantity() - quantity);
        }
        userWine.setUpdateTime(new Date());
        UserWine userWineSave = userWineRepository.saveAndFlush(userWine);
        //酒库存变更详情
        UserWineDetail userWineDetail = new UserWineDetail();
        if (type.equals("D")) {
            userWineDetail.setAction("A");
        } else {
            userWineDetail.setAction("M");
        }
        userWineDetail.setLastWine(userWineSave.getQuantity());
        userWineDetail.setOrderNo(wineOrderSave.getOrderNo());
        userWineDetail.setWineId(wineId);
        userWineDetail.setUserId(userId);
        userWineDetail.setWine(quantity);
        userWineDetail.setCreateUserId(adminUser.getId());
        userWineDetail.setWineryId(adminUser.getWineryId());
        userWineDetail.setWineType(type);
        userWineDetail.setCreateTime(new Date());
        userWineDetailRepository.saveAndFlush(userWineDetail);
        // 储酒金额需要按照规则计算积分，但是不参与充值赠送的规则。（storageAmount）
        PointRewardRule rule = pointRewardRuleRepository.findByWineryId(wineId);
        if(null != rule ){
            int pointInt = 0;
            if(rule.getIsLongTime().equals("Y")){
                if(rule.getIsLimit().equals("N")){
                    BigDecimal getPoint = storageAmount.divide(rule.getDepositMoneyPoint());
                    pointInt = getPoint.intValue();
                }else{
                    //获取当前用户今天已经积分兑换的次数
                    Integer counts = wineOrderRepository.orderCountsToday(wineId,userId);
                    if(counts <= rule.getEveryDayLimit()){
                        BigDecimal getPoint = storageAmount.divide(rule.getDepositMoneyPoint());
                        pointInt = getPoint.intValue();
                    }
                }
            }else{
                if(rule.getBeginTime().getTime() <= System.currentTimeMillis()  && System.currentTimeMillis() >= rule.getEndTime().getTime()){
                    if(rule.getIsLimit().equals("N")){
                        BigDecimal getPoint = storageAmount.divide(rule.getDepositMoneyPoint());
                        pointInt = getPoint.intValue();
                    }else{
                        //获取当前用户今天已经积分兑换的次数
                        Integer counts = wineOrderRepository.orderCountsToday(wineId,userId);
                        if(counts <= rule.getEveryDayLimit()){
                            BigDecimal getPoint = storageAmount.divide(rule.getDepositMoneyPoint());
                            pointInt = getPoint.intValue();
                        }
                    }
                }
            }
            UserPoint point = userPointRepository.findByUserId(userId);
            if (point != null) {
                point.setPoint(point.getPoint()+pointInt);
                point.setUpdateTime(new Date());
                userPointRepository.saveAndFlush(point);
            }else{
                UserPoint up = new UserPoint();
                up.setWineryId(wineId);
                up.setUserId(userId);
                up.setPoint(pointInt);
                up.setUpdateTime(new Date());
                userPointRepository.saveAndFlush(up);
            }
        }
        //赠送金额直接存入赠送余额。不参与任何优惠规则(donationAmount)
        UserBalance balance = userBalanceRepository.findByUserId(userId);
        if (balance != null) {
            balance.setDonationAmount(balance.getDonationAmount().add(donationAmount));
            balance.setUpdateTime(new Date());
            userBalanceRepository.saveAndFlush(balance);
        }else{
            balance = new  UserBalance();
            balance.setUserId(userId);
            balance.setBalance(new BigDecimal(0));
            balance.setDonationAmount(donationAmount);
            balance.setWineryId(wineId);
            balance.setUpdateTime(new Date());
            userBalanceRepository.saveAndFlush(balance);
        }

        // 发送短信提醒(储酒)
        User user = userRepository.findOne(userId);
        if (user.getPhone() != null && !user.getPhone().equals("")) {
            SmsTemp smsTemp = smsTempRepository.findByWineryIdAndContentLike(user.getWineryId(), "存取酒");
            if (type.equals("C")) {
                SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + user.getName() + "','wine':'" + wine.getWineName() + "','type':'" + "消费" + "','quantity':'" + quantity + "'}"));
            } else if (type.equals("D")) {
                SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + user.getName() + "','wine':'" + wine.getWineName() + "','type':'" + "储存" + "','quantity':'" + quantity + "'}"));
            }
        }
        return "操作成功";
    }

    /* *
     * 添加酒记录时查询所有酒
     * @Author        zyj
     * @Date          2018/12/25 14:27
     * @Description
     * */
    public List<Map<String, Object>> wineList(Integer userId, Integer wineryId, String type) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (type.equals("D")) {
            List<Wine> wineList = wineRepository.findByWineryIdAndStatus(wineryId, "A");
            if (wineList != null && wineList.size() > 0) {
                for (Wine wine : wineList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("price", wine.getPrice());
                    map.put("name", wine.getWineName());
                    map.put("id", wine.getId());
                    mapList.add(map);
                }
            }
        } else {
            List<UserWine> userWineList = userWineRepository.findByUserId(userId);
            if (userWineList != null && userWineList.size() > 0) {
                for (UserWine userWine : userWineList) {
                    Wine wine = wineRepository.findOne(userWine.getWineId());
                    Map<String, Object> map = new HashMap<>();
                    map.put("price", wine.getPrice());
                    map.put("name", wine.getWineName());
                    map.put("id", wine.getId());
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }


    public List<Map<String, Object>> getWineDetail(Integer userId) throws ParseException {
        List<Object[]> wineDetial = userWineDetailRepository.findByUserId(userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (wineDetial != null && wineDetial.size() > 0) {
            for (Object[] objects : wineDetial) {
                Map<String, Object> map = new HashMap<>();
                map.put("type", objects[0].equals("D") ? "储酒" : "消费");
                map.put("time", format.parse(objects[1].toString()));
                if (objects[7].equals("A")) {
                    map.put("quantity", "+"+objects[2]);
                }else{
                    map.put("quantity", "-"+objects[2]);
                }
                map.put("lastQuantity", objects[3]);
                if (objects[4]!=null) {
                    AdminUser adminUser = adminUserRepository.findOne(Integer.valueOf(objects[4].toString()));
                    map.put("createName", adminUser.getUserName());
                }else{
                    MemberUser memberUser = memberUserRepository.findByUserId(userId);
                    map.put("createName",memberUser.getNickName());
                }
                map.put("createUserName", objects[5]);
                map.put("wineName", objects[6]);
                mapList.add(map);
            }
        }
        return mapList;
    }

    /* *
     * @Author        zyj
     * @Date          2018/12/25 16:47
     * @Description
     * */
    public Map<String, Object> getWineDetailW(Integer userId) throws ParseException {
        Map<String, Object> mapResult = new HashMap<>();
        BigDecimal price = new BigDecimal(0);
        Integer quantity = 0;
        List<UserWine> userWineList = userWineRepository.findByUserId(userId);
        if (userWineList != null && userWineList.size() > 0) {
            for (UserWine userWine : userWineList) {
                quantity += userWine.getQuantity();
                Wine wine = wineRepository.findOne(userWine.getWineId());
                if (userWine.getQuantity() != null && userWine.getQuantity() != 0) {
                    price = price.add(wine.getPrice().multiply(new BigDecimal(userWine.getQuantity())));
                }
            }
        }
        List<Object[]> wineDetial = userWineDetailRepository.findByUserId(userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (wineDetial != null && wineDetial.size() > 0) {
            for (Object[] objects : wineDetial) {
                Map<String, Object> map = new HashMap<>();
                map.put("type", objects[0]);
                map.put("time", format.parse(objects[1].toString()));
                map.put("quantity", objects[2]);
                map.put("wineName", objects[6]);
                mapList.add(map);
            }
        }
        mapResult.put("list", mapList);
        mapResult.put("quantity", quantity);
        mapResult.put("price", price);
        return mapResult;
    }

}

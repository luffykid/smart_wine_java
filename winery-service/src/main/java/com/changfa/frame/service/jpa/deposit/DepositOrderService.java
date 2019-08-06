package com.changfa.frame.service.jpa.deposit;


import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.entity.deposit.*;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.market.MarketActivitySpecDetail;
import com.changfa.frame.data.entity.market.UserMarketActivity;
import com.changfa.frame.data.entity.message.SmsTemp;
import com.changfa.frame.data.entity.offline.OfflineOrder;
import com.changfa.frame.data.entity.offline.OfflineOrderPrice;
import com.changfa.frame.data.entity.order.OrderPay;
import com.changfa.frame.data.entity.user.ActivationCodeSMS;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.activity.UserMarketActivityRepository;
import com.changfa.frame.data.repository.deposit.*;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.market.MarketActivitySpecDetailRepository;
import com.changfa.frame.data.repository.message.SmsTempRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderPriceRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderRepository;
import com.changfa.frame.data.repository.order.OrderPayRepository;
import com.changfa.frame.data.repository.user.ActivationCodeSMSRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import com.changfa.frame.service.jpa.market.MarketActivityService;
import com.changfa.frame.service.jpa.point.PointRewardRuleService;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.service.jpa.util.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DepositOrderService {


    @Autowired
    private DepositOrderRepository depositOrderRepository;

    @Autowired
    private OrderPayRepository orderPayRepository;

    @Autowired
    private UserDepositDetailRepository userDepositDetailRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointRewardRuleService pointRewardRuleService;

    @Autowired
    private MemberWechatRepository memberWechatRepository;

    @Autowired
    private MarketActivityService marketActivityService;

    @Autowired
    private MarketActivitySpecDetailRepository marketActivitySpecDetailRepository;

    @Autowired
    private DepositRuleRepository depositRuleRepository;

    @Autowired
    private DepositRuleSpecDetailRepository depositRuleSpecDetailRepository;

    @Autowired
    private VoucherInstRepository voucherInstRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private UserMarketActivityRepository userMarketActivityRepository;

    @Autowired
    private ActivationCodeSMSRepository activationCodeSMSRepository;

    @Autowired
    private OfflineOrderRepository offlineOrderRepository;

    @Autowired
    private OfflineOrderPriceRepository offlineOrderPriceRepository;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private SmsTempRepository smsTempRepository;

    @Autowired
    private MemberService memberService;

    public DepositOrder addOrder(Map<String, Object> map, Member user) {
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.setUserId(Integer.valueOf(user.getId().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String tempUserId = String.format("%02d", user.getId());
        String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
        depositOrder.setOrderNo(format);
        depositOrder.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        BigDecimal totalPrice = new BigDecimal(String.valueOf(map.get("price")));
        depositOrder.setTotalPrice(totalPrice);
        depositOrder.setFinalPrice(totalPrice);
        depositOrder.setCreateTime(new Date());
        depositOrder.setStatus("B");
        depositOrder.setStatusTime(new Date());
        DepositOrder depositOrderSave = depositOrderRepository.saveAndFlush(depositOrder);
        return depositOrderSave;
    }

    /* *
     * 支付成功
     * @Author        zyj
     * @Date          2018/10/25 10:40
     * @Description
     * */
    public void paySuccess(String orderNo, Map<String, String> map) {
        //添加支付信息
        DepositOrder depositOrder = depositOrderRepository.findByOrderNo(orderNo);
        Member user = memberRepository.getOne(depositOrder.getUserId());
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(orderNo);
        orderPay.setOrderId(depositOrder.getId());
        orderPay.setOrderType("D");
        orderPay.setPayType("W");
        orderPay.setUserId(depositOrder.getUserId());
        orderPay.setTotalPrice(depositOrder.getTotalPrice());
        orderPay.setPlatformOrderNo(map.get("transaction_id"));
        orderPay.setNotifyStatus("A");
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        //获取进行中的营销活动（自定义活动）
        MarketActivity marketActivity = marketActivityService.findActivity(user, marketActivityRepository.findMtByWineryId("自定义", Constant.wineryId));
        BigDecimal depositMoney = new BigDecimal(0);
        BigDecimal sendMoney = new BigDecimal(0);
        Integer sendVoucherId = null;
        Integer sendVoucherQuantity = null;
        if (marketActivity != null) {
            if (marketActivity.getIsLimit().equals("Y")) {
                Integer depositCount = depositOrderRepository.findMarketCount(marketActivity.getId());
                if (depositCount < marketActivity.getLimitUser()) {
                    List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
                    if (marketActivitySpecDetailList != null) {
                        for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                            if (depositOrder.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                                if (marketActivitySpecDetail.getDepositMoney().compareTo(depositMoney) > 0) {
                                    depositMoney = marketActivitySpecDetail.getDepositMoney();
                                    depositOrder.setComeActivityId(marketActivity.getId());
                                    sendMoney = marketActivitySpecDetail.getPresentMoney();
                                    sendVoucherId = marketActivitySpecDetail.getPresentVoucherId();
                                    sendVoucherQuantity = marketActivitySpecDetail.getPresentVoucherQuantity();
                                }
                            }
                        }
                    }
                } else {
                    DepositRule depositRule = depositRuleRepository.findByWineryIdAndStatus(Integer.valueOf(user.getWineryId().toString()), "A");
                    if (depositRule != null) {
                        List<DepositRuleSpecDetail> depositRuleSpecDetailList = depositRuleSpecDetailRepository.findByDepositRuleId(depositRule.getId());
                        if (depositRuleSpecDetailList != null) {
                            for (DepositRuleSpecDetail depositRuleSpecDetail : depositRuleSpecDetailList) {
                                if (depositOrder.getTotalPrice().compareTo(depositRuleSpecDetail.getDepositMoney()) >= 0) {
                                    if (depositRuleSpecDetail.getDepositMoney().compareTo(depositMoney) > 0) {
                                        depositMoney = depositRuleSpecDetail.getDepositMoney();
                                        sendMoney = depositRuleSpecDetail.getPresentMoney();
                                        sendVoucherId = depositRuleSpecDetail.getPresentVoucherId();
                                        sendVoucherQuantity = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
                if (marketActivitySpecDetailList != null) {
                    for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                        if (depositOrder.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            if (marketActivitySpecDetail.getDepositMoney().compareTo(depositMoney) > 0) {
                                depositMoney = marketActivitySpecDetail.getDepositMoney();
                                depositOrder.setComeActivityId(marketActivity.getId());
                                sendMoney = marketActivitySpecDetail.getPresentMoney();
                                sendVoucherId = marketActivitySpecDetail.getPresentVoucherId();
                                sendVoucherQuantity = marketActivitySpecDetail.getPresentVoucherQuantity();
                            }
                        }
                    }
                }
            }
        } else {
            //若没有自定义活动，则找储值活动
            DepositRule depositRule = depositRuleRepository.findByWineryIdAndStatus(Integer.valueOf(user.getWineryId().toString()), "A");
            if (depositRule != null) {
                List<DepositRuleSpecDetail> depositRuleSpecDetailList = depositRuleSpecDetailRepository.findByDepositRuleId(depositRule.getId());
                if (depositRuleSpecDetailList != null) {
                    for (DepositRuleSpecDetail depositRuleSpecDetail : depositRuleSpecDetailList) {
                        if (depositOrder.getTotalPrice().compareTo(depositRuleSpecDetail.getDepositMoney()) >= 0) {
                            if (depositRuleSpecDetail.getDepositMoney().compareTo(depositMoney) > 0) {
                                depositMoney = depositRuleSpecDetail.getDepositMoney();
                                sendMoney = depositRuleSpecDetail.getPresentMoney();
                                sendVoucherId = depositRuleSpecDetail.getPresentVoucherId();
                                sendVoucherQuantity = 1;
                            }
                        }
                    }
                }
            }
        }
        //修改订单状态
        depositOrder.setStatus("P");
        depositOrder.setRewardMoney(sendMoney);
        depositOrder.setStatusTime(new Date());
        depositOrderRepository.saveAndFlush(depositOrder);
        //修改用户储值
        UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        userBalance.setBalance(userBalance.getBalance().add(depositOrder.getTotalPrice()).add(sendMoney));
        userBalance.setUpdateTime(new Date());
        UserBalance updateUserBalance = userBalanceRepository.saveAndFlush(userBalance);
        //增加储值记录
        UserDepositDetail userDepositDetail = new UserDepositDetail();
        userDepositDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userDepositDetail.setUserId(Integer.valueOf(user.getId().toString()));
        userDepositDetail.setAction("D");
        userDepositDetail.setBalance(depositOrder.getTotalPrice());
        userDepositDetail.setBalanceType("A");
        userDepositDetail.setLatestBalance(updateUserBalance.getBalance());
        userDepositDetail.setOrderId(depositOrder.getId());
        userDepositDetail.setOrderNo(depositOrder.getOrderNo());
        userDepositDetail.setCreateTime(new Date());
        userDepositDetailRepository.saveAndFlush(userDepositDetail);
        //赠送券
        if (sendVoucherId != null && sendVoucherQuantity != null) {
            Voucher voucher = voucherRepository.getOne(sendVoucherId);
            if (voucher != null) {
                //流水号
                for (int i = 0; i < sendVoucherQuantity; i++) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                    String tempUserId = String.format("%02d", user.getId());
                    String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);

                    //计算生效时间
                    Calendar caEff = Calendar.getInstance();
                    caEff.add(Calendar.DATE, voucher.getEnableDay());// num为增加的天数，可以改变的
                    Date effectiveTime = caEff.getTime();
                    Date ineffectiveTime = voucher.getIneffectiveTime();
                    if (voucher.getUsefulTime() != null) {
                        Calendar caIne = Calendar.getInstance();
                        caIne.add(Calendar.DATE, voucher.getUsefulTime());
                        ineffectiveTime = caIne.getTime();
                    }
                    //添加券实体
                    VoucherInst voucherInst = new VoucherInst(Integer.valueOf(user.getWineryId().toString()), voucher.getName(), voucher.getId(), format, voucher.getType(), voucher.getScope(), voucher.getCanPresent(), voucher.getMoney(), voucher.getDiscount(), voucher.getExchangeProdId(), voucher.getEnableType(), voucher.getEnableMoeny(), effectiveTime, ineffectiveTime, "A", new Date(), new Date());
                    if (marketActivity != null) {
                        voucherInst.setComActivityType("M");
                        voucherInst.setComeActivityId(marketActivity.getId());
                    }
                    voucherInst.setOrderType("D");
                    voucherInst.setOrderId(depositOrder.getId());
                    VoucherInst voucherInstSave = voucherInstRepository.saveAndFlush(voucherInst);
                    //添加用户券记录
                    UserVoucher userVoucher = new UserVoucher();
                    userVoucher.setUserId(Integer.valueOf(user.getId().toString()));
                    userVoucher.setCreateTime(new Date());
                    userVoucher.setVoucherInstId(voucherInstSave.getId());
                    userVoucher.setSendTime(new Date());
                    userVoucher.setGetTime(new Date());
                    userVoucher.setIneffectiveTime(voucherInstSave.getIneffectiveTime());
                    userVoucherRepository.saveAndFlush(userVoucher);

                    //添加参加营销活动记录
                    if (marketActivity != null) {
                        UserMarketActivity userMarketActivity = new UserMarketActivity();
                        userMarketActivity.setWineryId(Integer.valueOf(user.getWineryId().toString()));
                        userMarketActivity.setUserId(Integer.valueOf(user.getId().toString()));
                        userMarketActivity.setMarketActivityId(marketActivity.getId());
                        userMarketActivity.setSendMoney(sendMoney);
                        userMarketActivity.setSendVoucherId(voucherInstSave.getId());
                        userMarketActivity.setCreateTime(new Date());
                        userMarketActivityRepository.saveAndFlush(userMarketActivity);
                    }
                }
            }
        }
        pointRewardRuleService.updateUserPoint(user, depositOrder.getTotalPrice(), "D", depositOrder.getOrderNo(), depositOrder.getId());
    }


    /* *
     * 后台手动添加储值记录
     * @Author        zyj
     * @Date          2018/12/22 15:00
     * @Description
     * */
    public String addDepositOrder(Integer userId, AdminUser adminUser, BigDecimal money, BigDecimal totalMoney, String phone, String code, String descri) {
       /* TimeZone tz = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(tz);*/
        //校验手机号码在当前酒庄是否是会员 如果不是会员 提示“该手机号不是会员，请先添加会员”
        List<Member> userList = memberRepository.findUserByPhoneAndWinery(phone, adminUser.getWineryId().longValue());
        if(null == userList || userList.size()<=0){
            return "该手机号不是会员，请先添加会员";
        }
        if(null == userId && userList.size()>0){
            userId = Integer.valueOf(userList.get(0).getId().toString());
        }

        Date date = new Date();
        System.out.println(date);
        ActivationCodeSMS activationCodeSMS = activationCodeSMSRepository.findByPhoneAndType(phone, "D");
        if (activationCodeSMS == null || !activationCodeSMS.getCode().equals(code)) {
            return "验证码错误";
        } else {
            Date afterDate = new Date(activationCodeSMS.getCreateTime().getTime() + 300000);
            if (new Date().compareTo(afterDate) > 0) {
                return "验证码失效";
            }
        }

        DepositOrder depositOrder = new DepositOrder();
        depositOrder.setUserId(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String tempUserId = String.format("%02d", userId);
        String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
        depositOrder.setOrderNo(format);
        depositOrder.setWineryId(adminUser.getWineryId());
        depositOrder.setTotalPrice(money);
        depositOrder.setCreateTime(new Date());
        depositOrder.setStatus("B");
        depositOrder.setFinalPrice(totalMoney);
        depositOrder.setStatusTime(new Date());
        depositOrder.setCreateUserId(adminUser.getId());
        depositOrder.setDescri(descri);
        DepositOrder depositOrderSave = depositOrderRepository.saveAndFlush(depositOrder);
        //添加支付信息
        Member user = memberRepository.getOne(depositOrderSave.getUserId());
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(depositOrderSave.getOrderNo());
        orderPay.setOrderId(depositOrderSave.getId());
        orderPay.setOrderType("D");
        orderPay.setPayType("O");
        orderPay.setUserId(depositOrderSave.getUserId());
        orderPay.setTotalPrice(totalMoney);
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        //获取进行中的营销活动（自定义活动）
        MarketActivity marketActivity = marketActivityService.findActivity(user, marketActivityRepository.findMtByWineryId("自定义", Constant.wineryId));
        BigDecimal depositMoney = new BigDecimal(0);
        BigDecimal sendMoney = new BigDecimal(0);
        Integer sendVoucherId = null;
        Integer sendVoucherQuantity = null;
        if (marketActivity != null) {
            List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
            if (marketActivitySpecDetailList != null) {
                for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                    if (depositOrderSave.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                        if (marketActivitySpecDetail.getDepositMoney().compareTo(depositMoney) > 0) {
                            depositMoney = marketActivitySpecDetail.getDepositMoney();
                            depositOrderSave.setComeActivityId(marketActivity.getId());
                            sendMoney = marketActivitySpecDetail.getPresentMoney();
                            sendVoucherId = marketActivitySpecDetail.getPresentVoucherId();
                            sendVoucherQuantity = marketActivitySpecDetail.getPresentVoucherQuantity();
                        }
                    }
                }
            }

        } else {
            //若没有自定义活动，则找储值活动
            DepositRule depositRule = depositRuleRepository.findByWineryIdAndStatus(Integer.valueOf(user.getWineryId().toString()), "A");
            if (depositRule != null) {
                List<DepositRuleSpecDetail> depositRuleSpecDetailList = depositRuleSpecDetailRepository.findByDepositRuleId(depositRule.getId());
                if (depositRuleSpecDetailList != null) {
                    for (DepositRuleSpecDetail depositRuleSpecDetail : depositRuleSpecDetailList) {
                        if (depositOrderSave.getTotalPrice().compareTo(depositRuleSpecDetail.getDepositMoney()) >= 0) {
                            if (depositRuleSpecDetail.getDepositMoney().compareTo(depositMoney) > 0) {
                                depositMoney = depositRuleSpecDetail.getDepositMoney();
                                sendMoney = depositRuleSpecDetail.getPresentMoney();
                                sendVoucherId = depositRuleSpecDetail.getPresentVoucherId();
                                sendVoucherQuantity = 1;
                            }
                        }
                    }
                }
            }
        }
        //修改订单状态
        depositOrderSave.setStatus("P");
        depositOrderSave.setRewardMoney(sendMoney);
        depositOrderSave.setStatusTime(new Date());
        depositOrderRepository.saveAndFlush(depositOrderSave);
        //修改用户储值
        UserBalance userBalance = userBalanceRepository.findByUserId(userId);
        userBalance.setBalance((userBalance.getBalance() == null ? new BigDecimal(0) : userBalance.getBalance()).add(depositOrderSave.getTotalPrice()));
        if (depositOrderSave.getRewardMoney() != null) {
            userBalance.setBalance((userBalance.getBalance() == null ? new BigDecimal(0) : userBalance.getBalance()).add(depositOrderSave.getRewardMoney()));
        }
        userBalance.setUpdateTime(new Date());
        UserBalance updateUserBalance = userBalanceRepository.saveAndFlush(userBalance);
        //增加储值记录
        UserDepositDetail userDepositDetail = new UserDepositDetail();
        userDepositDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userDepositDetail.setUserId(Integer.valueOf(user.getId().toString()));
        userDepositDetail.setAction("D");
        userDepositDetail.setBalance(depositOrderSave.getTotalPrice());
        userDepositDetail.setBalanceType("A");
        userDepositDetail.setLatestBalance(updateUserBalance.getBalance());
        userDepositDetail.setOrderId(depositOrderSave.getId());
        userDepositDetail.setOrderNo(depositOrderSave.getOrderNo());
        userDepositDetail.setCreateTime(new Date());
        userDepositDetailRepository.saveAndFlush(userDepositDetail);
        //赠送券
        if (sendVoucherId != null && sendVoucherQuantity != null) {
            Voucher voucher = voucherRepository.getOne(sendVoucherId);
            if (voucher != null) {
                //流水号
                for (int i = 0; i < sendVoucherQuantity; i++) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                    String temp = String.format("%02d", user.getId());
                    String formatt = dateFormat.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + temp.substring(temp.length() - 2);

                    //计算生效时间
                    Calendar caEff = Calendar.getInstance();
                    caEff.add(Calendar.DATE, voucher.getEnableDay());// num为增加的天数，可以改变的
                    Date effectiveTime = caEff.getTime();
                    Date ineffectiveTime = voucher.getIneffectiveTime();
                    if (voucher.getUsefulTime() != null) {
                        Calendar caIne = Calendar.getInstance();
                        caIne.add(Calendar.DATE, voucher.getUsefulTime());
                        ineffectiveTime = caIne.getTime();
                    }
                    //添加券实体
                    VoucherInst voucherInst = new VoucherInst(Integer.valueOf(user.getWineryId().toString()), voucher.getName(), voucher.getId(), formatt, voucher.getType(), voucher.getScope(), voucher.getCanPresent(), voucher.getMoney(), voucher.getDiscount(), voucher.getExchangeProdId(), voucher.getEnableType(), voucher.getEnableMoeny(), effectiveTime, ineffectiveTime, "A", new Date(), new Date());
                    if (marketActivity != null) {
                        voucherInst.setComActivityType("M");
                        voucherInst.setComeActivityId(marketActivity.getId());
                    }
                    voucherInst.setOrderType("D");
                    voucherInst.setOrderId(depositOrderSave.getId());
                    VoucherInst voucherInstSave = voucherInstRepository.saveAndFlush(voucherInst);
                    //添加用户券记录
                    UserVoucher userVoucher = new UserVoucher();
                    userVoucher.setUserId(Integer.valueOf(user.getId().toString()));
                    userVoucher.setCreateTime(new Date());
                    userVoucher.setVoucherInstId(voucherInstSave.getId());
                    userVoucher.setSendTime(new Date());
                    userVoucher.setGetTime(new Date());
                    userVoucher.setIneffectiveTime(voucherInstSave.getIneffectiveTime());
                    userVoucherRepository.saveAndFlush(userVoucher);

                    //添加参加营销活动记录
                    if (marketActivity != null) {
                        UserMarketActivity userMarketActivity = new UserMarketActivity();
                        userMarketActivity.setWineryId(Integer.valueOf(user.getWineryId().toString()));
                        userMarketActivity.setUserId(Integer.valueOf(user.getId().toString()));
                        userMarketActivity.setMarketActivityId(marketActivity.getId());
                        userMarketActivity.setSendMoney(sendMoney);
                        userMarketActivity.setSendVoucherId(voucherInstSave.getId());
                        userMarketActivity.setCreateTime(new Date());
                        userMarketActivityRepository.saveAndFlush(userMarketActivity);
                    }
                }
            }
        }
        pointRewardRuleService.updateUserPoint(user, depositOrderSave.getTotalPrice(), "D", depositOrderSave.getOrderNo(), depositOrderSave.getId());
        activationCodeSMS.setStatus("U");
        activationCodeSMSRepository.saveAndFlush(activationCodeSMS);

        // 发送短信提醒
        if (user.getPhone() != null && !user.getPhone().equals("")) {
            SmsTemp smsTemp = smsTempRepository.findByWineryIdAndContentLike(Integer.valueOf(user.getWineryId().toString()),"储值");
            try {
                SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + user.getNickName() + "','money':'" + money + "'}"));
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return "充值成功";
    }


    public String addConsumeOrder(Integer userId, AdminUser adminUser, BigDecimal money, BigDecimal totalMoney, String phone, String code, String consumeType, String descri) throws ClientException {
        List<Member> userList = memberRepository.findUserByPhoneAndWinery(phone, adminUser.getWineryId().longValue());
        if(null == userList || userList.size()<=0){
            return "该手机号不是会员，请先添加会员";
        }
        if(null == userId && userList.size()>0){
            userId = Integer.valueOf(userList.get(0).getId().toString());
        }
        ActivationCodeSMS activationCodeSMS = activationCodeSMSRepository.findByPhoneAndType(phone, "S");
        if (activationCodeSMS == null || !activationCodeSMS.getCode().equals(code)) {
            return "验证码错误";
        } else {
            Date afterDate = new Date(activationCodeSMS.getCreateTime().getTime() + 300000);
            if (new Date().compareTo(afterDate) > 0) {
                return "验证码失效";
            }
        }
        UserBalance userBalance = userBalanceRepository.findByUserId(userId);
        if (userBalance.getBalance().compareTo(money) < 0) {
            return "余额不足";
        }
        OfflineOrder offlineOrder = new OfflineOrder();
        //生成订单
        offlineOrder.setWineryId(adminUser.getWineryId());
        offlineOrder.setUserId(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String tempUserId = String.format("%02d", userId);
        String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
        offlineOrder.setOrderNo(format);
        offlineOrder.setPayType("O");
        offlineOrder.setStatus("B");
        offlineOrder.setCreateUserId(adminUser.getId());
        offlineOrder.setOrderType("S");
        offlineOrder.setTotalPrice(money);
        offlineOrder.setStatusTime(new Date());
        offlineOrder.setCreateTime(new Date());
        offlineOrder.setConsumeType(consumeType); // 消费方式 1-菜品 2-酒品
        offlineOrder.setDescri(descri); // 备注
        OfflineOrder offlineOrderSave = offlineOrderRepository.saveAndFlush(offlineOrder);
        //添加订单价格详情记录
        OfflineOrderPrice offlineOrderPrice = new OfflineOrderPrice();
        offlineOrderPrice.setOfflineOrderId(offlineOrderSave.getId());
        offlineOrderPrice.setOrigPrice(money);
        offlineOrderPrice.setFinalPrice(money);
        offlineOrderPrice.setTotalPrice(totalMoney);
        offlineOrderPriceRepository.saveAndFlush(offlineOrderPrice);
        offlineOrderSave.setStatus("P");
        offlineOrderSave.setStatusTime(new Date());
        offlineOrderRepository.saveAndFlush(offlineOrderSave);
        //修改储值金额
        userBalance.setBalance(userBalance.getBalance().subtract(offlineOrderSave.getTotalPrice()));
        userBalance.setUpdateTime(new Date());
        UserBalance userBalanceSave = userBalanceRepository.saveAndFlush(userBalance);
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(offlineOrderSave.getOrderNo());
        orderPay.setTotalPrice(offlineOrderSave.getTotalPrice());
        orderPay.setOrderId(offlineOrderSave.getId());
        orderPay.setOrderType("F");
        orderPay.setPayType("D");
        orderPay.setUserId(offlineOrderSave.getUserId());
        orderPay.setTotalPrice(offlineOrderSave.getTotalPrice());
        orderPay.setNotifyStatus("A");
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        //添加储值流水
        UserDepositDetail userDepositDetail = new UserDepositDetail();
        userDepositDetail.setWineryId(adminUser.getWineryId());
        userDepositDetail.setUserId(userId);
        userDepositDetail.setAction("A");
        userDepositDetail.setBalance(offlineOrderSave.getTotalPrice());
        userDepositDetail.setBalanceType("M");
        userDepositDetail.setLatestBalance(userBalanceSave.getBalance());
        userDepositDetail.setOrderId(offlineOrderSave.getId());
        userDepositDetail.setOrderNo(offlineOrderSave.getOrderNo());
        userDepositDetail.setCreateTime(new Date());
        userDepositDetailRepository.saveAndFlush(userDepositDetail);
        Member user = memberRepository.getOne(userId);
        pointRewardRuleService.updateUserPoint(user, offlineOrderSave.getTotalPrice(), "W", offlineOrderSave.getOrderNo(), offlineOrderSave.getId());
        MemberWechat memberUser = memberWechatRepository.findByMbrId(userId);
        //满额赠券
        List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("满额", offlineOrder.getWineryId());
        for (MarketActivity newUserActivity : activityList) {
            if (newUserActivity != null) {
                MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                    if (newUserActivity.getSendSetting() != null && newUserActivity.getSendSetting().equals("O")) {
                        if (user != null && marketActivitySpecDetail.getDepositMoney() != null && offlineOrder.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                        }
                    } else {
                        if (user != null && marketActivitySpecDetail.getDepositMoney() != null && offlineOrder.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            Integer count = offlineOrder.getTotalPrice().divide(marketActivitySpecDetail.getDepositMoney(), 0, BigDecimal.ROUND_HALF_DOWN ).intValue();
                            for (int i = 0; i < count; i++) {
                                marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                            }
                        }
                    }

                }
            }
        }
        //累计消费增券
        BigDecimal decimal = orderPayRepository.findSumByUserId(offlineOrder.getUserId());
        List<MarketActivity> activityListm = marketActivityRepository.findByStatusAndMarketActivityTypeLike("累计", offlineOrder.getWineryId());
        for (MarketActivity newUserActivity : activityListm) {
            if (newUserActivity != null) {
                MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                    if (user != null && marketActivitySpecDetail.getDepositMoney() != null && decimal.compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                        marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "O");
                    }
                }
            }
        }
        activationCodeSMS.setStatus("U");
        activationCodeSMSRepository.saveAndFlush(activationCodeSMS);
        return "成功";
    }
}


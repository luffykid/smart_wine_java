package com.changfa.frame.service.order;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.data.dto.wechat.DiscountListDTO;
import com.changfa.frame.data.dto.wechat.NewProdListDTO;
import com.changfa.frame.data.dto.wechat.OrderListDTO;
import com.changfa.frame.data.dto.wechat.OrdersDTO;
import com.changfa.frame.data.entity.activity.Area;
import com.changfa.frame.data.entity.cart.Cart;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.deposit.UserDepositDetail;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.market.MarketActivitySpecDetail;
import com.changfa.frame.data.entity.message.SmsTemp;
import com.changfa.frame.data.entity.order.*;
import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.entity.prod.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.entity.user.UserAddress;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.activity.AreaRepository;
import com.changfa.frame.data.repository.cart.CartRepository;
import com.changfa.frame.data.repository.common.DictRepository;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import com.changfa.frame.data.repository.deposit.UserDepositDetailRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.market.MarketActivitySpecDetailRepository;
import com.changfa.frame.data.repository.message.SmsTempRepository;
import com.changfa.frame.data.repository.order.*;
import com.changfa.frame.data.repository.point.UserPointRepository;
import com.changfa.frame.data.repository.prod.*;
import com.changfa.frame.data.repository.user.AdminUserRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.data.repository.user.UserAddressRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.service.PicturePathUntil;
import com.changfa.frame.service.dict.DictService;
import com.changfa.frame.service.market.MarketActivityService;
import com.changfa.frame.service.point.PointRewardRuleService;
import com.changfa.frame.service.theme.ThemeService;
import com.changfa.frame.service.util.SMSUtil;
import com.changfa.frame.service.util.kuaidi100.ExpressQuery;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/11/9.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProdPriceRepository prodPriceRepository;
    @Autowired
    private OrderProdRepository orderProdRepository;
    @Autowired
    private OrderPriceRepository orderPriceRepository;
    @Autowired
    private OrderPriceItemRepository orderPriceItemRepository;
    @Autowired
    private OrderExpressRepository orderExpressRepository;
    @Autowired
    private OrderAddressRepository orderAddressRepository;
    @Autowired
    private OrderSettleRepository orderSettleRepository;
    @Autowired
    private OrderPayRepository orderPayRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private DictService dictService;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private ProdLogoRepository prodLogoRepository;
    @Autowired
    private ProdSpecRepository prodSpecRepository;
    @Autowired
    private ProdProdSpecRepository prodProdSpecRepository;
    @Autowired
    private VoucherInstRepository voucherInstRepository;
    @Autowired
    private UserVoucherRepository userVoucherRepository;
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private ProdChangedRepository prodChangedRepository;
    @Autowired
    private PointRewardRuleService pointRewardRuleService;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private MarketActivityRepository marketActivityRepository;
    @Autowired
    private MemberWechatRepository memberWechatRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MarketActivityService marketActivityService;
    @Autowired
    private ProdPriceLevelRepository prodPriceLevelRepository;
    @Autowired
    private ProdStockRepository prodStockRepository;
    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserDepositDetailRepository userDepositDetailRepository;
    @Autowired
    private DictRepository dictRepository;
    @Autowired
    private MarketActivitySpecDetailRepository marketActivitySpecDetailRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private SmsTempRepository smsTempRepository;



    //添加订单(购物车)
    public String addOrderByCart(Member user, UserAddress userAddress, UserVoucher userVoucher, List<Cart> carts, String descri) {
        Order order = new Order();
        order.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        order.setUserId(Integer.valueOf(user.getId().toString()));
        //订单号
        String format = getOrderNoByMethod(user);
        order.setOrderNo(format);
        order.setStatus("P");
        order.setStatusTime(new Date());
        order.setCreateTime(new Date());
        order.setDescri(descri);
        order.setOrderOri(0);//订单来源（0普通订单，1拼团订单，2砍价订单）
        if (userAddress == null) {
            order.setType("T");
        } else {
            order.setType("E");
        }
        orderRepository.saveAndFlush(order);
        //订单价格
        OrderPrice orderPrice = new OrderPrice();
        orderPrice.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        orderPrice.setOrderId(order.getId());
        orderPrice.setCarriageExpense(new BigDecimal(0));
        orderPriceRepository.saveAndFlush(orderPrice);
        Double totalPrice = 0.0;
        Integer totalPoint = 0;
        Integer prodNum = 0;
        for (Cart cart : carts) {
            //订单商品
            OrderProd orderProd = new OrderProd();
            orderProd.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            orderProd.setOrderId(order.getId());
            orderProd.setProdId(cart.getProdId());
            orderProd.setQuantity(cart.getAmount());
            orderProdRepository.saveAndFlush(orderProd);
            ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(cart.getProdId());
            if (prodPrice != null) {
                Prod prod = prodRepository.getOne(prodPrice.getProdId());
                double oneprice = 0;
                if (prod.getMemberDiscount().equals("Y")) {
                    MemberWechat user1 = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                    if (user1 != null) {
                        ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevel());
                        if (levelList != null) {
//                            oneprice = prodPrice.getFinalPrice().doubleValue() * levelList.getDiscount().doubleValue();
                            // TODO 如果商品存在折扣，则价格为原价，否则为优惠价
                            if (levelList.getDiscount() != null && !levelList.getDiscount().equals("")) {
                                oneprice = prodPrice.getOriginalPrice().doubleValue();
                            } else {
                                oneprice = levelList.getDiscountPrice().doubleValue();
                            }
                        } else {
                            oneprice = prodPrice.getFinalPrice().doubleValue() * 1;
                        }
                    }
                } else if (prod.getMemberDiscount().equals("P")) {
                    ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                    if (changed != null) {
                        totalPoint += changed.getUsePoint() * cart.getAmount();
                        oneprice = changed.getPrice().doubleValue();
                    }
                } else {
                    oneprice = prodPrice.getFinalPrice().doubleValue();
                }
                //订单价格项
                OrderPriceItem orderPriceItem = new OrderPriceItem();
                orderPriceItem.setWineryId(Integer.valueOf(user.getWineryId().toString()));
                orderPriceItem.setOrderPriceId(orderPrice.getId());
                orderPriceItem.setOrderId(order.getId());
                orderPriceItem.setProdId(cart.getProdId());
                orderPriceItem.setQuantity(cart.getAmount());
                orderPriceItem.setOrigUnitPrice(prodPrice.getOriginalPrice());
                orderPriceItem.setOrigTotalPrice(prodPrice.getOriginalPrice().multiply(new BigDecimal(cart.getAmount())));
                orderPriceItem.setFinalUnitPrice(new BigDecimal(oneprice));
                orderPriceItem.setFinalTotalPrice(new BigDecimal(oneprice).multiply(new BigDecimal(cart.getAmount())));
                orderPriceItemRepository.saveAndFlush(orderPriceItem);
                totalPrice += orderPriceItem.getFinalTotalPrice().doubleValue();
                prodNum += cart.getAmount();
                ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(cart.getProdId());
                if (prodSpec != null) {
                    orderPriceItem.setProdSpecId(prodSpec.getProdSpecId());
                    orderProd.setProdSpecId(prodSpec.getProdSpecId());
                }
            }
        }
        orderPrice.setProdOrigPrice(new BigDecimal(totalPrice));
        if (userVoucher != null && userVoucher.getUseTime() == null) {
            //订单结算
            OrderSettle orderSettle = new OrderSettle();
            orderSettle.setOrderId(order.getId());
            orderSettle.setUserId(Integer.valueOf(user.getId().toString()));
            orderSettle.setUseVoucherId(userVoucher.getId());
            if (totalPoint != 0) {
                orderSettle.setUsePoint(totalPoint);
            }
            orderSettle.setCreateTime(new Timestamp(System.currentTimeMillis()));
            orderSettleRepository.saveAndFlush(orderSettle);
            VoucherInst inst = voucherInstRepository.getOne(userVoucher.getVoucherInstId());
            if (inst != null && inst.getType().equals("M")) {
                totalPrice -= inst.getMoney().doubleValue();
            } else if (inst != null && inst.getType().equals("D")) {
                totalPrice *= (inst.getDiscount().doubleValue() / 100);
            } else if (inst != null && inst.getType().equals("G")) {
                for (Cart cart : carts) {
                    if (cart.getProdId() == inst.getExchangeProdId()) {
                        ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(inst.getExchangeProdId());
                        Double price1 = 0.0;
                        if (prodPrice != null) {
                            price1 = prodPrice.getFinalPrice().doubleValue();
                        }
                        Prod prod = prodRepository.getOne(cart.getProdId());
                        if (prod != null && prod.getMemberDiscount().equals("Y")) {
                            MemberWechat user1 = memberWechatRepository.findByMbrId(Integer.valueOf(user.getWineryId().toString()));
                            if (user1 != null) {
                                ProdPriceLevel level = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevel());
                                if (level != null) {
                                    if (level != null) {
                                        // TODO 如果商品存在折扣，则价格为原价，否则为优惠价
                                        if (level.getDiscount() != null && !level.getDiscount().equals("")) {
                                            price1 = prodPrice.getOriginalPrice().doubleValue();
                                        } else {
                                            price1 = level.getDiscountPrice().doubleValue();
                                        }
//                                        price1 = prodPrice.getFinalPrice().doubleValue() * level.getDiscount().doubleValue();
                                    } else {
                                        price1 = prodPrice.getFinalPrice().doubleValue() * 1;
                                    }
                                }
                            }
                        }
                        totalPrice -= price1;
                    }
                }
            }
            //作废券
            userVoucher.setUseTime(new Date());
            userVoucherRepository.save(userVoucher);
            inst.setStatus("U");
            inst.setStatusTime(new Date());
            inst.setOrderId(order.getId());
            inst.setOrderType("O");
            voucherInstRepository.save(inst);
        }
        orderPrice.setProdFinalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
        orderPrice.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
        order.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
        order.setProdNum(prodNum);
        //订单地址
        if (userAddress != null) {
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setOrderId(order.getId());
            orderAddress.setWineryId(order.getWineryId());
            orderAddress.setUserAddressId(userAddress.getId());
            orderAddress.setCreateTime(new Timestamp(System.currentTimeMillis()));
            orderAddressRepository.saveAndFlush(orderAddress);
        }
        for (Cart cart : carts) {
            cartRepository.delete(cart);
        }
        return order.getOrderNo();
    }

    public String getOrderNoByMethod(Member user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String tempUserId = String.format("%02d", user.getId());
        String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
        return format;
    }

    public String addOrderByOne(Member user, UserAddress userAddress, UserVoucher userVoucher, Integer prodId, Integer amount, String descri) {
        Order order = new Order();
        order.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        order.setUserId(Integer.valueOf(user.getId().toString()));
        //订单号
        String format = getOrderNoByMethod(user);
        order.setOrderNo(format);
        order.setStatus("P");
        order.setStatusTime(new Date());
        order.setCreateTime(new Date());
        order.setDescri(descri);
        order.setProdNum(amount);
        order.setOrderOri(0);//订单来源（0普通订单，1拼团订单，2砍价订单）
        if (userAddress == null) {
            order.setType("T");
        } else {
            order.setType("E");
        }
        orderRepository.saveAndFlush(order);
        ProdPrice price = prodPriceRepository.findProdPriceByProdId(prodId);
        Double totalPrice = 0.0;
        Integer totalPoint = 0;
        if (price != null) {
            Prod prod = prodRepository.getOne(prodId);
            if (prod.getMemberDiscount().equals("Y")) {
                MemberWechat user1 = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                if (user1 != null) {
                    ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevel());
                    if (levelList != null) {
                        // TODO 如果商品存在折扣，则价格为原价，否则为优惠价
                        if (levelList.getDiscount() != null && !levelList.getDiscount().equals("")) {
                            totalPrice = price.getOriginalPrice().doubleValue();
                        } else {
                            totalPrice = levelList.getDiscountPrice().doubleValue() * amount;
                        }
//                        totalPrice = price.getFinalPrice().doubleValue() * levelList.getDiscount().doubleValue() * amount;
                    } else {
                        totalPrice = price.getFinalPrice().doubleValue() * amount;
                    }
                }
            } else if (prod.getMemberDiscount().equals("P")) {
                ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                if (changed != null) {
                    totalPoint += changed.getUsePoint() * amount;
                    totalPrice = changed.getPrice().doubleValue() * amount;
                }
            } else {
                totalPrice = price.getFinalPrice().doubleValue() * amount;
            }
        }

        OrderSettle orderSettle = new OrderSettle();
        orderSettle.setOrderId(order.getId());
        orderSettle.setUserId(Integer.valueOf(user.getId().toString()));
        if (totalPoint != 0) {
            orderSettle.setUsePoint(totalPoint);
        }
        orderSettle.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if (userVoucher != null && userVoucher.getUseTime() == null) {
            orderSettle.setUseVoucherId(userVoucher.getId());
            VoucherInst inst = voucherInstRepository.getOne(userVoucher.getVoucherInstId());
            if (inst != null && inst.getType().equals("M")) {
                totalPrice -= inst.getMoney().doubleValue();
            } else if (inst != null && inst.getType().equals("D")) {
                totalPrice *= (inst.getDiscount().doubleValue() / 100);
            } else if (inst != null && inst.getType().equals("G")) {
                if (prodId == inst.getExchangeProdId()) {
                    ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(inst.getExchangeProdId());
                    Double price1 = 0.0;
                    if (prodPrice != null) {
                        price1 = prodPrice.getFinalPrice().doubleValue();
                    }
                    Prod prod = prodRepository.getOne(prodId);
                    if (prod != null && prod.getMemberDiscount().equals("Y")) {
                        MemberWechat user1 = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                        if (user1 != null) {
                            ProdPriceLevel level = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevel());
                            if (level != null) {
                                if (level != null) {
                                    // TODO 如果商品存在折扣，则价格为原价，否则为优惠价
                                    if (level.getDiscount() != null && !level.getDiscount().equals("")) {
                                        price1 = prodPrice.getOriginalPrice().doubleValue();
                                    } else {
                                        price1 = level.getDiscountPrice().doubleValue();
                                    }
//                                    price1 = prodPrice.getFinalPrice().doubleValue() * level.getDiscount().doubleValue();
                                } else {
                                    price1 = prodPrice.getFinalPrice().doubleValue() * 1;
                                }
                            }
                        }
                    }
                    totalPrice -= price1;
                }
            }
            //作废券
            userVoucher.setUseTime(new Date());
            userVoucherRepository.save(userVoucher);
            inst.setStatus("U");
            inst.setStatusTime(new Date());
            inst.setOrderId(order.getId());
            inst.setOrderType("O");
            voucherInstRepository.save(inst);
        }
        orderSettleRepository.saveAndFlush(orderSettle);
        order.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
        OrderProd orderProd = new OrderProd();
        orderProd.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        orderProd.setOrderId(order.getId());
        orderProd.setProdId(prodId);
        orderProd.setQuantity(amount);
        orderProdRepository.saveAndFlush(orderProd);
        ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(prodId);
        if (prodPrice != null) {
            OrderPrice orderPrice = new OrderPrice();
            orderPrice.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            orderPrice.setOrderId(order.getId());
            orderPrice.setProdOrigPrice(prodPrice.getOriginalPrice().multiply(new BigDecimal(amount)));
            orderPrice.setProdFinalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
            orderPrice.setCarriageExpense(new BigDecimal(0));
            orderPrice.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
            orderPriceRepository.saveAndFlush(orderPrice);
            OrderPriceItem orderPriceItem = new OrderPriceItem();
            orderPriceItem.setOrderPriceId(orderPrice.getId());
            orderPriceItem.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            orderPriceItem.setOrderId(order.getId());
            orderPriceItem.setProdId(prodId);
            orderPriceItem.setQuantity(amount);
            orderPriceItem.setOrigUnitPrice(prodPrice.getOriginalPrice());
            orderPriceItem.setOrigTotalPrice(prodPrice.getOriginalPrice().multiply(new BigDecimal(amount)));
            orderPriceItem.setFinalUnitPrice(new BigDecimal(totalPrice));
            orderPriceItem.setFinalTotalPrice(new BigDecimal(totalPrice).multiply(new BigDecimal(amount)));
            orderPriceItemRepository.saveAndFlush(orderPriceItem);
            ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prodId);
            if (prodSpec != null) {
                orderPriceItem.setProdSpecId(prodSpec.getProdSpecId());
                orderProd.setProdSpecId(prodSpec.getProdSpecId());
            }
        }
        if (userAddress != null) {
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setOrderId(order.getId());
            orderAddress.setWineryId(order.getWineryId());
            orderAddress.setUserAddressId(userAddress.getId());
            orderAddress.setCreateTime(new Timestamp(System.currentTimeMillis()));
            orderAddressRepository.saveAndFlush(orderAddress);
        }
        return order.getOrderNo();
    }

    public Order findByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo);
    }

    public void paySuccess(String orderNo, String type, Map<String, String> map) throws ClientException {
        Order order = orderRepository.findByOrderNo(orderNo);
        //支付成功减库存
        List<OrderProd> list = orderProdRepository.findByOrderId(order.getId());
        for (OrderProd orderProd : list) {
            ProdStock stock = prodStockRepository.findByProdId(orderProd.getProdId());
            if (stock != null) {
                stock.setProdStock(stock.getProdStock() - orderProd.getQuantity());
            }
            prodStockRepository.save(stock);
        }
        OrderSettle settle = orderSettleRepository.findByOrderId(order.getId());
        if (order.getTotalPrice().compareTo(new BigDecimal(0)) > 0) {
            //支付成功减用户积分
            Member userRepositoryOne = memberRepository.getOne(order.getUserId());
            if (userRepositoryOne != null && settle != null) {
                pointRewardRuleService.addUserPoint(userRepositoryOne, settle.getUsePoint(), "S", order.getOrderNo(), order.getId());
            }
            //消费多少送一积分
            pointRewardRuleService.updateUserPoint(userRepositoryOne, order.getTotalPrice(), "W", order.getOrderNo(), order.getId());
        }
        if (settle != null && settle.getUseVoucherId() != null) {
            UserVoucher voucher = userVoucherRepository.getOne(settle.getUseVoucherId());
            if (voucher != null && voucher.getUseTime() == null) {
                VoucherInst inst = voucherInstRepository.getOne(voucher.getVoucherInstId());
                if (inst != null) {
                    //作废券
                    inst.setStatus("U");
                    inst.setStatusTime(new Date());
                    inst.setOrderId(order.getId());
                    inst.setOrderType("O");
                    voucherInstRepository.save(inst);
                    voucher.setUseTime(new Date());
                    userVoucherRepository.save(voucher);
                }
            }
        }
        if (order.getType() != null && !order.getType().equals("")) {
            if (order.getType().equals("E")) {
                order.setStatus("F");
            } else {
                order.setStatus("R");
            }
        }
        order.setStatusTime(new Date());
        orderRepository.saveAndFlush(order);
        //添加支付信息
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(orderNo);
        orderPay.setOrderId(order.getId());
        orderPay.setOrderType("P");
        if (type.equals("D")) {
            orderPay.setPayType("D");
        } else if (type.equals("DW")) {
            orderPay.setPayType("DW");
        } else {
            orderPay.setPayType("W");
        }
        orderPay.setUserId(order.getUserId());
        if (map == null) {
            orderPay.setPlatformOrderNo(null);
        } else {
            orderPay.setPlatformOrderNo(map.get("transaction_id"));
        }
        if (type.equals("DW")) {
            UserBalance userBalance = userBalanceRepository.findByUserId(order.getUserId());
            orderPay.setTotalPrice(order.getTotalPrice().subtract(userBalance.getBalance()));
        } else {
            orderPay.setTotalPrice(order.getTotalPrice());
        }
        orderPay.setNotifyStatus("A");
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        MemberWechat memberUser = memberWechatRepository.findByMbrId(order.getUserId());
        //满额赠券
        List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("满额", order.getWineryId());
        for (MarketActivity newUserActivity : activityList) {
            if (newUserActivity != null) {
                MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                    if (newUserActivity.getSendSetting() != null && newUserActivity.getSendSetting().equals("O")) {
                        Member user = memberRepository.getOne(memberUser.getMbrId());
                        if (user != null && marketActivitySpecDetail.getDepositMoney() != null && order.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                        }
                    } else {
                        Member user = memberRepository.getOne(memberUser.getMbrId());
                        if (user != null && marketActivitySpecDetail.getDepositMoney() != null && order.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            Integer count = order.getTotalPrice().divide(marketActivitySpecDetail.getDepositMoney(), 0, BigDecimal.ROUND_HALF_DOWN).intValue();
                            for (int i = 0; i < count; i++) {
                                marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                            }
                        }
                    }

                }
            }
        }
        //累计消费增券
        BigDecimal decimal = orderPayRepository.findSumByUserId(order.getUserId());
        List<MarketActivity> activityListm = marketActivityRepository.findByStatusAndMarketActivityTypeLike("累计", order.getWineryId());
        for (MarketActivity newUserActivity : activityListm) {
            if (newUserActivity != null) {
                MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                    Member user = memberRepository.getOne(memberUser.getMbrId());
                    if (user != null && marketActivitySpecDetail.getDepositMoney() != null && decimal.compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                        marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "O");
                    }
                }
            }
        }
        List<AdminUser> adminUsers = adminUserRepository.findAdminUserByWIdAndRoleName(order.getWineryId(), "管理员");
        for (AdminUser adminUser : adminUsers) {
            if (adminUser.getPhone() != null && !adminUser.getPhone().equals("")) {
                SmsTemp smsTemp = smsTempRepository.findByWineryIdAndContentLike(order.getWineryId(), "新订单");
                SMSUtil.sendRemindSMS(adminUser.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + adminUser.getName()  + "'}"));
            }
        }
    }

    /**
     * 通过订单id查询物流信息
     *
     * @param orderId
     * @return
     */
    public String express(Integer orderId) {
        /*List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("A", 3);*/
        //
        String result = null;
        //通过订单号查询物流单号，如果已发货则存在物流单号，否则为空
        OrderExpress express = orderExpressRepository.findByOrderId(orderId);
        if (express != null) {
            String expressNo = express.getExpressNo();
            if (expressNo != null) {
                result = ExpressQuery.kuaidi(expressNo);
            }

        }
        return result;
    }

    public List<OrderListDTO> orderList(Member user, String status) throws ParseException {
        List<Order> orderList;
        if (status.equals("all")) {
            orderList = orderRepository.findByOrderUserId(Integer.valueOf(user.getId().toString()));
        } else {
            orderList = orderRepository.findByOrderUserIdAndStatus(Integer.valueOf(user.getId().toString()), status);
        }
        List<OrderListDTO> orderListDTOS = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Order order : orderList) {
            if (order.getStatus().equals("P")) {
                String ineffectiveTime = addDateMinut(format.format(order.getCreateTime()), 24);
                if (new Date().compareTo(format.parse(ineffectiveTime)) >= 0) {
                    markTrueOrder(order, "E");
                }
            }
            OrderListDTO orderListDTO = new OrderListDTO();
            orderListDTO.setOrderId(order.getId());
            orderListDTO.setOrderNo(order.getOrderNo());
            orderListDTO.setProdNum(order.getProdNum());
            orderListDTO.setStatus(order.getStatus());
            orderListDTO.setStatusName(dictService.findStatusName("orders", "status", order.getStatus()));
            orderListDTO.setTotalPrice(String.valueOf(order.getTotalPrice()));
            List<NewProdListDTO> prodItem = new ArrayList<>();
            List<OrderPriceItem> list = orderPriceItemRepository.findByOrderId(order.getId());
            for (OrderPriceItem priceItem : list) {
                NewProdListDTO newProdList = new NewProdListDTO();
                Prod prod = prodRepository.getOne(priceItem.getProdId());
                DiscountListDTO discountList = new DiscountListDTO();
                if (prod != null) {
                    discountList.setId(prod.getId());
                    discountList.setProdName(prod.getTitle());
                    ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                    if (prodLogo != null) {
                        discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                    }
                    ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
                    if (price != null) {
                        discountList = themeService.getDiscountListByPrice(price, discountList, user, prod);
                    }
                    if (prod != null) {
                        newProdList.setName(prod.getTitle());
                        if (prodLogo != null) {
                            newProdList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                        }
                        if (price != null) {
                            newProdList.setPrice(discountList.getFinalPrice() == null ? discountList.getOriginalPrice() : discountList.getFinalPrice());
                        }
                    }
                    newProdList.setAmount(priceItem.getQuantity());

                    if (priceItem.getProdSpecId() != null) {
                        ProdSpec spec = prodSpecRepository.getOne(priceItem.getProdSpecId());
                        if (spec != null) {
                            newProdList.setProdSpec(spec.getSpecValue());
                        }
                    }
                    prodItem.add(newProdList);
                }
                UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
                orderListDTO.setBalance(userBalance.getBalance());
                orderListDTO.setProdItem(prodItem);
                orderListDTOS.add(orderListDTO);
            }
        }
        return orderListDTOS;
    }

    public Order findByOrderId(Integer orderId) {
        return orderRepository.getOne(orderId);
    }

    public void markTrueOrder(Order order, String status) {
        order.setStatus(status);
        order.setStatusTime(new Date());
        orderRepository.save(order);
        if (!status.equals("R")) {
            OrderSettle orderSettle = orderSettleRepository.findByOrderId(order.getId());
            if (orderSettle != null && orderSettle.getUseVoucherId() != null) {
                UserVoucher userVoucher = userVoucherRepository.getOne(orderSettle.getUseVoucherId());
                if (userVoucher != null) {
                    userVoucher.setUseTime(null);
                    userVoucherRepository.save(userVoucher);
                    VoucherInst inst = voucherInstRepository.getOne(userVoucher.getVoucherInstId());
                    if (inst != null) {
                        inst.setStatus("A");
                        inst.setStatusTime(new Date());
                        inst.setOrderId(null);
                        inst.setOrderType(null);
                        voucherInstRepository.save(inst);
                    }
                }

            }
            if (orderSettle != null) {
                UserPoint userPoint = userPointRepository.findByUserId(order.getUserId());
                if (userPoint != null) {
                    userPoint.setPoint(userPoint.getPoint() + (orderSettle.getUsePoint() == null ? 0 : orderSettle.getUsePoint()));
                    userPoint.setUpdateTime(new Date());
                    userPointRepository.saveAndFlush(userPoint);
                }
            }
        }

    }

    public OrdersDTO orderDetail(Order order) throws ParseException {
        Member user = memberRepository.getOne(order.getUserId());
        OrdersDTO ordersDTO = new OrdersDTO();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (order.getStatus().equals("P")) {
            String ineffectiveTime = addDateMinut(format.format(order.getCreateTime()), 24);
            StringBuffer time = getDatePoor(format.parse(ineffectiveTime), new Date());
            ordersDTO.setTime(time);
        }
        ordersDTO.setExpressMethod("普通快递");
        ordersDTO.setOrderId(order.getId());
        ordersDTO.setOrderNo(order.getOrderNo());
        ordersDTO.setOrderTime(order.getCreateTime());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ordersDTO.getOrderTime());
        ordersDTO.setStatus(order.getStatus());
        ordersDTO.setStatusName(dictService.findStatusName("orders", "status", order.getStatus()));
        ordersDTO.setExpressPrice("0.00");
        OrderSettle settle = orderSettleRepository.findByOrderId(order.getId());
        if (settle != null && settle.getUseVoucherId() != null) {
            UserVoucher voucher = userVoucherRepository.getOne(settle.getUseVoucherId());
            if (voucher != null) {
                VoucherInst inst = voucherInstRepository.getOne(voucher.getVoucherInstId());
                if (inst != null) {
                    ordersDTO.setVoucherName(inst.getName());
                }
            }
        }
        OrderAddress address = orderAddressRepository.findByOrderId(order.getId());
        if (address != null) {
            ordersDTO.setUserAddressId(address.getUserAddressId());
            UserAddress userAddress = userAddressRepository.getOne(address.getUserAddressId());
            if (userAddress != null) {
                ordersDTO.setUserName(userAddress.getContact());
                ordersDTO.setPhone(userAddress.getPhone());
                ordersDTO.setAddress(userAddress.getFullAddress());
            }

        } else {
            MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
            ordersDTO.setUserName(memberUser.getNickName());
            ordersDTO.setPhone(user.getPhone());
            ordersDTO.setAddress("自提");
        }
        List<NewProdListDTO> newProdList = new ArrayList<>();
        List<OrderPriceItem> list = orderPriceItemRepository.findByOrderId(order.getId());
        Double pric = 0.0;
        Integer pointSum = 0;
        for (OrderPriceItem orderPriceItem : list) {
            NewProdListDTO newProd = new NewProdListDTO();
            Prod prod = prodRepository.getOne(orderPriceItem.getProdId());
            if (prod != null) {
                ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
                newProd.setName(prod.getTitle());
                if (prod.getMemberDiscount().equals("P")) {
                    DiscountListDTO discountList = new DiscountListDTO();
                    discountList.setId(prod.getId());
                    discountList.setProdName(prod.getTitle());
                    ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                    if (prodLogo != null) {
                        discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                    }

                    if (price != null) {
                        discountList = themeService.getDiscountListByPrice(price, discountList, user, prod);
                    }
                    ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                    if (changed != null) {
                        /*newProd.setPoint(changed.getUsePoint());*/
                        pointSum += changed.getUsePoint();
                        newProd.setPrice(discountList.getFinalPrice());
                    }
                } else if (prod.getMemberDiscount().equals("Y")) {
                    DiscountListDTO discountList = new DiscountListDTO();
                    discountList.setId(prod.getId());
                    discountList.setProdName(prod.getTitle());
                    ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                    if (prodLogo != null) {
                        discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                    }

                    if (price != null) {
                        discountList = themeService.getDiscountListByPrice(price, discountList, user, prod);
                    }
                    ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                    if (changed != null) {
                        newProd.setPoint(changed.getUsePoint());
                        pointSum += newProd.getPoint();
                        newProd.setPrice(discountList.getFinalPrice());
                    } else {
                        newProd.setPrice(discountList.getFinalPrice());
                    }
                } else {
                    newProd.setPrice(String.valueOf(price.getFinalPrice()));
                }
                pric += Double.valueOf(String.valueOf(price.getFinalPrice()));
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                if (prodLogo != null) {
                    newProd.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
                if (prodSpec != null) {
                    ProdSpec spec = prodSpecRepository.getOne(prodSpec.getProdSpecId());
                    if (spec != null) {
                        newProd.setProdSpec(spec.getSpecValue());
                    }
                }
            }
            newProd.setAmount(orderPriceItem.getQuantity());
            newProdList.add(newProd);
        }
        ordersDTO.setGhsj("供货商家");
        ordersDTO.setTotalProdPrice(String.valueOf(pric));
        ordersDTO.setTotalPrice(String.valueOf(order.getTotalPrice()));
        ordersDTO.setNewProdLists(newProdList);
        ordersDTO.setTotalPoint(pointSum);
        UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        ordersDTO.setBalance(userBalance.getBalance());
        return ordersDTO;
    }

    public List<UserAddress> mineAddress(Member user) {
        List<UserAddress> address = userAddressRepository.findAddressByUserId(Integer.valueOf(user.getId().toString()));
        return address;
    }

    public void addAddress(Member user, String userName, String phone, String province, String city, String
            country, String detailAddress) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(Integer.valueOf(user.getId().toString()));
        userAddress.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userAddress.setContact(userName);
        userAddress.setPhone(phone);
        StringBuffer add = new StringBuffer("");
        if (province != null && !province.equals("")) {
            userAddress.setProvince(Integer.valueOf(province));
            Area area = areaRepository.getOne(Integer.valueOf(province));
            if (area != null) {
                add.append(area.getName());
            }
        }
        if (city != null && !city.equals("")) {
            userAddress.setCity(Integer.valueOf(city));
            Area area = areaRepository.getOne(Integer.valueOf(city));
            if (area != null) {
                add.append(area.getName());
            }
        }
        if (country != null && !country.equals("")) {
            userAddress.setCountry(Integer.valueOf(country));
            Area area = areaRepository.getOne(Integer.valueOf(country));
            if (area != null) {
                add.append(area.getName());
            }
        }
        userAddress.setDetailAddress(detailAddress);
        userAddress.setFullAddress(add.toString() + "" + detailAddress);
        UserAddress address = userAddressRepository.findDefaultAddressByUserId(Integer.valueOf(user.getId().toString()));
        if (address == null) {
            userAddress.setIsDefault("Y");
        } else {
            userAddress.setIsDefault("N");
        }
        userAddress.setCreateTime(new Date());
        userAddress.setStatusTime(new Date());
        userAddressRepository.save(userAddress);
    }

    public String oneMoreOrder(Member user, Order order) {
        Order order1 = new Order();
        order1.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        order1.setUserId(Integer.valueOf(user.getId().toString()));
        order1.setOrderNo(getOrderNoByMethod(user));
        order1.setProdNum(order.getProdNum());
        order1.setTotalPrice(order.getTotalPrice());
        order1.setStatus("P");
        order1.setType(order.getType());
        order1.setCreateTime(new Date());
        order1.setStatusTime(new Date());
        order1.setDescri(order.getDescri());
        orderRepository.save(order1);
        OrderAddress address = orderAddressRepository.findByOrderId(order.getId());
        if (address != null) {
            OrderAddress address1 = new OrderAddress();
            address1.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            address1.setOrderId(order1.getId());
            address1.setUserAddressId(address.getUserAddressId());
            address1.setCreateTime(new Date());
            orderAddressRepository.save(address1);
        }
        OrderPrice price = orderPriceRepository.findByOrderId(order.getId());
        if (price != null) {
            OrderPrice price1 = new OrderPrice();
            price1.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            price1.setOrderId(order1.getId());
            price1.setProdOrigPrice(price.getProdOrigPrice());
            price1.setProdFinalPrice(price.getProdFinalPrice());
            price1.setCarriageExpense(price.getCarriageExpense());
            price1.setTotalPrice(price.getTotalPrice());
            orderPriceRepository.save(price1);
        }
        OrderSettle settle = orderSettleRepository.findByOrderId(order.getId());
        OrderSettle settle1 = new OrderSettle();
        settle1.setOrderId(order1.getId());
        if (settle != null) {
            settle1.setUsePoint(settle.getUsePoint() == null ? 0 : settle.getUsePoint());
        }
        settle1.setUserId(Integer.valueOf(user.getId().toString()));
        settle1.setCreateTime(new Date());
        if (settle != null && settle.getUseVoucherId() != null) {
            UserVoucher voucher = userVoucherRepository.getOne(settle.getUseVoucherId());
            if (voucher != null && voucher.getUseTime() == null) {
                settle1.setUseVoucherId(settle.getUseVoucherId());
                //作废券
                voucher.setUseTime(new Date());
                userVoucherRepository.save(voucher);
                VoucherInst inst = voucherInstRepository.getOne(voucher.getVoucherInstId());
                if (inst != null) {
                    inst.setStatus("U");
                    inst.setStatusTime(new Date());
                    inst.setOrderId(order.getId());
                    inst.setOrderType("O");
                    voucherInstRepository.save(inst);
                }
            }
        }
        orderSettleRepository.save(settle1);
        List<OrderProd> prodList = orderProdRepository.findByOrderId(order.getId());
        List<OrderProd> prodList1 = new ArrayList<>();
        for (OrderProd orderProd : prodList) {
            OrderProd orderProd1 = new OrderProd();
            orderProd1.setOrderId(order1.getId());
            orderProd1.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            orderProd1.setProdId(orderProd.getProdId());
            orderProd1.setProdSpecId(orderProd.getProdSpecId());
            orderProd1.setQuantity(orderProd.getQuantity());
            prodList1.add(orderProd1);
        }
        orderProdRepository.saveAll(prodList1);
        List<OrderPriceItem> itemList = orderPriceItemRepository.findByOrderId(order.getId());
        List<OrderPriceItem> itemList1 = new ArrayList<>();
        for (OrderPriceItem orderPriceItem : itemList) {
            OrderPriceItem orderPriceItem1 = new OrderPriceItem();
            orderPriceItem1.setOrderId(order1.getId());
            orderPriceItem1.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            orderPriceItem1.setOrderPriceId(orderPriceItem.getId());
            orderPriceItem1.setProdId(orderPriceItem.getProdId());
            orderPriceItem1.setQuantity(orderPriceItem.getQuantity());
            orderPriceItem1.setProdSpecId(orderPriceItem.getProdSpecId());
            orderPriceItem1.setOrigUnitPrice(orderPriceItem.getOrigUnitPrice());
            orderPriceItem1.setOrigTotalPrice(orderPriceItem.getOrigTotalPrice());
            orderPriceItem1.setFinalUnitPrice(orderPriceItem.getFinalUnitPrice());
            orderPriceItem1.setFinalTotalPrice(orderPriceItem.getFinalTotalPrice());
            itemList1.add(orderPriceItem1);
        }
        orderPriceItemRepository.saveAll(itemList1);
        return order1.getOrderNo();
    }

    public UserAddress addressInfo(Integer addressId) {
        UserAddress address = userAddressRepository.getOne(addressId);
        Area area = areaRepository.getOne(Integer.valueOf(address.getCity()));
        address.setCityName(area.getName());
        Area areac = areaRepository.getOne(Integer.valueOf(address.getCountry()));
        address.setCountryName(areac.getName());
        Area areap = areaRepository.getOne(Integer.valueOf(address.getProvince()));
        address.setProvinceName(areap.getName());
        return address;
    }

    public void updateAddress(Member user, Integer addressId, String userName, String phone, String province, String
            city, String country, String detailAddress) {
        UserAddress userAddress = userAddressRepository.getOne(addressId);
        userAddress.setUserId(Integer.valueOf(user.getId().toString()));
        userAddress.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userAddress.setContact(userName);
        userAddress.setPhone(phone);
        StringBuffer add = new StringBuffer("");
        if (province != null && !province.equals("")) {
            userAddress.setProvince(Integer.valueOf(province));
            Area area = areaRepository.getOne(Integer.valueOf(province));
            if (area != null) {
                add.append(area.getName());
            }
        }
        if (city != null && !city.equals("")) {
            userAddress.setCity(Integer.valueOf(city));
            Area area = areaRepository.getOne(Integer.valueOf(city));
            if (area != null) {
                add.append(area.getName());
            }
        }
        if (country != null && !country.equals("")) {
            userAddress.setCountry(Integer.valueOf(country));
            Area area = areaRepository.getOne(Integer.valueOf(country));
            if (area != null) {
                add.append(area.getName());
            }
        }
        userAddress.setDetailAddress(detailAddress);
        userAddress.setFullAddress(add.toString() + "" + detailAddress);
        userAddress.setStatusTime(new Date());
        userAddressRepository.save(userAddress);
    }

    public List<Order> ordersList(AdminUser adminUser, String
            orderNo, List<String> orderTime, List<String> orderStatus, String orderStatusAll) {
        List<Order> list = new ArrayList<>();
        String strtime = orderTime.get(0).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
        String strstatus = orderStatus.get(0).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
        List<String> strs = new ArrayList<>();
        if (!orderStatus.equals("")) {
            for (String str : orderStatus) {
                strs.add(str.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", ""));
            }
        }
        if (strstatus.equals("") && strtime.equals("")) {
            list = orderRepository.findByWineryIdAndNo(adminUser.getWineryId(), orderNo);
        } else if (!strstatus.equals("") && !strtime.equals("")) {
            String strtime2 = orderTime.get(1).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            list = orderRepository.findByWineryIdAndNoAndTimeAndStatus(adminUser.getWineryId(), orderNo, strtime, strtime2, strs);
        } else if (strstatus.equals("")) {
            String strtime2 = orderTime.get(1).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            list = orderRepository.findByWineryIdAndNoAndTime(adminUser.getWineryId(), orderNo, strtime, strtime2);
        } else if (strtime.equals("")) {
            list = orderRepository.findByWineryIdAndNoAndStatus(adminUser.getWineryId(), orderNo, strs);
        }
        Integer count = 1;
        for (Order order : list) {
            order.setIndex(count);
            count++;
                order.setStatusName(dictService.findStatusName("orders", "status", order.getStatus()));
        }
        return list;
    }

    public Map<String, Object> ordersDetail(AdminUser adminUser, Order order) {
        Map<String, Object> map = new HashMap<>();
        ProdPriceLevel prodPriceLevel = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> orderBaseInfo = new HashMap<>();
        orderBaseInfo.put("orderId", order.getId());
        orderBaseInfo.put("orderNo", order.getOrderNo());
        MemberWechat user = memberWechatRepository.findByMbrId(order.getUserId());
        if (user != null) {
            orderBaseInfo.put("orderUserName", EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(user.getNickName())));
        }
        OrderPay orderPay = orderPayRepository.findByOrderIdAndOrderNoAndOrderType(order.getId(), order.getOrderNo(), "P");
        orderBaseInfo.put("payMethod", dictService.findStatusName("order_pay", "pay_type", orderPay.getPayType()));

        orderBaseInfo.put("expressMethod", "快递");//配送方式
        if (order.getStatus().equals("H") || order.getStatus().equals("R") || order.getStatus().equals("S")) {
            OrderExpress express = orderExpressRepository.findByOrderId(order.getId());
            if (express != null) {
                orderBaseInfo.put("expressNo", express.getExpressNo());
            }
        } else if (order.getStatus().equals("P")) {
            orderBaseInfo.put("expressNo", "待支付");
        } else {
            orderBaseInfo.put("expressNo", "未发货");
        }
        orderBaseInfo.put("orderFrom", "小程序商城");
        mapList.add(orderBaseInfo);
        map.put("orderBaseInfo", mapList);//基本信息
        mapList = new ArrayList<>();
        orderBaseInfo = new HashMap<>();
        OrderAddress address = orderAddressRepository.findByOrderId(order.getId());
        if (address != null) {
            UserAddress address1 = userAddressRepository.getOne(address.getUserAddressId());
            if (address1 != null) {
                orderBaseInfo.put("userName", address1.getContact());
                orderBaseInfo.put("userPhone", address1.getPhone());
                orderBaseInfo.put("orderAddress", address1.getFullAddress());
                orderBaseInfo.put("addressCode", "10001");
            }

        }
        mapList.add(orderBaseInfo);
        map.put("orderUserInfo", mapList);//收货人信息
        List<Map<String, Object>> list = new ArrayList<>();
        List<OrderPriceItem> orderPriceItems = orderPriceItemRepository.findByOrderId(order.getId());
        for (OrderPriceItem orderPriceItem : orderPriceItems) {
            orderBaseInfo = new HashMap<>();
            Prod prod = prodRepository.getOne(orderPriceItem.getProdId());
            if (prod != null) {
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                if (prodLogo != null) {
                    orderBaseInfo.put("prodLogo", PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdProdSpec prodProdSpec = prodProdSpecRepository.findByProdId(prod.getId());
                if (prodProdSpec != null) {
                    ProdSpec spec = prodSpecRepository.getOne(prodProdSpec.getProdSpecId());
                    if (spec != null) {
                        orderBaseInfo.put("prodSpec", spec.getSpecValue());
                    }
                }
                orderBaseInfo.put("prodName", prod.getName());
                orderBaseInfo.put("prodCode", prod.getCode());
                orderBaseInfo.put("prodPrice", orderPriceItem.getOrigUnitPrice());
                orderBaseInfo.put("prodNum", orderPriceItem.getQuantity());
                orderBaseInfo.put("xiaoJi", orderPriceItem.getOrigTotalPrice());
                MemberWechat memberUser = memberWechatRepository.findByMbrId(user.getMbrId());
                prodPriceLevel = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), memberUser.getMemberLevel());

                list.add(orderBaseInfo);
            }
        }
        map.put("orderProdInfo", list);//商品信息
        orderBaseInfo = new HashMap<>();
        mapList = new ArrayList<>();
        OrderPrice price = orderPriceRepository.findByOrderId(order.getId());
        if (price != null) {
            orderBaseInfo.put("heJi", price.getProdOrigPrice());
            orderBaseInfo.put("expressPrice", price.getCarriageExpense());
        }
        OrderSettle settle = orderSettleRepository.findByOrderId(order.getId());
        if (settle != null && settle.getUseVoucherId() != null) {
            UserVoucher userVoucher = userVoucherRepository.getOne(settle.getUseVoucherId());
            VoucherInst inst = voucherInstRepository.getOne(userVoucher.getVoucherInstId());
            if (inst != null) {
                orderBaseInfo.put("voucherName", inst.getName());
            }

        }
        if (settle != null) {
            orderBaseInfo.put("userPoint", settle.getUsePoint());
        }
        if (prodPriceLevel != null) {
            // TODO 直接显示折后价格
//            orderBaseInfo.put("memberDiscount", prodPriceLevel.getDiscount().multiply(new BigDecimal(10)) + "折");
            orderBaseInfo.put("memberDiscount", String.valueOf(prodPriceLevel.getDiscountPrice()));
        }
        orderBaseInfo.put("copePrice", order.getTotalPrice());
        orderBaseInfo.put("realPrice", order.getTotalPrice());
        mapList.add(orderBaseInfo);
        map.put("orderPriceInfo", mapList);//费用信息
        return map;
    }

    public void orderShair(AdminUser adminUser, Order order, String expressMethod, String expressNo) {
        order.setStatus("H");
        orderRepository.save(order);
        OrderExpress orderExpress = new OrderExpress();
        orderExpress.setDetail(expressMethod);
        orderExpress.setExpressNo(expressNo);
        orderExpress.setWineryId(adminUser.getWineryId());
        orderExpress.setOrderId(order.getId());
        orderExpress.setCreateTime(new Date());
        orderExpressRepository.save(orderExpress);
    }

    public boolean checkHavePoint(Member user, Prod prod, Integer amount) {
        ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
        if (changed != null) {
            Integer totalPoint = changed.getUsePoint() * amount;
            UserPoint point = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
            if (point == null || point.getPoint() < totalPoint) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean checkHavePointCarts(Member user, List<Cart> carts) {
        Integer totalPoint = 0;
        for (Cart cart : carts) {
            Prod prod = themeService.checkProdId(cart.getProdId());
            if(prod.getMemberDiscount().equals("P")){
                ProdChanged changed = prodChangedRepository.findByProdId(cart.getProdId());
                if (changed != null) {
                    totalPoint += changed.getUsePoint() * cart.getAmount();
                }
            }
        }
        UserPoint point = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        if (point == null || point.getPoint() < totalPoint) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkHavePointCartsMoreOrder(Member user, Order order) {
        List<OrderProd> list = orderProdRepository.findByOrderId(order.getId());
        Integer totalPoint = 0;
        for (OrderProd orderProd : list) {
            Prod prod = themeService.checkProdId(orderProd.getProdId());
            if(prod.getMemberDiscount().equals("P")){
                ProdChanged changed = prodChangedRepository.findByProdId(orderProd.getProdId());
                if (changed != null) {
                    totalPoint += changed.getUsePoint() * orderProd.getQuantity();
                }
            }
        }
        UserPoint point = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        if (point == null || point.getPoint() < totalPoint) {
            return false;
        } else {
            return true;
        }
    }

    public Integer checkStock(List<Cart> carts) {
        for (Cart cart : carts) {
            ProdStock stock = prodStockRepository.findByProdId(cart.getProdId());
            if (stock != null) {
                if (cart.getAmount() > stock.getProdStock()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public Integer checkStockOne(Integer prodId, Integer amount) {
        ProdStock stock = prodStockRepository.findByProdId(prodId);
        if (stock != null) {
            if (amount > stock.getProdStock()) {
                return 1;
            }
        }
        return 0;
    }

    public Integer checkStockMoreOrder(Order order) {
        List<OrderProd> list = orderProdRepository.findByOrderId(order.getId());
        for (OrderProd orderProd : list) {
            ProdStock stock = prodStockRepository.findByProdId(orderProd.getProdId());
            if (stock != null) {
                if (orderProd.getQuantity() > stock.getProdStock()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public void delAddress(Integer addressId) {
        userAddressRepository.deleteById(addressId);
    }

    public String checkLimit(List<Cart> carts) {
        for (Cart cart : carts) {
            Prod prod = prodRepository.getOne(cart.getProdId());
            if (prod != null && prod.getIsLimit().equals("N") && cart.getAmount() > prod.getLimitCount()) {
                return "商品" + prod.getTitle() + "只能购买" + prod.getLimitCount() + "个";
            }
        }
        return "";
    }

    public String buyNumCart(Member user, List<Cart> carts) {
        for (Cart cart : carts) {
            Prod prod = prodRepository.getOne(cart.getProdId());
            int buyNum = orderRepository.findByUserIdAndProdId(Integer.valueOf(user.getId().toString()), prod.getId(),Integer.valueOf(user.getWineryId().toString())) + cart.getAmount();
            if (prod != null && prod.getIsLimit().equals("N") && buyNum > prod.getLimitCount()) {
                return "商品" + prod.getTitle() + "限购已达上限";
            }

        }
        return "";
    }

    public String buyNumOne(Member user, Integer pordId, Integer amount) {
        Prod prod = prodRepository.getOne(pordId);
        int buyNum = orderRepository.findByUserIdAndProdId(Integer.valueOf(user.getId().toString()), prod.getId(),Integer.valueOf(user.getWineryId().toString())) + amount;
        if (prod != null && prod.getIsLimit().equals("N") && buyNum > prod.getLimitCount()) {
            return "商品" + prod.getTitle() + "限购已达上限";
        }
        return "";
    }



    public String addDateMinut(String day, int hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null){
            return "";
        }

        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return format.format(date);
    }


    public StringBuffer getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        StringBuffer result = null;
        if (hour >= 10) {
            result = new StringBuffer(hour + ":");
        } else {
            result = new StringBuffer("0" + hour + ":");
        }

        if (min >= 10) {
            result.append(min + ":");
        } else {
            result.append("0" + min + ":");
        }

        if (sec >= 10) {
            result.append(sec);
        } else {
            result.append("0" + sec);
        }
        return result;
    }


    public void balance(Integer userId, Order order, String type) throws ClientException {
        paySuccess(order.getOrderNo(), type, null);
        Member user = memberRepository.getOne(userId);
        /*List<AdminUser> adminUsers = adminUserRepository.findAdminUserByWIdAndRoleName(user.getWineryId(), "管理员");
        for (AdminUser adminUser : adminUsers) {
            if (adminUser.getPhone() != null && !adminUser.getPhone().equals("")) {
                SmsTemp smsTemp = smsTempRepository.findByWineryIdAndContentLike(user.getWineryId(), "新订单");
                    SMSUtil.sendRemindSMS(adminUser.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + user.getName()  + "'}"));
            }
        }*/
        UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        UserDepositDetail userDepositDetail = new UserDepositDetail();
        if (type.equals("D")) {
            userDepositDetail.setBalance(order.getTotalPrice());
            userBalance.setBalance(userBalance.getBalance().subtract(order.getTotalPrice()));
        } else {
            userDepositDetail.setBalance(userBalance.getBalance());
            userBalance.setBalance(new BigDecimal(0));
        }
        userBalance.setUpdateTime(new Date());
        UserBalance userBalanceSave = userBalanceRepository.saveAndFlush(userBalance);
        userDepositDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userDepositDetail.setUserId(Integer.valueOf(user.getId().toString()));
        userDepositDetail.setAction("O");
        userDepositDetail.setBalanceType("M");
        userDepositDetail.setLatestBalance(userBalanceSave.getBalance());
        userDepositDetail.setOrderId(order.getId());
        userDepositDetail.setOrderNo(order.getOrderNo());
        userDepositDetail.setCreateTime(new Date());
        userDepositDetailRepository.saveAndFlush(userDepositDetail);
    }


    /* *
     * 商城订单线下支付
     * @Author        zyj
     * @Date          2018/12/21 16:38
     * @Description
     * */
    public void offlinePay(Order order) {
        OrderPay orderPay = new OrderPay();
        orderPay.setTotalPrice(order.getTotalPrice());
        orderPay.setOrderNo(order.getOrderNo());
        orderPay.setOrderId(order.getId());
        orderPay.setOrderType("P");
        orderPay.setPayType("O");
        orderPay.setUserId(order.getUserId());
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("W");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
    }


    public void updateOrderStatus(Integer createUserId, Order order, String status) throws ClientException {
        order.setCreateUserId(createUserId);
        order.setStatus(status);
        order.setStatusTime(new Date());
        orderRepository.saveAndFlush(order);
        if (status.equals("F")) {
            //支付成功减库存
            List<OrderProd> list = orderProdRepository.findByOrderId(order.getId());
            for (OrderProd orderProd : list) {
                ProdStock stock = prodStockRepository.findByProdId(orderProd.getProdId());
                if (stock != null) {
                    stock.setProdStock(stock.getProdStock() - orderProd.getQuantity());
                }
                prodStockRepository.save(stock);
            }
            OrderSettle settle = orderSettleRepository.findByOrderId(order.getId());
            if (order.getTotalPrice().compareTo(new BigDecimal(0)) != 0) {
                //支付成功减用户积分
                Member userRepositoryOne = memberRepository.getOne(order.getUserId());
                if (userRepositoryOne != null && settle != null) {
                    pointRewardRuleService.addUserPoint(userRepositoryOne, settle.getUsePoint(), "S", order.getOrderNo(), order.getId());
                }
                //消费多少送一积分
                pointRewardRuleService.updateUserPoint(userRepositoryOne, order.getTotalPrice(), "W", order.getOrderNo(), order.getId());
            }
            if (settle != null && settle.getUseVoucherId() != null) {
                UserVoucher voucher = userVoucherRepository.getOne(settle.getUseVoucherId());
                if (voucher != null && voucher.getUseTime() == null) {
                    VoucherInst inst = voucherInstRepository.getOne(voucher.getVoucherInstId());
                    if (inst != null) {
                        //作废券
                        inst.setStatus("U");
                        inst.setStatusTime(new Date());
                        inst.setOrderId(order.getId());
                        inst.setOrderType("O");
                        voucherInstRepository.save(inst);
                        voucher.setUseTime(new Date());
                        userVoucherRepository.save(voucher);
                    }
                }
            }
            //添加支付信息
            OrderPay orderPay = null;
            OrderPay orderPaySelect = orderPayRepository.findByOrderId(order.getId(), "P");
            if (orderPaySelect != null) {
                orderPay = orderPaySelect;
            } else {
                orderPay = new OrderPay();
                orderPay.setOrderNo(order.getOrderNo());
                orderPay.setTotalPrice(order.getTotalPrice());
                orderPay.setOrderId(order.getId());
                orderPay.setOrderType("P");
                orderPay.setPayType("O");
                orderPay.setUserId(order.getUserId());
                orderPay.setPayStatus("W");
                orderPay.setPayTime(new Date());
            }
            orderPay.setPayStatus("A");
            orderPay.setPayTime(new Date());
            orderPayRepository.saveAndFlush(orderPay);
            MemberWechat memberUser = memberWechatRepository.findByMbrId(order.getUserId());
            //满额赠券
            List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("满额", order.getWineryId());
            for (MarketActivity newUserActivity : activityList) {
                if (newUserActivity != null) {
                    MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                    if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                        if (newUserActivity.getSendSetting() != null && newUserActivity.getSendSetting().equals("O")) {
                            Member user = memberRepository.getOne(memberUser.getMbrId());
                            if (user != null && marketActivitySpecDetail.getDepositMoney() != null && order.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                                marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                            }
                        } else {
                            Member user = memberRepository.getOne(memberUser.getMbrId());
                            if (user != null && marketActivitySpecDetail.getDepositMoney() != null && order.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                                Integer count = order.getTotalPrice().divide(marketActivitySpecDetail.getDepositMoney(), 0, BigDecimal.ROUND_HALF_DOWN).intValue();
                                for (int i = 0; i < count; i++) {
                                    marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                                }
                            }
                        }

                    }
                }
            }
            //累计消费增券
            BigDecimal decimal = orderPayRepository.findSumByUserId(order.getUserId());
            List<MarketActivity> activityListm = marketActivityRepository.findByStatusAndMarketActivityTypeLike("累计", order.getWineryId());
            for (MarketActivity newUserActivity : activityListm) {
                if (newUserActivity != null) {
                    MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                    if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                        Member user = memberRepository.getOne(memberUser.getMbrId());
                        if (user != null && marketActivitySpecDetail.getDepositMoney() != null && decimal.compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "O");
                        }
                    }
                }
            }
        }
    }

    public Map<String, Object> getOrderDetail(String orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo);
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        String status = dictRepository.findNameByTableNameAndColumnNameAndStsId("orders", "status", order.getStatus());
        map.put("status", status);
        Map<String, String> mapList = dictService.findMapByTableNameAndColumnName("orders", "status");
        map.put("statusList", mapList);
        return map;
    }

}
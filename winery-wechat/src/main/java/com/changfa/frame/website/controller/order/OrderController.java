package com.changfa.frame.website.controller.order;


import com.changfa.frame.data.dto.wechat.OrderListDTO;
import com.changfa.frame.data.dto.wechat.OrdersDTO;
import com.changfa.frame.data.entity.cart.Cart;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.UserAddress;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.cart.CartService;
import com.changfa.frame.service.deposit.UserBalanceService;
import com.changfa.frame.service.order.OrderService;
import com.changfa.frame.service.theme.ThemeService;
import com.changfa.frame.service.user.MemberService;
import com.changfa.frame.service.voucher.UserVoucherService;
import com.changfa.frame.service.wechat.*;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserVoucherService userVoucherService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private PayService payService;
    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private MemberRepository memberRepository;

    //添加订单(购物车)
    @RequestMapping(value = "/addOrderByCart", method = RequestMethod.POST)
    public String addOrderByCart(@RequestBody Map<String, Object> map) {
        try {
            log.info("添加订单(购物车)：" + "token:" + map);
            String token = map.get("token").toString();
            String descri = map.get("descri").toString();
            Integer userAddressId = (Integer) map.get("userAddressId");
            List<Integer> cartIds = (List<Integer>) map.get("cartIds");//购物车
            String voucherId1 = map.get("voucherId").toString(); //优惠券
            Integer voucherId = null;
            if (!voucherId1.equals("")) {
                voucherId = (Integer) map.get("voucherId");
            }
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            UserAddress userAddress = null;
            if (userAddressId != -1) {
                userAddress = memberService.checkUserAddressId(userAddressId);
                if (userAddress == null) {
                    return JsonReturnUtil.getJsonReturn(37002, "地址[" + userAddressId + "]不存在");
                }
            }
            UserVoucher userVoucher = null;
            if (voucherId != null) {
                userVoucher = userVoucherService.checkVoucherId(voucherId);
                if (userVoucher == null) {
                    return JsonReturnUtil.getJsonReturn(37003, "票[" + voucherId + "]不存在");
                }
            }
            List<Cart> carts = cartService.checkCartIds(cartIds);
            if (carts == null || carts.size() == 0 || carts.size() != cartIds.size()) {
                return JsonReturnUtil.getJsonReturn(37003, "cartIds[" + cartIds + "]不存在");
            }
            //检查库存
            Integer stock = orderService.checkStock(carts);
            if (stock != 0) {
                return JsonReturnUtil.getJsonReturn(37003, "库存不足");
            }
            //检查限购
            String buyNum = orderService.buyNumCart(user,carts);
            if (!buyNum.equals("")) {
                return JsonReturnUtil.getJsonReturn(37003, buyNum);
            }
            String isLimit = orderService.checkLimit(carts);
            if (!isLimit.equals("")) {
                return JsonReturnUtil.getJsonReturn(37003, isLimit);
            }

            //检查积分是否购
            boolean flag = orderService.checkHavePointCarts(user, carts);
            if (!flag) {
                return JsonReturnUtil.getJsonReturn(37004, "积分不足");
            }
            String cart = orderService.addOrderByCart(user, userAddress, userVoucher, carts, descri);
            Map<String, String> map1 = new HashMap<>();
            Order order = orderService.findByOrderNo(cart);
            map1.put("orderId", order.getId().toString());
            map1.put("orderNo", cart);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "添加成功", map1).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //添加订单(立即购买)
    @RequestMapping(value = "/addOrderByOne", method = RequestMethod.POST)
    public String addOrderByOne(@RequestBody Map<String, Object> map) {
        try {
            log.info("添加订单(立即购买)" + "token:" + map);
            String token = map.get("token").toString();
            String descri = map.get("descri").toString();
            Integer userAddressId = (Integer) map.get("userAddressId");
            Integer prodId = (Integer) map.get("prodId");
            Integer amount = (Integer) map.get("amount");
            String voucherId1 = map.get("voucherId").toString();
            Integer voucherId = null;
            if (!voucherId1.equals("")) {
                voucherId = (Integer) map.get("voucherId");
            }
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            UserAddress userAddress = null;
            if (userAddressId != -1) {
                userAddress = memberService.checkUserAddressId(userAddressId);
                if (userAddress == null) {
                    return JsonReturnUtil.getJsonReturn(37002, "地址[" + userAddressId + "]不存在");
                }
            }
            UserVoucher userVoucher = null;
            if (voucherId != null) {
                userVoucher = userVoucherService.checkVoucherId(voucherId);
                if (userVoucher == null) {
                    return JsonReturnUtil.getJsonReturn(37003, "票[" + voucherId + "]不存在");
                }
            }
            Prod prod = themeService.checkProdId(prodId);
            if (prod == null) {
                return JsonReturnUtil.getJsonReturn(37003, "prodId[" + prodId + "]不存在");
            }
            //检查库存
            Integer stock = orderService.checkStockOne(prodId, amount);
            if (stock != 0) {
                return JsonReturnUtil.getJsonReturn(37003, "库存不足");
            }
            //检查限购
            String buyNum = orderService.buyNumOne(user,prod.getId(),amount);
            if (!buyNum.equals("")) {
                return JsonReturnUtil.getJsonReturn(37003, buyNum);
            }
            if (prod.getIsLimit().equals("N")){
                if ( amount > prod.getLimitCount()) {
                    return JsonReturnUtil.getJsonReturn(37003, "只能购买" + prod.getLimitCount() + "个");
                }
            }

            //检查积分是否够
            if(prod.getMemberDiscount().equals("P")){
                boolean flag = orderService.checkHavePoint(user, prod, amount);
                if (!flag) {
                    return JsonReturnUtil.getJsonReturn(37004, "积分不足");
                }
            }

            String cart = orderService.addOrderByOne(user, userAddress, userVoucher, prodId, amount, descri);
            Map<String, String> map1 = new HashMap<>();
            Order order = orderService.findByOrderNo(cart);
            map1.put("orderId", order.getId().toString());
            map1.put("orderNo", cart);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "添加成功", map1).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //添加订单(再来一单)
    @RequestMapping(value = "/oneMoreOrder", method = RequestMethod.POST)
    public String oneMoreOrder(@RequestBody Map<String, Object> map) {
        try {
            log.info("添加订单(再来一单)" + "token:" + map);
            String token = map.get("token").toString();
            Integer orderId = (Integer) map.get("orderId");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Order order = orderService.findByOrderId(orderId);
            if (order == null) {
                return JsonReturnUtil.getJsonReturn(37003, "orderId[" + orderId + "]不存在");
            }
            //检查库存
            Integer stock = orderService.checkStockMoreOrder(order);
            if (stock != 0) {
                return JsonReturnUtil.getJsonReturn(37003, "库存不足,购买失败");
            }
            //检查积分是否购
            boolean flag = orderService.checkHavePointCartsMoreOrder(user, order);
            if (!flag) {
                return JsonReturnUtil.getJsonReturn(37004, "积分不足");
            }
            String cart = orderService.oneMoreOrder(user, order);
            Map<String, String> map1 = new HashMap<>();
            map1.put("orderNo", cart);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "添加成功", map1).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    /**
     * 支付接口获得预支付id,然后封装请求参数获得拉取支付的响应参数
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/pay")
    public Request<Map<String, String>> order(@RequestBody Map<String, Object> map,

                                              HttpServletResponse response, HttpServletRequest request) throws Exception {

        log.info("订单支付：" + map);
        String token = map.get("token").toString();
        String openId = map.get("openId").toString();
        String orderNo = map.get("orderNo").toString();
        Member user = memberService.checkToken(token);
        //检查库存
        Order order = orderService.findByOrderNo(orderNo);
        if (order != null) {
            Integer suec = orderService.checkStockMoreOrder(order);
            if (suec != 0) {
                return ResultBuilder.failStock();
            }
        }
        if (user != null) {
            String ip = WechatService.getIpAddr(request);
            BigDecimal totalPrice = orderService.findByOrderNo(orderNo).getTotalPrice();
            if (totalPrice.compareTo(new BigDecimal(0)) <= 0) {
                totalPrice = new BigDecimal(0.01);
            }
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(Integer.valueOf(user.getWineryId().toString()));
            String requestParam = payService.getPayParam(openId, String.valueOf(totalPrice.multiply(new BigDecimal(100)).intValue()), ip, "订单", orderNo, "O");
            Map<String, String> result = payService.requestWechatPayServer(requestParam);
            Map<String, String> datas = new TreeMap<>();
            if (result.get("return_code").equals("SUCCESS")) {
                String prepayId = result.get("prepay_id");
                datas.put("appId", wineryConfigure.getAppId());
                datas.put("package", "prepay_id=" + prepayId);
                datas.put("signType", "MD5");
                datas.put("timeStamp", Long.toString(System.currentTimeMillis()).substring(0, 10));
                datas.put("nonceStr", WXUtil.getNonceStr());
                String sign = SignatureUtils.signature(datas, wineryConfigure.getWxPayKey());
                datas.put("paySign", sign);
                System.out.println(ResultBuilder.success(datas));
                return ResultBuilder.success(datas);
            }
        }
        return ResultBuilder.fail();
    }

    @RequestMapping(value = "/successOrder", method = RequestMethod.POST)
    public String successOrder(@RequestBody Map<String, Object> map) {
        try {
            log.info("支付成功订单：" + "token:" + map);
            String token = map.get("token").toString();
            String orderNo = map.get("orderNo").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Order order = orderService.findByOrderNo(orderNo);
            if (order != null && order.getStatus().equals("P")) {
                orderService.paySuccess(order.getOrderNo(), "W", null);
                return JsonReturnUtil.getJsonReturn(0, "", "支付成功");
            } else {
                return JsonReturnUtil.getJsonReturn(37002, "订单异常支付失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/anotifyUrl")
    public String notification(HttpServletRequest request) {
        try {
            Map<String, String> map = payService.getCallbackParams(request);
            log.info("订单支付回调接口：" + map);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderNo = map.get("out_trade_no");
                System.out.println("支付成功");
                //支付成功之后的逻辑
                orderService.paySuccess(orderNo, "W", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    public String balancePay(@RequestBody Map<String, Object> map) {
        try {
            log.info("商品余额支付：" + "token:" + map);
            String token = map.get("token").toString();
            Integer orderId = (Integer) map.get("orderId");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            //检查库存
            Order order = orderService.findByOrderId(orderId);
            UserBalance userBalance = userBalanceService.findByUserId(Integer.valueOf(user.getId().toString()));
            if (order != null) {
                Integer suec = orderService.checkStockMoreOrder(order);
                if (suec != 0) {
                    return JsonReturnUtil.getJsonReturn(1, "100", "库存不足");
                } else if (userBalance.getBalance().compareTo(order.getTotalPrice()) < 0) {
                    return JsonReturnUtil.getJsonReturn(1, "100", "余额不足");
                } else {
                    orderService.balance(Integer.valueOf(user.getId().toString()), order, "D");
                }
                return JsonReturnUtil.getJsonReturn(0, "200", "支付成功");
            }
            return JsonReturnUtil.getJsonReturn(0, "100", "订单错误");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


    /**
     * 通过订单id查询物流信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/express", method = RequestMethod.POST)
    public String express(@RequestBody Map<String, Object> map) {
        try {
            log.info("物流信息查询单号：" + "orderId:" + map);
            Integer orderId = (Integer) map.get("orderId");
            String result = orderService.express(orderId);
            if (result == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "暂时查询不到物流信息");
            } else {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 确认收货
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/markTrueOrder", method = RequestMethod.POST)
    public String markTrueOrder(@RequestBody Map<String, Object> map) {
        try {
            log.info("确认收货：" + "orderId:" + map);
            Integer orderId = (Integer) map.get("orderId");
            Order order = orderService.findByOrderId(orderId);
            if (order == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到orderId");
            } else {
                orderService.markTrueOrder(order, "R");
                return JsonReturnUtil.getJsonIntReturn(0, "确认收货");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 取消订单
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public String cancelOrder(@RequestBody Map<String, Object> map) {
        try {
            log.info("取消订单：" + "orderId:" + map);
            Integer orderId = Integer.valueOf(map.get("orderId").toString());
            Order order = orderService.findByOrderId(orderId);
            if (order == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到orderId");
            } else {
                orderService.markTrueOrder(order, "D");
                return JsonReturnUtil.getJsonIntReturn(0, "取消订单成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 提醒发货
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/callExpressOrder", method = RequestMethod.POST)
    public String callExpressOrder(@RequestBody Map<String, Object> map) {
        try {
            log.info("提醒发货：" + "orderId:" + map);
            Integer orderId = (Integer) map.get("orderId");
            Order order = orderService.findByOrderId(orderId);
            if (order == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到orderId");
            } else {
                return JsonReturnUtil.getJsonIntReturn(0, "提醒发货成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * token查询订单列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public String orderList(@RequestBody Map<String, Object> map) {
        try {
            log.info("token查询订单列表：" + "token:" + map);
            String token = map.get("token").toString();
            String status = map.get("status").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<OrderListDTO> listDTOS = orderService.orderList(user, status);
            if (listDTOS == null || listDTOS.size() == 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "暂无信息", "").toString();
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", listDTOS).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 订单id查询订单详情
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.POST)
    public String orderDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("订单id查询订单详情：" + "token:" + map);
            String token = map.get("token").toString();
            Integer orderId = Integer.valueOf(map.get("orderId").toString());
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Order order = orderService.findByOrderId(orderId);
            if (order == null) {
                return JsonReturnUtil.getJsonReturn(37002, "id[" + orderId + "]不存在");
            }
            OrdersDTO listDTOS = orderService.orderDetail(order);
            if (listDTOS == null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "暂无信息", "").toString();
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", listDTOS).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 我的地址管理列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/mineAddress", method = RequestMethod.POST)
    public String mineAddress(@RequestBody Map<String, Object> map) {
        try {
            log.info("我的地址管理列表：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<UserAddress> listDTOS = orderService.mineAddress(user);
            if (listDTOS == null || listDTOS.size() == 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "暂无信息", "").toString();
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", listDTOS).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 地址默认信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/addressInfo", method = RequestMethod.POST)
    public String addressInfo(@RequestBody Map<String, Object> map) {
        try {
            log.info("地址默认信息：" + "token:" + map);
            String token = map.get("token").toString();
            Integer addressId = (Integer) map.get("addressId");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            UserAddress listDTOS = orderService.addressInfo(addressId);
            if (listDTOS == null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "暂无信息", "").toString();
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", listDTOS).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 修改收获地址
     */
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public String updateAddress(@RequestBody Map<String, Object> map) {
        try {
            log.info("修改收获地址：" + "token:" + map);
            String token = map.get("token").toString();
            Integer addressId = (Integer) map.get("addressId");
            String userName = map.get("userName").toString();
            String phone = map.get("phone").toString();
            String province = map.get("province").toString();
            String city = map.get("city").toString();
            String country = map.get("country").toString();
            String detailAddress = map.get("detailAddress").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            orderService.updateAddress(user, addressId, userName, phone, province, city, country, detailAddress);
            return JsonReturnUtil.getJsonIntReturn(0, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 删除收获地址
     */
    @RequestMapping(value = "/delAddress", method = RequestMethod.POST)
    public String delAddress(@RequestBody Map<String, Object> map) {
        try {
            log.info("删除收获地址：" + "token:" + map);
            String token = map.get("token").toString();
            Integer addressId = (Integer) map.get("addressId");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            orderService.delAddress(addressId);
            return JsonReturnUtil.getJsonIntReturn(0, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 新增收获地址
     */
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public String addAddress(@RequestBody Map<String, Object> map) {
        try {
            log.info("新增收获地址：" + "token:" + map);
            String token = map.get("token").toString();
            String userName = map.get("userName").toString();
            String phone = map.get("phone").toString();
            String province = map.get("province").toString();
            String city = map.get("city").toString();
            String country = map.get("country").toString();
            String detailAddress = map.get("detailAddress").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            orderService.addAddress(user, userName, phone, province, city, country, detailAddress);
            return JsonReturnUtil.getJsonIntReturn(0, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @PostMapping(value = "/wxBalancePay")
    public Request<Map<String, String>> wxBalancePay(@RequestBody Map<String, Object> map,

                                                     HttpServletResponse response, HttpServletRequest request) throws Exception {

        log.info("订单支付：" + map);
        String openId = map.get("openId").toString();
        String orderNo = map.get("orderNo").toString();
        Member user = memberRepository.findByOpenId(openId);
        //检查库存
        Order order = orderService.findByOrderNo(orderNo);
        if (order != null) {
            Integer suec = orderService.checkStockMoreOrder(order);
            if (suec != 0) {
                return ResultBuilder.failStock();
            }

            if (user != null) {
                String ip = WechatService.getIpAddr(request);
                WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(Integer.valueOf(user.getWineryId().toString()));
                BigDecimal totalPrice = BigDecimal.valueOf(Double.valueOf(map.get("price").toString()));
                String requestParam = payService.getPayParam(openId, String.valueOf(totalPrice.multiply(new BigDecimal(100)).intValue()), ip, "线下活动", orderNo, "OW");
                Map<String, String> result = payService.requestWechatPayServer(requestParam);
                Map<String, String> datas = new TreeMap<>();
                if (result.get("return_code").equals("SUCCESS")) {
                    String prepayId = result.get("prepay_id");
                    datas.put("appId", wineryConfigure.getAppId());
                    datas.put("package", "prepay_id=" + prepayId);
                    datas.put("signType", "MD5");
                    datas.put("timeStamp", Long.toString(new Date().getTime()).substring(0, 10));
                    datas.put("nonceStr", WXUtil.getNonceStr());
                    String sign = SignatureUtils.signature(datas, wineryConfigure.getWxPayKey());
                    datas.put("paySign", sign);
                    return ResultBuilder.success(datas);
                }
            }
        }
        return ResultBuilder.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/orderWxBalanceNotifyUrl")
    public String orderWxBalanceNotifyUrl(HttpServletRequest request) {
        try {
            Map<String, String> map = payService.getCallbackParams(request);
            log.info("订单支付回调接口：" + map);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderNo = map.get("out_trade_no");
                System.out.println("支付成功");
                //支付成功之后的逻辑
                Order order = orderService.findByOrderNo(orderNo);
                orderService.balance(order.getUserId(), order, "DW");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    /* *
     * 订单线下支付
     * @Author        zyj
     * @Date          2018/12/21 16:45
     * @Description
     * */
    @RequestMapping(value = "/offlinePay", method = RequestMethod.POST)
    public String offlinePay(@RequestBody Map<String, Object> map) {
        try {
            String token = map.get("token").toString();
            log.info("订单线下支付：" + "token:" + token);
            Integer orderId = (Integer) map.get("orderId");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Order order = orderService.findByOrderId(orderId);
            orderService.offlinePay(order);
            return JsonReturnUtil.getJsonIntReturn(0, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
}

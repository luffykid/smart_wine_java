package com.changfa.frame.website.controller.cart;


import com.changfa.frame.data.dto.wechat.CartSettlementDTO;
import com.changfa.frame.data.dto.wechat.NewProdListDTO;
import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.prod.ProdProdSpec;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.jpa.cart.CartService;
import com.changfa.frame.service.jpa.theme.ThemeService;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static Logger log = LoggerFactory.getLogger(CartController.class);


    @Autowired
    private ThemeService themeService;
    @Autowired
    private CartService cartService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    public String addCart(@RequestBody Map<String, Object> map) {
        try {
            log.info("加入购物车：" + "token:" + map);
            String token = map.get("token").toString();
            Integer prodId = Integer.valueOf(map.get("prodId").toString());
            Integer amount = Integer.valueOf(map.get("amount").toString());
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Prod prod = themeService.checkProdId(prodId);
            if (prod == null) {
                return JsonReturnUtil.getJsonReturn(37002, "prodId[" + prodId + "]不存在");
            }
            Integer stock = cartService.checkStock(prod);
            if (amount > stock) {
                return JsonReturnUtil.getJsonReturn(37003, "库存不足,加入商品失败");
            }
            ProdProdSpec prodSpec = themeService.getProdSpecId(prod);
            BigDecimal bigDecimal = cartService.addCart(user, prod, prodSpec, amount);
            Map<String, Integer> map1 = new HashMap<>();
            if (bigDecimal == null) {
                map1.put("count", 0);
            } else {
                map1.put("count", bigDecimal.intValue());
            }
            return JsonReturnUtil.getJsonObjectReturn(0, "", "加入购物车成功", map1).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/cartList", method = RequestMethod.POST)
    public String cartList(@RequestBody Map<String, Object> map) {
        try {
            log.info("购物车列表：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "],请重新登录");
            }
            List<NewProdListDTO> cartList = cartService.cartList(user);
            if (cartList == null || cartList.size() == 0) {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无信息");
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", cartList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/cartCount", method = RequestMethod.POST)
    public String cartCount(@RequestBody Map<String, Object> map) {
        try {
            log.info("我的购物车数量：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            BigDecimal bigDecimal = cartService.cartCount(user);
            Map<String, Integer> map1 = new HashMap<>();
            if (bigDecimal == null) {
                map1.put("count", 0);
            } else {
                map1.put("count", bigDecimal.intValue());
            }
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", map1).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/cartSettlement", method = RequestMethod.POST)
    public String cartSettlement(@RequestBody Map<String, Object> map) {
        try {
            log.info("购物车点击结算：" + "token:" + map);
            String token = map.get("token").toString();
            List<Integer> cartId = (List<Integer>) map.get("cartId");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            CartSettlementDTO cartSettlement = cartService.cartSettlement(user, cartId);
            if (cartSettlement == null || cartSettlement.getNewProdLists() == null || cartSettlement.getNewProdLists().size() == 0) {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无信息");
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", cartSettlement).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/directBuy", method = RequestMethod.POST)
    public String directBuy(@RequestBody Map<String, Object> map) {
        try {
            log.info("立即购买：" + "token:" + map);
            String token = map.get("token").toString();
            Integer prodId = (Integer) map.get("prodId");
            Integer amount = (Integer) map.get("amount");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            CartSettlementDTO cartSettlement = cartService.directBuy(user, prodId, amount);
            if (cartSettlement == null || cartSettlement.getNewProdLists() == null || cartSettlement.getNewProdLists().size() == 0) {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无信息");
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", cartSettlement).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/voucherListCanUse", method = RequestMethod.POST)
    public String voucherListCanUse(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取所有线下可用券：" + "token:" + map);
            String token = map.get("token").toString();
            BigDecimal price = new BigDecimal(Double.valueOf(map.get("price").toString()));
            List<Integer> prodIds = (List<Integer>) map.get("prodIds");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<VoucherInstDTO> voucherInstDTOList = cartService.findOfflineVoucherListCanUse(user, price, prodIds);
            if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/maxVoucherCanUse", method = RequestMethod.POST)
    public String maxVoucherCanUse(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取最大优惠券：" + "token:" + map);
            String token = map.get("token").toString();
            BigDecimal price = new BigDecimal(Double.valueOf(map.get("price").toString()));
            List<Integer> prodIds = (List<Integer>) map.get("prodIds");
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Map voucherInstDTO = cartService.findOfflineMaxVoucherCanUse(user, price, prodIds);
            if (voucherInstDTO != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTO).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    /* *
     * 修改购物车数量
     * @Author        zyj
     * @Date          2019/1/7 10:45
     * @Description
     * */
    @RequestMapping(value = "/updateCartSum", method = RequestMethod.POST)
    public String updateCartSum(@RequestBody Map<String, Object> map) {
        try {
            log.info("修改购物车数量：" + "token:" + map);
            String token = map.get("token").toString();
            Integer quantity = Integer.valueOf(map.get("quantity").toString());
            Integer cartId = Integer.valueOf(map.get("cartId").toString());
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            cartService.updateCartSum(quantity, cartId);
            return JsonReturnUtil.getJsonReturn(0, "200", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody Map<String, Object> map) {
        try {
            log.info("删除购物车：" + "token:" + map);
            String token = map.get("token").toString();
            Integer cartId = Integer.valueOf(map.get("cartId").toString());
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            cartService.deleteCart(cartId);
            return JsonReturnUtil.getJsonReturn(0, "200", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
}

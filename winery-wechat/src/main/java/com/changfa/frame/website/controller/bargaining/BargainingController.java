package com.changfa.frame.website.controller.bargaining;

import com.changfa.frame.data.dto.wechat.*;
import com.changfa.frame.data.entity.bargaining.BargainingCommodity;
import com.changfa.frame.data.entity.bargaining.BargainingUser;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.UserAddress;
import com.changfa.frame.data.repository.bargaining.BargainingCommodityRepository;
import com.changfa.frame.data.repository.bargaining.BargainingUserRepository;
import com.changfa.frame.service.jpa.bargaining.BargainingService;
import com.changfa.frame.service.jpa.theme.ThemeService;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.website.common.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(value = "小程序端 砍价管理", tags = "小程序端 砍价管理")
@RestController
@RequestMapping("/bargaining")
public class BargainingController {

    private static Logger log = LoggerFactory.getLogger(BargainingController.class);

    @Autowired
    private MemberService memberService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private BargainingService bargainingService;
    @Autowired
    private BargainingCommodityRepository bargainingCommodityRepository;
    @Autowired
    private BargainingUserRepository bargainingUserRepository;

    @ApiOperation(value = "砍价商品列表", notes = "砍价商品列表")
    @RequestMapping(value = "/bargainingList", method = RequestMethod.POST)
    public String bargainingList(@RequestBody Map<String, Object> map) {
        try {
            log.info("砍价商品列表:" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<BargainingWeichatListDTO> bcListDTOS = bargainingService.bargainingListWechat(user, "");
            if (bcListDTOS!=null && bcListDTOS.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", bcListDTOS).toString();
            }else{
                return JsonReturnUtil.getJsonReturn(0, "100","暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "砍价商品列表搜索", notes = "砍价商品列表搜索")
    @RequestMapping(value = "/bargainingListSearch", method = RequestMethod.POST)
    public String bargainingListSearch(@RequestBody Map<String, Object> map) {
        try {
            log.info("砍价商品列表搜索:" + "token:" + map);
            String token = map.get("token").toString();
            String input = map.get("input").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<BargainingWeichatListDTO> bcListDTOS = bargainingService.bargainingListWechat(user, input);
            if (bcListDTOS!=null && bcListDTOS.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", bcListDTOS).toString();
            }else{
                return JsonReturnUtil.getJsonReturn(0, "100","暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "砍价商品详情", notes = "砍价商品详情")
    @RequestMapping(value = "/bargainingProdDeatil", method = RequestMethod.POST)
    public String bargainingProdDeatil(@RequestBody Map<String, Object> map) {
        try {
            log.info("砍价商品详情:token:" + map);
            String token = map.get("token").toString();
            String bargainingId1 = map.get("bargainingId").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer bargainingId = null;
            if (!bargainingId1.equals("")) {
                bargainingId = (Integer) map.get("bargainingId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品信息有误");
            }
            BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingId);
            if(null == bargainingCommodity ){
                return JsonReturnUtil.getJsonReturn(37003, "商品信息有误");
            }
            //砍价信息
            BargainingPordDetailWeichatDTO bargainingDetail =bargainingService.bargainingProdWchat(user,bargainingId);

            //商品详情页面
            Prod prod = themeService.checkProdId(bargainingCommodity.getProdId());
            if (prod == null) {
                return JsonReturnUtil.getJsonReturn(37002, "砍价的商品不存在");
            }
            ProdDetailDTO themeList = themeService.getProdDetail(user, prod);

            Map<String, Object> map1 = new HashMap<>();
            map1.put("bargainingDetail", bargainingDetail);
            map1.put("themeList", themeList);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "查询成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "发起砍价按钮（去砍价按钮）", notes = "发起砍价按钮（去砍价按钮）")
    @RequestMapping(value = "/bargainingButton", method = RequestMethod.POST)
    public String bargainingButton(@RequestBody Map<String, Object> map) {
        try {
            log.info("去砍价:token:" + map);
            String token = map.get("token").toString();
            String bargainingId1 = map.get("bargainingId").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer bargainingId = null;
            if (!bargainingId1.equals("")) {
                bargainingId = (Integer) map.get("bargainingId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品信息有误");
            }
            Integer bargainingUserId = bargainingService.bargainingButton(user, bargainingId);
            if(null == bargainingUserId  || bargainingUserId.equals("")){
                return JsonReturnUtil.getJsonReturn(37003, "发起商品砍价失败");
            }
            BargainingDetailWeichatDTO bdwDTO = bargainingService.bargainingDetail(user,bargainingUserId);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("bdwDTO", bdwDTO);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "发起砍价成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }



    @ApiOperation(value = "砍价详情", notes = "砍价详情")
    @RequestMapping(value = "/bargainingDetail", method = RequestMethod.POST)
    public String bargainingDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("砍价详情:token:" + map);
            String token = map.get("token").toString();
            String bargainingUserId1 = map.get("bargainingUserId").toString();//砍价用户表id
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer bargainingUserId = null;
            if (!bargainingUserId1.equals("")) {
                bargainingUserId = (Integer) map.get("bargainingUserId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品用户表信息有误");
            }
            BargainingDetailWeichatDTO bdwDTO = bargainingService.bargainingDetail(user,bargainingUserId);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("bdwDTO", bdwDTO);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "查询成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "帮ta砍一刀按钮", notes = "帮ta砍一刀按钮")
    @RequestMapping(value = "/bargainingHelp", method = RequestMethod.POST)
    public String bargainingHelp(@RequestBody Map<String, Object> map) {
        try {
            log.info("帮ta砍:token:" + map);
            String token = map.get("token").toString();
            String bargainingUserId1 = map.get("bargainingUserId").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer bargainingUserId = null;
            if (!bargainingUserId1.equals("")) {
                bargainingUserId = (Integer) map.get("bargainingUserId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品用户表信息有误");
            }

            bargainingService.bargainingHelp(user,bargainingUserId);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "帮砍成功", "").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    //当前价购买按钮   进入付款页面
    @ApiOperation(value = "当前价购买按钮", notes = "当前价购买按钮")
    @RequestMapping(value = "/buyNowPrice", method = RequestMethod.POST)
    public String buyNowPrice(@RequestBody Map<String, Object> map) {
        try {
            log.info("当前价购买按钮:token:" + map);
            String token = map.get("token").toString();
            String bargainingUserId1 = map.get("bargainingUserId").toString();
            String nowPrice1 = map.get("nowPrice").toString();  //当前价格
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer bargainingUserId = null;
            if (!bargainingUserId1.equals("")) {
                bargainingUserId = (Integer) map.get("bargainingUserId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品用户表信息有误");
            }
            BigDecimal nowPrice = new BigDecimal(0);
            if (!nowPrice1.equals("")) {
                nowPrice = new BigDecimal(nowPrice1);
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "购买价格有误");
            }
            BargainingWeichatDTO bargainingWeichatDTO = bargainingService.buyNowPrice(user,bargainingUserId,nowPrice);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("bargainingWeichatDTO", bargainingWeichatDTO);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }




    @ApiOperation(value = "创建砍价订单(提交订单按钮)", notes = "创建砍价订单(提交订单按钮)")
    @RequestMapping(value = "/createBargaining", method = RequestMethod.POST)
    public String createBargaining(@RequestBody Map<String, Object> map) {
        try {
            log.info("创建砍价订单:token:" + map);
            String token = map.get("token").toString();
            String descri = map.get("descri").toString();
            Integer userAddressId = (Integer) map.get("userAddressId");
            String nowPrice1 = map.get("nowPrice").toString();  //当前价格
            String bargainingUserId1 = map.get("bargainingUserId").toString();
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
            Integer bargainingUserId = null;
            if (!bargainingUserId1.equals("")) {
                bargainingUserId = (Integer) map.get("bargainingUserId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品用户表信息有误");
            }
            BigDecimal nowPrice = new BigDecimal(0);
            if (!nowPrice1.equals("")) {
                nowPrice = new BigDecimal(nowPrice1);
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "购买价格有误");
            }
            BargainingUser bargainingUser = bargainingUserRepository.getOne(bargainingUserId);
            if(null != bargainingUser){
                BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingUser.getBargainingCommodity());
                if(null != bargainingCommodity){
                    //检查库存
                    Integer stock = bargainingService.checkStockOne(bargainingCommodity);
                    if (stock != 0) {
                        return JsonReturnUtil.getJsonReturn(37003, "库存不足");
                    }
                }
            }
            bargainingService.createBargaining(user,userAddress, descri, bargainingUser,nowPrice);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "创建砍价订单成功", "").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

        //*********************************************************************************************



}

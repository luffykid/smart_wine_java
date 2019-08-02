package com.changfa.frame.website.controller.assemble;

import com.changfa.frame.data.dto.saas.AssembleCommodityDTO;
import com.changfa.frame.data.dto.saas.AssembleCommodityListDTO;
import com.changfa.frame.data.dto.saas.ProdAssembleDTO;
import com.changfa.frame.data.dto.wechat.*;
import com.changfa.frame.data.entity.assemble.AssembleCommodity;
import com.changfa.frame.data.entity.assemble.AssembleList;
import com.changfa.frame.data.entity.cart.Cart;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.user.UserAddress;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.repository.assemble.AssembleCommodityRepository;
import com.changfa.frame.data.repository.assemble.AssembleListRepository;
import com.changfa.frame.data.repository.assemble.AssembleUserRepository;
import com.changfa.frame.service.assemble.AssembleService;
import com.changfa.frame.service.cart.CartService;
import com.changfa.frame.service.order.OrderService;
import com.changfa.frame.service.prod.ProdService;
import com.changfa.frame.service.theme.ThemeService;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.service.user.UserService;
import com.changfa.frame.service.voucher.UserVoucherService;
import com.changfa.frame.website.common.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "小程序端 拼团管理", tags = "小程序端 拼团管理")
@RestController
@RequestMapping("/assemble")
public class AssembleController {

    private static Logger log = LoggerFactory.getLogger(AssembleController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private AssembleService assembleService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssembleCommodityRepository assembleCommodityRepository;

    @Autowired
    private UserVoucherService userVoucherService;

    @Autowired
    private ThemeService themeService;
    @Autowired
    private AssembleListRepository assembleListRepository;
    @Autowired
    private AssembleUserRepository assembleUserRepository;

    @ApiOperation(value = "拼团商品列表", notes = "拼团商品列表")
    @RequestMapping(value = "/assembleList", method = RequestMethod.POST)
    public String assembleList(@RequestBody Map<String, Object> map) {
        try {
            log.info("拼团商品列表:" + "token:" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<AssembleWeichatListDTO> acListDTOS = assembleService.assembleListWechat(user, "");
            if (acListDTOS!=null && acListDTOS.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", acListDTOS).toString();
            }else{
                return JsonReturnUtil.getJsonReturn(0, "100","暂无");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "拼团商品列表搜索", notes = "拼团商品列表搜索")
    @RequestMapping(value = "/assembleListSearch", method = RequestMethod.POST)
    public String assembleListSearch(@RequestBody Map<String, Object> map) {
        try {
            log.info("拼团商品列表:" + "token:" + map);
            String token = map.get("token").toString();
            String input = map.get("input").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<AssembleWeichatListDTO> acListDTOS = assembleService.assembleListWechat(user, input);
            if (acListDTOS!=null && acListDTOS.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", acListDTOS).toString();
            }else{
                return JsonReturnUtil.getJsonReturn(0, "100","暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "去开团按钮", notes = "去开团按钮")
    @RequestMapping(value = "/assembleButton", method = RequestMethod.POST)
    public String assembleButton(@RequestBody Map<String, Object> map) {
        try {
            log.info("去开团:token:" + map);
            String token = map.get("token").toString();
            String assembleId1 = map.get("assembleId").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer assembleId = null;
            if (!assembleId1.equals("")) {
                assembleId = (Integer) map.get("assembleId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品信息有误");
            }
            AssembleWeichatDTO assembleWeichatDTO = assembleService.assembleButton(user, assembleId);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("assembleWeichatDTO", assembleWeichatDTO);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "创建开团订单(提交订单按钮)", notes = "创建开团订单(提交订单按钮)")
    @RequestMapping(value = "/createAssemble", method = RequestMethod.POST)
    public String createAssemble(@RequestBody Map<String, Object> map) {
        try {
            log.info("创建开团订单:token:" + map);
            String token = map.get("token").toString();
            String descri = map.get("descri").toString();
            Integer userAddressId = (Integer) map.get("userAddressId");
            String voucherId1 = map.get("voucherId").toString();//优惠券
            Integer voucherId = null;
            if (!voucherId1.equals("")) {
                voucherId = (Integer) map.get("voucherId");
            }
            String assembleId1 = map.get("assembleId").toString();

            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            UserAddress userAddress = null;
            if (userAddressId != -1) {
                userAddress = userService.checkUserAddressId(userAddressId);
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

            Integer assembleId = null;
            if (!assembleId1.equals("")) {
                assembleId = (Integer) map.get("assembleId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品信息有误");
            }
            AssembleCommodity assembleCommodity = assembleCommodityRepository.getOne(assembleId);
            if(null != assembleCommodity ){
                //检查剩余团购数量是否满足
                Integer assemblenum = assembleService.checkAssemblenum(assembleCommodity);
                if (assemblenum != 0) {
                    return JsonReturnUtil.getJsonReturn(37003, "剩余团购数量不足");
                }

                //检查库存
                Integer stock = assembleService.checkStockOne(assembleCommodity);
                if (stock != 0) {
                    return JsonReturnUtil.getJsonReturn(37003, "库存不足");
                }
            }
            assembleService.createAssemble(user,userAddress, userVoucher, descri, assembleCommodity);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "创建开团订单成功", "").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "拼团详情", notes = "拼团详情")
    @RequestMapping(value = "/assembleDeatil", method = RequestMethod.POST)
    public String assembleDeatil(@RequestBody Map<String, Object> map) {
        try {
            log.info("拼团详情:token:" + map);
            String token = map.get("token").toString();
            String assembleListId1 = map.get("assembleListId").toString();
            Integer assembleListId = null;
            if (!assembleListId1.equals("")) {
                assembleListId = (Integer) map.get("assembleListId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "团队信息有误");
            }

            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            AssembleWeichatListDTO assembleWeichatListDTO =assembleService.assembleDetailWechat(assembleListId); //拼团信息
            List<AssembleUserWeichatListDTO> assembleUserWeichatListDTOList =  assembleService.assembleUserWechat(assembleListId);//拼团用户信息
            List<AssembleWeichatListDTO> acListDTOS = assembleService.assembleListWechat(user, assembleListId);  //更多拼团商品
            if(assembleWeichatListDTO.getAssemblePreson().equals(assembleUserWeichatListDTOList.size())){
                assembleWeichatListDTO.setIsAssembleSuccess(1); //拼团成功
            }else{
                assembleWeichatListDTO.setIsAssembleSuccess(0);
            }
            Map<String, Object> map1 = new HashMap<>();
            map1.put("assembleWeichatListDTO", assembleWeichatListDTO);
            map1.put("assembleUserWeichatListDTOList", assembleUserWeichatListDTOList);
            map1.put("acListDTOS", acListDTOS);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "查询成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "拼团商品详情", notes = "拼团商品详情")
    @RequestMapping(value = "/assembleProdDeatil", method = RequestMethod.POST)
    public String assembleProdDeatil(@RequestBody Map<String, Object> map) {
        try {
            log.info("拼团商品详情:token:" + map);
            String token = map.get("token").toString();
            String assembleId1 = map.get("assembleId").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer assembleId = null;
            if (!assembleId1.equals("")) {
                assembleId = (Integer) map.get("assembleId1");
            }else{
                return JsonReturnUtil.getJsonReturn(37003, "商品信息有误");
            }
            AssembleCommodity assembleCommodity = assembleCommodityRepository.getOne(assembleId);
            if(null == assembleCommodity ){
                return JsonReturnUtil.getJsonReturn(37003, "商品信息有误");
            }

            //拼团信息
            AssemblePordDetailWeichatDTO assembleDetail =assembleService.assembleProdWchat(user,assembleId);
            //商品详情页面
            Prod prod = themeService.checkProdId(assembleCommodity.getProdId());
            if (prod == null) {
                return JsonReturnUtil.getJsonReturn(37002, "团购的商品不存在");
            }
            ProdDetailDTO themeList = themeService.getProdDetail(user, prod);
            //已经参团且人数未满的列表
            List<AssembleListWeichatDTO> assembleListList =  assembleService.assembleProdWchat(assembleCommodity, assembleId);

            Map<String, Object> map1 = new HashMap<>();
            map1.put("assembleDetail", assembleDetail);
            map1.put("themeList", themeList);
            map1.put("assembleListList", assembleListList);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "查询成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "我要参团按钮", notes = "我要参团按钮")
    @RequestMapping(value = "/joinAssembleButton", method = RequestMethod.POST)
    public String joinAssembleButton(@RequestBody Map<String, Object> map) {
        try {
            log.info("去开团:token:" + map);
            String token = map.get("token").toString();
            String assembleListId1 = map.get("assembleListId").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Integer assembleListId = null;
            if (!assembleListId1.equals("")) {
                assembleListId = (Integer) map.get("assembleListId1");
            } else {
                return JsonReturnUtil.getJsonReturn(37003, "拼团列表信息有误");
            }
            AssembleWeichatDTO assembleWeichatDTO = assembleService.joinAssembleButton(user, assembleListId);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("assembleWeichatDTO", assembleWeichatDTO);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "成功", map1).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }



    @ApiOperation(value = "参团订单(提交订单按钮)", notes = "参团订单(提交订单按钮)")
    @RequestMapping(value = "/joinAssemble", method = RequestMethod.POST)
    public String joinAssemble (@RequestBody Map < String, Object > map){
        try {
            log.info("创建开团订单:token:" + map);
            String token = map.get("token").toString();
            String descri = map.get("descri").toString();
            Integer userAddressId = (Integer) map.get("userAddressId");
            String voucherId1 = map.get("voucherId").toString();//优惠券
            Integer voucherId = null;
            if (!voucherId1.equals("")) {
                voucherId = (Integer) map.get("voucherId");
            }
            String assembleListId1 = map.get("assembleListId").toString();

            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            UserAddress userAddress = null;
            if (userAddressId != -1) {
                userAddress = userService.checkUserAddressId(userAddressId);
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

            Integer assembleListId = null;
            if (!assembleListId1.equals("")) {
                assembleListId = (Integer) map.get("assembleListId1");
            } else {
                return JsonReturnUtil.getJsonReturn(37003, "商品拼团信息有误");
            }

            AssembleList assembleList = assembleListRepository.getOne(assembleListId);
            AssembleCommodity assembleCommodity = null;
            if(null != assembleList){
                assembleCommodity = assembleCommodityRepository.getOne(assembleList.getAssembleCommodity());
            }
            if (null != assembleCommodity) {
                //检查剩余团购数量是否满足
                Integer assemblenum = assembleService.checkAssemblenum(assembleCommodity);
                if (assemblenum != 0) {
                    return JsonReturnUtil.getJsonReturn(37003, "剩余团购数量不足");
                }

                //检查库存
                Integer stock = assembleService.checkStockOne(assembleCommodity);
                if (stock != 0) {
                    return JsonReturnUtil.getJsonReturn(37003, "库存不足");
                }
            }
            assembleService.joinAssemble(user, userAddress, userVoucher, descri, assembleList);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "参团成功", "").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

        //*********************************************************************************************



}

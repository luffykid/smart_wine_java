package com.changfa.frame.website.controller.theme;


import com.changfa.frame.data.dto.wechat.*;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.theme.Theme;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.jpa.theme.ThemeService;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/theme")
public class ThemeController {

    private static Logger log = LoggerFactory.getLogger(ThemeController.class);

    @Autowired
    private ThemeService themeService;

    @Autowired
    private MemberService memberService;

    //获取当前商城活动主题轮播图
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String themeList(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取当前商城活动主题轮播图：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<ThemeListDTO> themeList = themeService.getThemeList(user);
            if (themeList != null && themeList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //点击主题轮播图跳详情
    @RequestMapping(value = "/themeDetail", method = RequestMethod.POST)
    public String themeDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("点击主题轮播图跳详情：" + "token:" + map);
            Integer id = Integer.valueOf(map.get("id").toString());
            Member user = null;
            if (map.get("token") != null && !map.get("token").equals("")) {
                String token = map.get("token").toString();
                user = memberService.checkToken(token);
                if (user == null) {
                    return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
                }
            }
            Theme theme = themeService.checkId(id);
            if (theme == null) {
                return JsonReturnUtil.getJsonReturn(37002, "id[" + id + "]不存在");
            }
            ThemeDetailDTO themeList = themeService.themeDetail(theme, user);
            if (themeList != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //商品详情页面
    @RequestMapping(value = "/prodDetail", method = RequestMethod.POST)
    public String prodDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("商品详情页面：" + "token:" + map);
            Integer id = Integer.valueOf(map.get("id").toString());
            Member user = null;
            if (map.get("token") != null && !map.get("token").equals("")) {
                String token = map.get("token").toString();
                user = memberService.checkToken(token);
                if (user == null) {
                    return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
                }
            }
            Prod prod = themeService.checkProdId(id);
            if (prod == null) {
                return JsonReturnUtil.getJsonReturn(37002, "id[" + id + "]不存在");
            }
            ProdDetailDTO themeList = themeService.getProdDetail(user, prod);
            if (themeList != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //推荐商品
    @RequestMapping(value = "/discountList", method = RequestMethod.POST)
    public String discountList(@RequestBody Map<String, Object> map) {
        try {
            log.info("推荐商品：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<DiscountListDTO> themeList = themeService.getDiscountList(user);
            if (themeList != null && themeList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //积分兑换
    @RequestMapping(value = "/getProdChanged", method = RequestMethod.POST)
    public String getProdChanged(@RequestBody Map<String, Object> map) {
        try {
            log.info("推荐商品：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<DiscountListDTO> themeList = themeService.getProdChanged(user);
            if (themeList != null && themeList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //热门商品
    @RequestMapping(value = "/popularList", method = RequestMethod.POST)
    public String popularList(@RequestBody Map<String, Object> map) {
        try {
            log.info("热销商品：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<DiscountListDTO> themeList = themeService.getPopularList(user);
            if (themeList != null && themeList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //热销商品
    @RequestMapping(value = "/newProdList", method = RequestMethod.POST)
    public String newProdList(@RequestBody Map<String, Object> map) {
        try {
            log.info("热销商品：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<DiscountListDTO> themeList = themeService.getNewProdList(user);
            if (themeList != null && themeList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


    //商品列表
    @RequestMapping(value = "/getSearchProds", method = RequestMethod.POST)
    public String getSearchProds(@RequestBody Map<String, Object> map) {
        try {
            log.info("搜索商品：" + "token:" + map);
            String token = map.get("token").toString();
            String search = map.get("search").toString();
            String type = map.get("type").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<DiscountListDTO> themeList = themeService.getSearchProds(user, search, type);
            if (themeList != null && themeList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //选择规格
    @RequestMapping(value = "/getProdSpecDetail", method = RequestMethod.POST)
    public String getProdSpecDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("选择规格：" + "token:" + map);
            String token = map.get("token").toString();
            Integer prodId = Integer.valueOf(map.get("prodId").toString());
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            ProdSpecDTO themeList = themeService.getProdSpecDetail(prodId);
            if (themeList != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", themeList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "酒旗星推荐商品", notes = "酒旗星推荐商品")
    @RequestMapping(value = "/operateProdList", method = RequestMethod.POST)
    public String operateProdList(@RequestBody Map<String, Object> map) {
        try {
            log.info("酒旗星推荐商品：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<DiscountListDTO> operateProds = themeService.operateProdList(user);
            if (operateProds != null && operateProds.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", operateProds).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


}

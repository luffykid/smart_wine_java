package com.changfa.frame.website.controller.banner;


import com.changfa.frame.data.dto.wechat.BannerDTO;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.banner.BannerService;
import com.changfa.frame.service.user.MemberService;
import com.changfa.frame.website.common.JsonReturnUtil;
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
@RequestMapping("/banner")
public class BannerController {

    private static Logger log = LoggerFactory.getLogger(BannerController.class);


    @Autowired
    private BannerService bannerService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String userInfo(@RequestBody Map<String, Object> map) {
        try {
            log.info("首页轮播图图片列表：" + "token:" + map);
            String token = map.get("token").toString();
            String type = map.get("type").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<BannerDTO> bannerDTOList = bannerService.getBanner(user,type);

            if (bannerDTOList!=null && bannerDTOList.size()>0) {

                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", bannerDTOList).toString();
            }else{
                return JsonReturnUtil.getJsonReturn(0, "100","暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }

    }

}

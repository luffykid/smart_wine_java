package com.changfa.frame.website.controller.wine;

import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.service.jpa.wine.WineService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/wine")
public class WineController {

    private static Logger log = LoggerFactory.getLogger(WineController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private WineService wineService;

    @RequestMapping(value = "/getWineDetail",method = RequestMethod.POST)
    public String getWineDetail(@RequestBody Map<String,Object> map){
        try {
            log.info("酒流水：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Map<String,Object> list = wineService.getWineDetailW(Integer.valueOf(user.getId().toString()));
            if (list!=null && list.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "操作成功",list).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


}

package com.changfa.frame.website.controller.deposit;

import com.changfa.frame.data.dto.wechat.UserDepositDetailDTO;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.deposit.DepositRuleService;
import com.changfa.frame.service.deposit.UserBalanceService;
import com.changfa.frame.service.deposit.UserDepositDetailService;
import com.changfa.frame.service.user.MemberService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    private static Logger log = LoggerFactory.getLogger(DepositController.class);

    @Autowired
    public MemberService memberService;

    @Autowired
    public UserDepositDetailService userDepositDetailService;

    @Autowired
    public UserBalanceService userBalanceService;

    @Autowired
    public DepositRuleService depositRuleService;

    @RequestMapping(value = "/detailList", method = RequestMethod.POST)
    public String userInfo(@RequestBody Map<String, Object> map) {
        try {
            log.info("储值流水：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<UserDepositDetailDTO> userDepositDetailDTOList = userDepositDetailService.getUserDepositDetail(user);

            if (userDepositDetailDTOList != null && userDepositDetailDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", userDepositDetailDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "200", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    /* *
     * 储值页面（显示余额与规则）
     * @Author        zyj
     * @Date          2018/10/18 11:19
     * @Description
     * */
    @RequestMapping(value = "/depositRule", method = RequestMethod.POST)
    public String depositRule(@RequestBody Map<String, Object> map) {
        try {
            log.info("储值页面：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            Map<String, Object> depositRuleMap = new Hashtable<>();

            depositRuleMap.put("balance", userBalanceService.findByUserId(Integer.valueOf(user.getId().toString())).getBalance());
            depositRuleMap.put("depositRuleDetail", depositRuleService.getDepositRule(user));
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", depositRuleMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
}

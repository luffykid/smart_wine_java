package com.changfa.frame.website.controller.voucher;


import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.service.jpa.voucher.VoucherInstService;
import com.changfa.frame.website.utils.JsonReturnUtil;
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
@RequestMapping("/voucher")
public class VoucherInstController {

    private static Logger log = LoggerFactory.getLogger(VoucherInstController.class);

    @Autowired
    private VoucherInstService voucherInstService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String voucherList(@RequestBody Map<String,Object> map){
        try {
            log.info("获取当前用户所有优惠券：" + "token:" + map);
            String token = map.get("token").toString();
            String status = map.get("status").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<VoucherInstDTO> voucherInstDTOList = voucherInstService.getVoucherInstList(user,status);

            if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

}

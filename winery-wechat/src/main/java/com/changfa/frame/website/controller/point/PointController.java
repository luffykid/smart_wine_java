package com.changfa.frame.website.controller.point;


import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.jpa.point.PointDetailService;
import com.changfa.frame.service.jpa.point.PointRewardRuleService;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/point")
public class PointController {

    private static Logger log = LoggerFactory.getLogger(PointController.class);


    @Autowired
    private MemberService memberService;

    @Autowired
    private PointDetailService pointDetailService;

    @Autowired
    private PointRewardRuleService pointRewardRuleService;

    @RequestMapping("/detail")
    public String detail(@RequestBody Map<String,Object> map){
        try {
            log.info("积分详情：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

           Map<String,Object> pointDetail =  pointDetailService.pointDetailList(user);

            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", pointDetail).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping("/pointVoucherList")
    public String pointVoucherList(@RequestBody Map<String,Object> map){
        try {
            log.info("获取积分可兑换的券列表：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<VoucherInstDTO> voucherInstDTOList = pointRewardRuleService.getPointToVoucher(user);
            if (voucherInstDTOList!=null && voucherInstDTOList.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTOList).toString();
            }else{
                return JsonReturnUtil.getJsonReturn(1,"100","暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


     /* *
        * 积分换券操作
        * @Author        zyj
        * @Date          2018/11/23 10:25  
        * @Description
      * */
    @RequestMapping("/pointToVoucher")
    public String pointToVoucher(@RequestBody Map<String,Object> map){
        try {
            log.info("积分换券操作：" + "token:" + map);
            String token = map.get("token").toString();
            Integer voucherId = Integer.valueOf(map.get("voucherId").toString());
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            Boolean pointToVoucher = pointRewardRuleService.pointToVoucher(user,voucherId);
            if (pointToVoucher) {
                return JsonReturnUtil.getJsonReturn(0,"200","操作成功");
            }else{
                return JsonReturnUtil.getJsonReturn(1,"100","积分不足，兑换失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

     /* *
        * 赠券详情
        * @Author        zyj
        * @Date          2018/11/23 12:43  
        * @Description
      * */
    @RequestMapping("/getVoucherDetail")
    public String getVoucherDetail(@RequestBody Map<String,Object> map){
        try {
            log.info("赠券详情：" + "token:" + map);
            String token = map.get("token").toString();
            Integer voucherId = Integer.valueOf(map.get("voucherId").toString());
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            Map mapReturn = pointRewardRuleService.getVoucherDetail(user,voucherId);
            if (mapReturn!=null) {
                return JsonReturnUtil.getJsonObjectReturn(0,"200","查询成功",mapReturn).toString();
            }else{
                return JsonReturnUtil.getJsonReturn(1,"100","暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
}

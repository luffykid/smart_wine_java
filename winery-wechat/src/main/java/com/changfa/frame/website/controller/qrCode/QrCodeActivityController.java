package com.changfa.frame.website.controller.qrCode;


import com.changfa.frame.service.activity.QrCodeActivityService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.apache.xmlbeans.impl.jam.mutable.MAnnotatedElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/qrCode")
public class QrCodeActivityController {

    private static Logger log = LoggerFactory.getLogger(QrCodeActivityController.class);

    @Autowired
    private QrCodeActivityService qrCodeActivityService;

     /* *
        * 获取二维码跳转路径
        * @Author        zyj
        * @Date          2018/11/29 11:54  
        * @Description
      * */
    @RequestMapping(value = "/qrCodeType", method = RequestMethod.POST)
    public String getQrcode(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取二维码跳转路径：" + "wineryId:" + map);
            Integer wineryId = Integer.valueOf(map.get("wineryId").toString());
            Map returnMap = qrCodeActivityService.getQrcodeType(wineryId);
            System.out.println(returnMap);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", returnMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


}

package com.changfa.frame.website.controller.activity;

import com.changfa.frame.data.dto.saas.QRCodeDTO;
import com.changfa.frame.data.entity.activity.QrCodeActivity;
import com.changfa.frame.data.entity.activity.WineryQrCode;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.activity.QrCodeActivityService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Qrcode")
public class QrCodeActivityController {

    private static Logger log = LoggerFactory.getLogger(QrCodeActivityController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private QrCodeActivityService qrCodeActivityService;

    /* *
     * 新增活码
     * @Author        zyj
     * @Date          2018/12/19 18:28
     * @Description
     * */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam(value = "token")String token,@RequestParam(value = "type") String type,@RequestParam(value = "prodId")Integer prodId,@RequestParam(value = "activityId")Integer activityId,@RequestParam(value = "descri")String descri,@RequestParam(value = "themeId")Integer themeId) {
        try {
            log.info("新增活码+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);

            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                qrCodeActivityService.add(type,prodId,activityId,descri,adminUser,themeId);
                return JsonReturnUtil.getJsonReturn(0, "200", "创建成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     *获取所有可链接的活动商品
     * @Author        zyj
     * @Date          2018/12/20 10:15
     * @Description
     * */
    @RequestMapping(value = "/getActivityOrProd", method = RequestMethod.POST)
    public String getActivityOrProd(@RequestParam(value = "token") String token, @RequestParam(value = "type") String type) {
        try {
            log.info("获取所有可链接的活动商品+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<QRCodeDTO> qrCodeDTOList = qrCodeActivityService.getActivityOrProd(adminUser, type);
                if (qrCodeDTOList != null && qrCodeDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "创建成功", qrCodeDTOList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 列表
     * @Author        zyj
     * @Date          2018/12/20 11:10
     * @Description
     * */
    @RequestMapping(value = "/getQRCodeDTOList", method = RequestMethod.POST)
    public String getQRCodeDTOList(@RequestParam(value = "token") String token) {
        try {
            log.info("列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<QRCodeDTO> qrCodeDTOList = qrCodeActivityService.getQRCodeDTOList(adminUser);
                if (qrCodeDTOList != null && qrCodeDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "创建成功", qrCodeDTOList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/getQRCodeDTODetail", method = RequestMethod.POST)
    public String getQRCodeDTODetail(@RequestParam(value = "token") String token, @RequestParam(value = "id") Integer id) {
        try {
            log.info("列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                QrCodeActivity qrCodeActivity = qrCodeActivityService.getQrCodeActivity(id);
                WineryQrCode wineryQrCode = qrCodeActivityService.getWineryQrCode(qrCodeActivity.getWineryQRCodeId());
                QRCodeDTO qrCodeDTO = qrCodeActivityService.getQRCodeDetail(wineryQrCode, qrCodeActivity);
                if (qrCodeDTO != null) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "创建成功", qrCodeDTO).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

     /* *
        * 删除
        * @Author        zyj
        * @Date          2018/12/20 12:05
        * @Description
      * */
       @RequestMapping(value = "/delet", method = RequestMethod.POST)
    public String delet(@RequestParam(value = "token") String token, @RequestParam(value = "id") Integer id) {
        try {
            log.info("删除+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                qrCodeActivityService.delete(id);
                return JsonReturnUtil.getJsonIntReturn(0, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(value = "id")Integer id,@RequestParam(value = "token")String token,@RequestParam(value = "type") String type,@RequestParam(value = "prodId")Integer prodId,@RequestParam(value = "activityId")Integer activityId,@RequestParam(value = "descri")String descri,@RequestParam(value = "themeId")Integer themeId) {
        try {
            log.info("编辑+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                qrCodeActivityService.update(id,type,prodId,activityId,descri,adminUser,themeId);
                return JsonReturnUtil.getJsonIntReturn(0, "编辑成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


}

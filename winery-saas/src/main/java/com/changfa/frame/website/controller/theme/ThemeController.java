package com.changfa.frame.website.controller.theme;


import com.changfa.frame.data.dto.saas.AddTheMeDTO;
import com.changfa.frame.data.entity.theme.Theme;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.theme.ThemeService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theme")
public class ThemeController {

    private static Logger log = LoggerFactory.getLogger(ThemeController.class);

    @Autowired
    private ThemeService themeService;

    @Autowired
    private AdminUserService adminUserService;

    //添加主题
    @RequestMapping(value = "/addTheMe",method = RequestMethod.POST)
    public String addTheMe(@RequestBody AddTheMeDTO addTheMeDTO){
        try {
            log.info("添加主题：" + "token:" + addTheMeDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(addTheMeDTO.getToken());
            Theme theme = themeService.checkTheName(addTheMeDTO.getThemeName(),adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +addTheMeDTO.getToken());
            }
            else if(theme!=null){
                return JsonReturnUtil.getJsonIntReturn(1, "主题名称重复");
            }
            List<Theme> themes = themeService.checkTheMe(adminUser);
            if(themes!=null&&themes.size()>=3&&addTheMeDTO.getStatus().equals("A")){
                addTheMeDTO.setStatus("P");
            }
            themeService.addTheMe(addTheMeDTO,adminUser);
            return JsonReturnUtil.getJsonIntReturn(0, "添加主题成功" );
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //修改主题
    @RequestMapping(value = "/updateTheMe",method = RequestMethod.POST)
    public String updateTheMe(@RequestBody AddTheMeDTO addTheMeDTO){
        try {
            log.info("修改主题：" + "token:" + addTheMeDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(addTheMeDTO.getToken());
            Theme theme = themeService.checkTheName(addTheMeDTO.getThemeName(),adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +addTheMeDTO.getToken());
            }else if(theme!=null&&theme.getId()!=addTheMeDTO.getThemeId()){
                return JsonReturnUtil.getJsonIntReturn(1, "主题名称重复");
            }
            List<Theme> themes = themeService.checkTheMe(adminUser);
            if(themes!=null&&addTheMeDTO.getStatus().equals("A")&&themes.size()>=3){
                addTheMeDTO.setStatus("P");
            }
            themeService.updateTheMe(addTheMeDTO,adminUser);
            return JsonReturnUtil.getJsonIntReturn(0, "修改主题成功" );
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //主题详情
    @RequestMapping(value = "/theMeDetail",method = RequestMethod.POST)
    public String theMeDetail(@RequestParam("token")String token,@RequestParam("themeId")Integer themeId){
        try {
            log.info("主题详情：" + "token:" + token+"id"+themeId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            AddTheMeDTO addTheMeDTO = themeService.theMeDetail(themeId);
            return JsonReturnUtil.getJsonObjectReturn(0,"", "主题详情" ,addTheMeDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //主题列表
    @RequestMapping(value = "/theMeList",method = RequestMethod.POST)
    public String theMeList(@RequestParam("token") String token,/*@RequestParam("name") */String name){
        try {
            log.info("添加主题：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            List<Theme> theMeList = themeService.theMeList(adminUser,name);
            return JsonReturnUtil.getJsonObjectReturn(0, "" ,"",theMeList).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
    //停用启用
    @RequestMapping(value = "/openTheMe",method = RequestMethod.POST)
    public String openTheMe(@RequestParam("token") String token,@RequestParam("themeId") Integer themeId,@RequestParam("status") String status){
        try {
            log.info("停用启用：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            List<Theme> themes = themeService.checkTheMe(adminUser);
            if(themes!=null&&themes.size()>=3&&status.equals("A")){
                return JsonReturnUtil.getJsonIntReturn(1, "主题只可启用三个");
            }
            themeService.openTheMe(themeId,status);
            if(status.equals("A")){
                return JsonReturnUtil.getJsonIntReturn(0, "启用成功" );
            }else{
                return JsonReturnUtil.getJsonIntReturn(0, "停用成功" );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
    //删除
    @RequestMapping(value = "/delTheMe",method = RequestMethod.POST)
    public String delTheMe(@RequestParam("token") String token,@RequestParam("themeId") Integer themeId){
        try {
            log.info("删除：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            themeService.delTheMe(themeId);
            return JsonReturnUtil.getJsonIntReturn(0, "删除成功" );
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //置顶
    @RequestMapping(value = "/toppingTheMe",method = RequestMethod.POST)
    public String toppingTheMe(@RequestParam("token") String token,@RequestParam("themeId") Integer themeId){
        try {
            log.info("置顶：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            Theme theme = themeService.findOne(themeId);
            if(theme.getStatus().equals("P")){
                return JsonReturnUtil.getJsonIntReturn(1, "请先启用该主题");
            }
            themeService.toppingTheMe(themeId);
            return JsonReturnUtil.getJsonIntReturn(1, "置顶成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
}

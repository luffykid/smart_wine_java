package com.changfa.frame.website.controller.winerysight;

import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.service.mybatis.app.WinerySightService;
import com.changfa.frame.website.common.JsonReturnUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("winerySight")
public class WinerySightController {

    @Autowired
    private WinerySightService winerySightService;

    @RequestMapping("/selectPage")
    public String selectPage(@RequestBody WinerySight winerySight, PageInfo<WinerySight> pageInfo){
        try{
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "分页查询成功", winerySightService.selectList(winerySight,pageInfo)).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping("/queryById")
    public String queryById(@RequestBody WinerySight winerySight){
        try{
//            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", winerySightService.queryById(winerySight.getId())).toString();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
}

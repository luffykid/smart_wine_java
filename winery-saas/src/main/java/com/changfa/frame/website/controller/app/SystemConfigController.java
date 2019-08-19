package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.service.mybatis.app.SystemConfigService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.changfa.frame.website.utils.SettingUtils;
import org.apache.catalina.connector.CoyoteAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置控制器
 *
 * @author wyy
 * @date 2019-08-16 17:06
 */
@RestController("adminSystemConfigController")
@RequestMapping("/admin/systemConfig")
public class SystemConfigController extends BaseController {

    @Resource(name = "systemConfigServiceImpl")
    private SystemConfigService systemConfigService;

    /**
     * 更新系统配置
     *
     * @param setting 系统设置对象
     * @return
     */
    @RequestMapping(value = "/updateSysConfig", method = RequestMethod.GET)
    public Map<String,Object> updateSysConfig(Setting setting) {
        // 初始化返回对象
        HashMap<String, Object> returnMap = new HashMap<>();
//        throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);

        // 更新系统配置
//        systemConfigService.set(setting);
        String copyRight = SettingUtils.get().getCopyRight();
        System.out.println(copyRight);


        return getResult(null);
    }
}

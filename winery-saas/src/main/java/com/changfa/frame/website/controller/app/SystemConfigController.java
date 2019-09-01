package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.service.mybatis.app.SystemConfigService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统配置控制器
 *
 * @author wyy
 * @date 2019-08-16 17:06
 */
@Api(value = "系统配置控制器", tags = "系统配置控制器")
@RestController("adminSystemConfigController")
@RequestMapping("/admin/auth/systemConfig")
public class SystemConfigController extends BaseController {

    @Resource(name = "systemConfigServiceImpl")
    private SystemConfigService systemConfigService;

    /**
     * 更新系统配置
     *
     * @return
     */
    @ApiOperation(value = "获取系统配置", notes = "获取系统配置")
    @RequestMapping(value = "/getSysConfig", method = RequestMethod.GET)
    public Map<String, Object> getSysConfig() {
        // 更新系统配置
        Setting setting = systemConfigService.get(getCurWineryId());
        return getResult(setting);
    }

    /**
     * 更新系统配置
     *
     * @param setting 系统设置对象
     * @return
     */
    @ApiOperation(value = "更新系统配置", notes = "更新系统的配置")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "setting", value = "系统配置对象", dataType = "Setting"))
    @RequestMapping(value = "/updateSysConfig", method = RequestMethod.POST)
    public Map<String, Object> updateSysConfig(Setting setting) {
        // 更新系统配置
        systemConfigService.set(setting,1l);
        return getResult(null);
    }
}

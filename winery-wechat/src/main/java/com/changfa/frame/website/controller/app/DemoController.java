package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.service.mybatis.app.SystemConfigService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器编码范例
 *
 * @author wyy
 * @date 2019-08-20 19:57
 */
@Api(value = "控制器编码范例", tags = "控制器编码范例")
@RestController("adminSystemConfigController")
@RequestMapping("/wxMin/auth/systemConfig")
public class DemoController extends BaseController {

    // service层必须是接口、实现类方式，Spring为面向接口编程
    @Resource(name = "systemConfigServiceImpl")
    private SystemConfigService systemConfigService;

    /**
     * 更新系统配置
     *
     * @param setting 系统设置对象
     * @return
     */
    @ApiOperation(value = "更新系统配置", notes = "更新系统的配置")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "setting", value = "系统配置对象", dataType = "Setting"))
    @RequestMapping(value = "/updateSysConfig", method = RequestMethod.GET)
    public Map<String, Object> updateSysConfig(Setting setting) {
        // 正常Service层调用，控制层不能直接调用DAO层
        systemConfigService.set(setting);

        if (true) {
            // 必要的地方打印日志【日志使用占位符方式，如果打印堆栈信息，可以使用ExceptionUtils】
            log.info("此处有错误:{}","错误信息");

            // 如果需要返回错误提示【框架中错误返回均以全局异常处理】
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }

        // 如果返回对象信息，可以为简单类型、复合类型[Object、Map、List、model实体等]
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("name","小栗子");
        returnMap.put("model实体类", new Setting());
        returnMap.put("集合", new ArrayList<String>());

        return getResult(returnMap);
    }
}

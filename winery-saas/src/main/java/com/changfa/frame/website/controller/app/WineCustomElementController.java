package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.service.mybatis.app.WineCustomElementService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName WineCustomElementController
 * @Description
 * @Author 王天豪
 * @Date 2019/9/3 9:33 AM
 * @Version 1.0
 */

@Api(value = "酒贴管理",tags = "酒贴管理")
@RestController("adminWineCustomElement")
@RequestMapping("/admin/auth/WineCustom/Element")
public class WineCustomElementController extends BaseController {



    @Resource(name = "wineCustomElementServiceImpl")
    private WineCustomElementService wineCustomElementService;


    @ApiOperation(value = "新建酒贴",notes = "新建酒贴")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineCustomElement", value = "产品新建对象", dataType = "WineCustomElement"))
    public Map<String,Object> addWineCustomElement(WineCustomElement wineCustomElement){

        try {
            wineCustomElementService.addWineCustomElement(wineCustomElement);
        }catch (Exception e){
            log.info("com.changfa.frame.website.controller.app.WineCustomElementController addWineCustomElement :{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.ADD_FAILED);
        }
        return getResult("新建酒贴成功");
    }

}

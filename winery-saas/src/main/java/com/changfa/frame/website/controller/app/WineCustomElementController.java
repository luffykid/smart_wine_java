package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.service.mybatis.app.WineCustomAdvanceService;
import com.changfa.frame.service.mybatis.app.WineCustomElementService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

@Api(value = "定制化管理",tags = "定制化管理")
@RestController("adminWineCustomElement")
@RequestMapping("/admin/auth/WineCustom/Element")
public class WineCustomElementController extends BaseController {



    @Resource(name = "wineCustomAdvanceServiceImpl")
    private WineCustomAdvanceService wineCustomAdvanceService;
    @Resource(name = "wineCustomElementServiceImpl")
    private WineCustomElementService wineCustomElementService;

    @ApiOperation(value = "新建预制图",notes = "新建预制图")
    @ApiImplicitParams(@ApiImplicitParam(name = "winecustomAdcance", value = "预制图新建", dataType = "winecustomAdvance"))
    @RequestMapping(value = "/addWineCustomAdvance", method = RequestMethod.POST)
    public Map<String,Object> addElementAdvance(@RequestBody WineCustomAdvance wineCustomAdvance){
        log.info("com.changfa.frame.website.controller.app.WineCustomElementController addElementAdvance : {}",wineCustomAdvance);
        try {
            wineCustomAdvanceService.addWineCustomElementAdvance(wineCustomAdvance);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.ADD_FAILED);
        }
        return getResult("预制图新增成功");

    }


    @ApiOperation(value = "获取所有定制元素",notes = "获取所有定制元素")
    @RequestMapping(value = "/getAllWineCustomElement", method = RequestMethod.GET)
    public Map<String,Object> getAllWineCustomElement(){
       WineCustomElement wineCustomElement = new WineCustomElement();
       wineCustomElement.setElementStatus(WineCustomElement.ELEMENT_STATUS_ENUM.QY.getValue());
       return getResult(wineCustomElementService.selectWineCustomElement(wineCustomElement));
    }


}

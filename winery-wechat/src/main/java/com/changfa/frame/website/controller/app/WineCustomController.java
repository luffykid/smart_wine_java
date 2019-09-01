package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.ProdDetailService;
import com.changfa.frame.service.mybatis.app.ProdService;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(value = "定制酒接口", tags = "定制酒接口")
@RestController("wineCustomController")
@RequestMapping("/wxMini/auth/wineCustom/")
public class WineCustomController extends BaseController {

    @Resource(name = "wineCustomServiceImpl")
    private WineCustomService wineCustomService;

    @Resource(name = "prodServiceImpl")
    private ProdService prodServiceImpl;

    @Resource(name = "prodDetailServiceImpl")
    private ProdDetailService prodDetailServiceImpl;

    @ApiOperation(value = "获取定制酒列表")
    @GetMapping
    public Map<String, Object> getWineCustoms() {

        List<WineCustom> wineCustoms = wineCustomService.selectList(WineCustom.NULL);
        return getResult(wineCustoms);

    }

    @ApiOperation(value = "获取定制酒详情")
    @ApiImplicitParam(name = "id", value = "定制酒id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/{id}")
    public Map<String, Object> getWineCustom(@PathVariable("id") Long id) {

        WineCustom wineCustom = wineCustomService.getById(id);
        return getResult(wineCustom);

    }

    @ApiOperation(value = "获取定制酒的所有可定制元素")
    @ApiImplicitParam(name = "id", value = "定制酒id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/{id}/wineCustomElementContent/")
    public Map<String, Object> getWineCustomElementContentsUnderTheCustomWine(@PathVariable("id") Long id) {

        List<WineCustomElementContent> wineCustomElementContents
                    = wineCustomService.getWineCustomElementContentsUnderTheWineCustom(id);

        return getResult(wineCustomElementContents);

    }

    @ApiOperation(value = "获取定制元素中的预览图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "定制酒的id", required = true,
                              dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "elem_cont_id", value = "定制酒的元素内容id", required = true,
                              dataType = "Long", paramType = "path")
    })
    @GetMapping("{id}/wineCustomElementContent/{elem_cont_id}/")
    public Map<String, Object> getWineCustomAdvance(@PathVariable("id") Long id,
                                                    @PathVariable("elem_cont_id") Long elemContId) {

        return getResult(wineCustomService.getWineCustomAdvancesUnderTheWineCustomElementContent(id, elemContId));

    }

    @GetMapping("{id}/detail")
    public Map<String, Object> getWineCustomDetail(@PathVariable("id") Long id) {

        WineCustom wineCustom = wineCustomService.getById(id);

        Prod prod = prodServiceImpl.getProd(wineCustom.getProdId());

        ProdDetail queryForTheProd = new ProdDetail();
        queryForTheProd.setProdId(prod.getId());
        List<ProdDetail> prodDetails = prodDetailServiceImpl.selectList(queryForTheProd);

        prod.setProdDetailList(prodDetails);

        return getResult(prod);

    }



}

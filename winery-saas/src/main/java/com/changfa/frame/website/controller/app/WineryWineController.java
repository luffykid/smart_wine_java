package com.changfa.frame.website.controller.app;

import com.changfa.frame.service.mybatis.app.WineryWineService;
import com.changfa.frame.website.controller.common.BaseController;
import com.github.pagehelper.PageInfo;
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
 * 类名称:WineryWineController
 * 类描述:酒庄酒管理Controller
 * 创建人:wy
 * 创建时间:2019/8/24 14:41
 * Version 1.0
 */

@Api(value = "酒庄酒管理",tags = "酒庄酒管理")
@RestController("adminWineryWineController")
@RequestMapping("/admin/auth/WineryWine")
public class WineryWineController extends BaseController {

    @Resource(name = "wineryWineServiceImpl")
    private WineryWineService wineryWineService;


    /**
     * 查询产品列表
     * @param pageInfo 分页对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询产品列表",notes = "查询产品列表")
    @RequestMapping(value = "/getProdList", method = RequestMethod.POST)
    public Map<String, Object> getProdList(PageInfo pageInfo){
        if(wineryWineService.getWineryWineList(pageInfo).size() > 0){
            return getResult(wineryWineService.getWineryWineList(pageInfo));
        }
        return getResult("未查询到产品");

    }

    /**
     * 搜索产品
     * @param
     * @return Map<String, Object>
     *//*
    @ApiOperation(value = "搜索产品",notes = "搜索产品")
    @ApiImplicitParams({@ApiImplicitParam(name = "prodName", value = "产品名称", dataType = "String"),
            @ApiImplicitParam(name = "lableType", value = "产品类型", dataType = "Long")})
    @RequestMapping(value = "/findProdList", method = RequestMethod.POST)
    public Map<String, Object> findProdList(String prodName, Long lableType){
        return getResult(wineryWineService.findProd(prodName,lableType));
    }*/
}

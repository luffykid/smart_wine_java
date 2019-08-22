package com.changfa.frame.website.controller.app;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.model.app.WinerySightDetail;
import com.changfa.frame.service.mybatis.app.WinerySightService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 类名称:WinerySightController
 * 类描述:景点管理
 * 创建人:WY
 * 创建时间:2019/8/20 15:34
 * Version 1.0
 */

@Api(value = "酒庄管理",tags = "酒庄管理")
@RestController("adminWinerySightController")
@RequestMapping("/admin/anon/WinerySight")
public class WinerySightController extends BaseController {

    @Resource(name = "winerySightServiceImpl")
    private WinerySightService winerySightService;

    /**
     * 查询景点列表
     * @param
     * @return
     */
    @ApiOperation(value = "查询景点列表",notes = "查询景点列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "", value = "", dataType = ""))
    @RequestMapping(value = "/findWinerySightList", method = RequestMethod.POST)
    public Map<String, Object> findWinerySightList(){
        List<WinerySight> winerySightList =winerySightService.findWinerySightList();
        return getResult(winerySightList);
    }

    /**
     * 添加景点
     * @param winerySight
     * @return
     */
    @ApiOperation(value = "添加景点",notes = "添加景点信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "winerySight", value = "景点添加对象", dataType = "WinerySight"))
    @RequestMapping(value = "/addWinerySight", method = RequestMethod.POST)
    public Map<String, Object> addWinerySight(@RequestBody WinerySight winerySight,HttpServletRequest request){
        AdminUser curAdmin = getCurAdmin(request);
        winerySightService.addWinerySight(winerySight,curAdmin);
        //log.error("异常{}",ExceptionUtils.getFullStackTrace(e));
        return getResult("添加成功");
    }

    /**
     * 查看景点
     * @param id
     * @return map
     */
    @ApiOperation(value = "景点详情",notes = "查询景点详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "景点查询id", dataType = "Integer"))
    @RequestMapping(value = "/findWinerySightById", method = RequestMethod.POST)
    public Map<String,Object> findWinerySight(@RequestParam("id") Integer id){
        Map<String,Object> map = winerySightService.findWinerySight(id);
        return getResult(map);
    }

    /**
     * 编辑景点
     * @param winerySight
     * @return map
     */
    @ApiOperation(value = "编辑景点",notes = "编辑景点")
    @ApiImplicitParams(@ApiImplicitParam(name = "winerySight", value = "景点修改对象", dataType = "WinerySight"))
    @RequestMapping(value = "/updateWinerySight", method = RequestMethod.POST)
    public Map<String,Object> updateWinerySight(WinerySight winerySight){
        winerySightService.updateWinerySight(winerySight);
        return getResult("修改成功");
    }

    /**
     * 删除景点
     * @param id
     * @return map
     */
    @ApiOperation(value = "删除景点",notes = "删除景点")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "删除景点byId", dataType = "Integer"))
    @RequestMapping(value = "/deleteWinerySight", method = RequestMethod.POST)
    public Map<String,Object> deleteWinerySight(@RequestParam("id") Integer id){
        winerySightService.deleteWinerySight(id);
        return getResult("删除成功");
    }

    /**
     * 景点添加图文
     * @param winerySightDetailList
     * @return map
     */
    @ApiOperation(value = "添加图文",notes = "景点添加图文")
    @ApiImplicitParams(@ApiImplicitParam(name = "winerySightDetailList", value = "图文添加对象", dataType = "List<WinerySightDetail>"))
    @RequestMapping(value = "/addScenicImageText", method = RequestMethod.POST)
    public Map<String,Object> addScenicImageText(@RequestBody List<WinerySightDetail> winerySightDetailList){
        winerySightService.addScenicImageText(winerySightDetailList);
        return getResult("添加成功");
    }

    /**
     * 查询商品列表
     * @param
     * @return
     */
    @ApiOperation(value = "查询商品列表",notes = "查询商品列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "", value = "", dataType = ""))
    @RequestMapping(value = "/findProdList", method = RequestMethod.POST)
    public Map<String, Object> findProdList(){
        List<Prod> prodList =winerySightService.findProdList();
        return getResult(prodList);
    }

    /**
     * 查询商品列表
     * @param
     * @return
     */
    @ApiOperation(value = "查询商品SKU列表",notes = "查询商品SKU列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "商品id", dataType = "Integer"))
    @RequestMapping(value = "/findProdSku", method = RequestMethod.POST)
    public Map<String, Object> findProdSkuList(@RequestParam("id") Integer id){
        List<ProdSku> prodSkuList =winerySightService.findProdSkuList(id);
        return getResult(prodSkuList);
    }






}

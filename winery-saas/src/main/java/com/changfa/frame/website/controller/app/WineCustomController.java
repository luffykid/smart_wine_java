package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.model.app.Admin;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.service.mybatis.app.WinerySightService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 类名称:WineCustomController
 * 类描述:定制酒管理
 * 创建人:WY
 * 创建时间:2019/8/26 19:57
 * Version 1.0
 */

@Api(value = "定制酒管理",tags = "定制酒管理")
@RestController("adminWineCustom")
@RequestMapping("/admin/auth/WineCustom")
public class WineCustomController extends BaseController {

    @Resource(name = "wineCustomServiceImpl")
    private WineCustomService wineCustomService;

    @Resource(name = "winerySightServiceImpl")
    private WinerySightService winerySightService;

    /**
     * 获取定制酒列表
     * @param pageNum 分页参数
     * @param pageSize
     * @return Map<String, Object>
     */
    @ApiOperation(value = "获取定制酒列表",notes = "获取定制酒列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineCustom", value = "定制酒对象", dataType = "WineCustom"))
    @RequestMapping(value = "/getWineCustomList", method = RequestMethod.GET)
    public Map<String, Object> getWineCustomList(WineCustom wineCustom,int pageNum,int pageSize){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return getResult(wineCustomService.getWineCustomList(wineCustom,pageInfo).getList());
    }

    /**
     * 获取所有元素
     * @return Map<String, Object>
     */
    @ApiOperation(value = "获取所有元素",notes = "获取所有元素")
    @RequestMapping(value = "/getWineCustomElement", method = RequestMethod.POST)
    public Map<String, Object> getWineCustomElement(){
        return getResult(wineCustomService.getWineCustomElement());
    }

    /**
     * 搜索定制酒
     * @param pageNum 分页参数
     * @param pageSize
     * @param wineCustom 条件对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "搜索定制酒",notes = "搜索定制酒")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineCustom", value = "定制酒对象", dataType = "WineCustom"))
    @RequestMapping(value = "/selectWineCustom", method = RequestMethod.POST)
    public Map<String, Object> selectWineCustom(@RequestBody WineCustom wineCustom, int pageNum,int pageSize){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return getResult(wineCustomService.selectWineCustom(wineCustom,pageInfo).getList());
    }

    /**
     * 定制酒上下架
     * @param wineCustom 定制酒对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "定制酒上下架",notes = "定制酒上下架")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineCustom", value = "定制酒对象", dataType = "WineCustom"))
    @RequestMapping(value = "/updateWineCustomStatus", method = RequestMethod.POST)
    public Map<String, Object> updateWineCustomStatus(WineCustom wineCustom){
        if(wineCustomService.updateWineCustomStatus(wineCustom)){
            return getResult("操作成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
    }

    /**
     * 定制酒详情
     * @param id 定制酒id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "定制酒详情",notes = "定制酒详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "定制酒id", dataType = "Long"))
    @RequestMapping(value = "/getWineCustom", method = RequestMethod.POST)
    public Map<String, Object> getWineCustom(Long id){
        return  getResult(wineCustomService.getWineCustom(id));
    }


    /**
     * 定制酒信息查询
     * @param id 定制酒id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "定制酒删除",notes = "定制酒删除")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "定制酒id", dataType = "Long"))
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Map<String, Object> delete(Long id){
        if(wineCustomService.deleteWineCustom(id)){
            return getResult("操作成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
    }


    /**
     * 添加定制酒
     * @param wineCustom 添加定制酒
     * @return Map<String, Object>
     */
    @ApiOperation(value = "添加定制酒",notes = "添加定制酒")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineCustom", value = "定制酒对象", dataType = "WineCustom"))
    @RequestMapping(value = "/saveWineCustom", method = RequestMethod.POST)
    public Map<String, Object> saveWineCustom(@RequestBody  WineCustom wineCustom, HttpServletRequest request){
        Admin curAdmin = getCurAdmin(request);
        try{
            wineCustomService.addWineCustom(wineCustom,curAdmin.getWineryId());
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
        }
        return getResult("添加成功");
    }

    /**
     * 修改定制酒
     * @param wineCustom 修改定制酒
     * @return Map<String, Object>
     */
    @ApiOperation(value = "修改定制酒",notes = "修改定制酒")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineCustom", value = "定制酒对象", dataType = "WineCustom"))
    @RequestMapping(value = "/updateWineCustom", method = RequestMethod.POST)
    public Map<String, Object> updateWineCustom(@RequestBody  WineCustom wineCustom){
        try{
            wineCustomService.updateWineCustom(wineCustom);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
        }
        return getResult("修改成功");
    }

    /**
     * 查询商品列表
     * @param
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询商品列表",notes = "查询商品列表")
    @RequestMapping(value = "/getProdList", method = RequestMethod.GET)
    public Map<String, Object> getProdList(){
        List<Prod> prodList =winerySightService.findProdList();
        return getResult(prodList);
    }

    /**
     * 查询商品SKU列表
     * @param id  商品id
     * @return  Map<String, Object>
     */
    @ApiOperation(value = "查询商品SKU列表",notes = "查询商品SKU列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "商品id", dataType = "Long"))
    @RequestMapping(value = "/getProdSkuList", method = RequestMethod.POST)
    public Map<String, Object> getProdSkuList(@RequestParam("id") Long id){
        List<ProdSku> prodSkuList =winerySightService.findProdSkuList(id);
        if (prodSkuList.size() ==0 && prodSkuList == null){
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        return getResult(prodSkuList);
    }

    /**
     * 上传文件到临时库
     * @param uploadFile 文件
     * @return  Map<String, Object>
     */
    @ApiOperation(value = "上传文件",notes = "上传文件到临时库")
    @ApiImplicitParams(@ApiImplicitParam(name = "uploadFile", value = "上传文件", dataType = "MultipartFile"))
    @RequestMapping(value = "/uploadFileTemp", method = RequestMethod.POST)
    public Map<String, Object> uploadFileTemp(MultipartFile uploadFile) {
        // 图片异步上传返回图片URL，此时图片保存在临时文件夹,
        String orgFileName = FileUtil.getNFSFileName(uploadFile);
        return getResult(orgFileName);
    }


}

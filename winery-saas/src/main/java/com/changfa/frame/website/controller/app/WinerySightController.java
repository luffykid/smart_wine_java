package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.model.app.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称:WinerySightController
 * 类描述:admin/景点管理
 * 创建人:WY
 * 创建时间:2019/8/20 15:34
 * Version 1.0
 */

@Api(value = "酒庄管理",tags = "酒庄管理")
@RestController("adminWinerySightController")
@RequestMapping("/admin/auth/WinerySight")
public class WinerySightController extends BaseController {

    @Resource(name = "winerySightServiceImpl")
    private WinerySightService winerySightService;

    /**
     * 查询景点列表
     * @param request 获取请求
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询景点列表",notes = "查询景点列表")
    @RequestMapping(value = "/getWinerySightList", method = RequestMethod.GET)
    public Map<String, Object> getWinerySightList(HttpServletRequest request,int pageNum,int pageSize){
        Admin curAdmin = getCurAdmin(request);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        PageInfo<WinerySight> winerySightList =winerySightService.findWinerySightList(curAdmin,pageInfo);
        if (winerySightList.getList().size() == 0 && winerySightList == null){
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        return getResult(winerySightList.getList());
    }

    /**
     * 添加景点
     * @param winerySight 景点对象
     * @param request     获取请求
     * @return Map<String,Object>
     */
    @ApiOperation(value = "添加景点",notes = "添加景点信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "winerySight", value = "景点添加对象", dataType = "WinerySight"))
    @RequestMapping(value = "/addWinerySight", method = RequestMethod.POST)
    public Map<String, Object> addWinerySight(@RequestBody WinerySight winerySight,HttpServletRequest request){
        Admin curAdmin = getCurAdmin(request);
        winerySightService.addWinerySight(winerySight,curAdmin);
        return getResult("添加成功");
    }

    /**
     * 查看景点
     * @param id 景点id
     * @return Map<String,Object>
     */
    @ApiOperation(value = "景点详情",notes = "查询景点详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "景点id", dataType = "Long"))
    @RequestMapping(value = "/getWinerySightById", method = RequestMethod.GET)
    public Map<String,Object> getWinerySightById(@RequestParam("id") Long id){
        return getResult(winerySightService.findWinerySight(id));
    }

    /**
     * 编辑景点
     * @param winerySight 景点对象
     * @return Map<String,Object>
     */
    @ApiOperation(value = "编辑景点",notes = "编辑景点")
    @ApiImplicitParams(@ApiImplicitParam(name = "winerySight", value = "景点修改对象", dataType = "WinerySight"))
    @RequestMapping(value = "/updateWinerySight", method = RequestMethod.POST)
    public Map<String,Object> updateWinerySight(@RequestBody  WinerySight winerySight){
        try{
            winerySightService.updateWinerySight(winerySight);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
        }
        return getResult("修改成功");
    }

    /**
     * 删除景点
     * @param id  景点id
     * @return Map<String,Object>
     */
    @ApiOperation(value = "删除景点",notes = "删除景点")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "景点Id", dataType = "Long"))
    @RequestMapping(value = "/deleteWinerySight", method = RequestMethod.POST)
    public Map<String,Object> deleteWinerySight(@RequestParam("id") Long id){
        if ( winerySightService.deleteWinerySight(id) ){
            return getResult("删除成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.DELETE_FAILED);
    }

    /**
     * 景点添加图文
     * @param winerySightDetailList  景点图文对象集合
     * @return Map<String,Object>
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

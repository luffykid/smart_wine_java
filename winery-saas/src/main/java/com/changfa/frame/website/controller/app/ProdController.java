package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.model.app.Admin;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.service.mybatis.app.ProdService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称:ProdController
 * 类描述:产品管理Controller
 * 创建人:wy
 * 创建时间:2019/8/23 14:13
 * Version 1.0
 */
@Api(value = "产品管理",tags = "产品管理")
@RestController("adminProdController")
@RequestMapping("/admin/auth/Prod")
public class ProdController extends BaseController {

    @Resource(name = "prodServiceImpl")
    private ProdService prodService;

   /**
     * 查询产品列表
     * @param pageNum  分页参数
    * @param pageSize
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询产品列表",notes = "查询产品列表")
    @RequestMapping(value = "/getProdList", method = RequestMethod.GET)
    public Map<String, Object> getProdList(Prod prod,int pageNum,int pageSize){
        PageInfo<Prod> prodPageInfo = new PageInfo<>();
        prodPageInfo.setPageNum(pageNum);
        prodPageInfo.setPageSize(pageSize);
        PageInfo prodList = prodService.getProdList(prod, prodPageInfo);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("prodList",prodList.getList());
        returnMap.put("pageNum",prodList.getPageNum());
        returnMap.put("pageSize",prodList.getPageSize());
        returnMap.put("total",prodList.getTotal());
        return getResult(returnMap);
    }

    /**
     * 搜索产品
     * @param
     * @return Map<String, Object>
     */
    @ApiOperation(value = "搜索产品",notes = "搜索产品")
    @ApiImplicitParams({@ApiImplicitParam(name = "prodName", value = "产品名称", dataType = "String"),
                        @ApiImplicitParam(name = "lableType", value = "产品类型", dataType = "Long")})
    @RequestMapping(value = "/findProdList", method = RequestMethod.GET)
    public Map<String, Object> findProdList(String prodName, Long lableType){
        return getResult(prodService.findProd(prodName,lableType));
    }

    /**
     * 新建产品
     * @param prod 产品新建对象
     * @return Map<String, Object>//存在问题
     */
    @ApiOperation(value = "新建产品",notes = "新建产品")
    @ApiImplicitParams(@ApiImplicitParam(name = "prod", value = "产品新建对象", dataType = "Prod"))
    @RequestMapping(value = "/addProd", method = RequestMethod.POST)
    public Map<String, Object> addProd(HttpServletRequest request, @RequestBody  Prod prod){
        Admin admin = getCurAdmin(request);
        try{
            prodService.addProd(admin,prod);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.ADD_FAILED);
        }
        return getResult("添加成功");
    }

    /**
     * 产品下架 同时下架有关商品sku
     * @param id 产品id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "产品下架",notes = "产品下架")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "产品id", dataType = "Long"),
                        @ApiImplicitParam(name = "skuStatus", value = "下上架参数", dataType = "Integer")})
    @RequestMapping(value = "/prodOut", method = RequestMethod.POST)
    public Map<String, Object> prodOut(Long id ,Integer status){
        try{
            prodService.updateProdStatus(id,status);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
        }
        return getResult("修改成功");
    }

    /**
     * 查询产品详情
     * @param id 产品id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询产品详情",notes = "查询产品详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "产品id", dataType = "Long"))
    @RequestMapping(value = "/getProd", method = RequestMethod.GET)
    public Map<String, Object> getProd(Long id){
       return getResult(prodService.getProd(id));
    }

    /**
     * 编辑产品信息
     * @param prod 产品编辑对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "编辑产品信息",notes = "编辑产品信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "Prod", value = "产品编辑对象", dataType = "Prod"))
    @RequestMapping(value = "/updateProd", method = RequestMethod.POST)
    public Map<String, Object> updateProd(@RequestBody Prod prod){
        try{
            prodService.updateProd(prod);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
        }
        return getResult("修改成功");
    }

    /**
     * 删除详情图片
     * @param id 产品图文id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "删除详情图片",notes = "删除详情图片")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "产品图文id", dataType = "Long"))
    @RequestMapping(value = "/deleteProdDetailImg", method = RequestMethod.POST)
    public Map<String, Object> deleteProdDetailImg(Long id){
        if(prodService.deleteProdDetailImg(id)){
            return getResult("删除成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.DELETE_FAILED);
    }

    /**
     * 删除产品
     * @param id 产品id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "删除产品",notes = "删除产品")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "产品id", dataType = "Long"))
    @RequestMapping(value = "/deleteProd", method = RequestMethod.POST)
    public Map<String, Object> deleteProd(Long id){
        if(prodService.deleteById(id)){
            return getResult("删除成功");
        }
        return getResult("该产品规格未完全下架");
    }

    /**
     * 删除产品
     * @param id 产品id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "删除产品详情",notes = "删除产品详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "产品详情id", dataType = "Long"))
    @RequestMapping(value = "/deleteProdDetail", method = RequestMethod.POST)
    public Map<String, Object> deleteProdDetail(Long id){
        if(prodService.deleteProdDetail(id)){
            return getResult("删除成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.DELETE_FAILED);
    }



    /**
     * 通过产品id 查询产品规格列表
     * @param pageNum 分页参数
     * @param pageSize
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询产品规格列表",notes = "查询产品规格列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "产品id", dataType = "Long"))
    @RequestMapping(value = "/getProdSkuList", method = RequestMethod.GET)
    public Map<String, Object> getProdSkuList(Long id,int pageNum,int pageSize){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return getResult(prodService.getProdSkuList(id,pageInfo).getList());
    }


    /**
     * 新建产品规格
     * @param prodSku 规格新建对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "新建规格",notes = "新建产品规格")
    @ApiImplicitParams(@ApiImplicitParam(name = "prodSku", value = "产品规格新建对象", dataType = "ProdSku"))
    @RequestMapping(value = "/addProdSku", method = RequestMethod.POST)
    public Map<String, Object> addProdSku(@RequestBody ProdSku prodSku){
        try{
            prodService.addProdSku(prodSku);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.ADD_FAILED);
        }
        return getResult("添加成功");
    }

    /**
     * 产品规格下架
     * @param id 产品规格id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "产品规格下架",notes = "产品规格下架")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "产品规格id", dataType = "Long"),
                        @ApiImplicitParam(name = "skuStatus", value = "下上架参数", dataType = "Integer")})
    @RequestMapping(value = "/prodSkuOut", method = RequestMethod.POST)
    public Map<String, Object> ProdSkuOut(Long id,Integer skuStatus){
        if(prodService.updateProdSkuStatus(id,skuStatus)){
            return getResult("下架成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
    }

    /**
     * 删除产品规格
     * @param id 产品规格id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "删除产品规格",notes = "删除产品规格")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "产品规格id", dataType = "Long"))
    @RequestMapping(value = "/deleteProdSku", method = RequestMethod.POST)
    public Map<String, Object> deleteProdSku(Long id){
        if(prodService.deleteProdSku(id)){
            return getResult("删除成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.DELETE_FAILED);
    }

    /**
     * 查询会员等级列表
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询会员等级列表",notes = "查询会员等级列表")
    @RequestMapping(value = "/getMbrLevel", method = RequestMethod.GET)
    public Map<String, Object> getMbrLevel(){
        return getResult(prodService.getMbrLevel());
    }

    /**
     * 查询产品规格详情
     * @param id 产品规格id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询产品规格详情",notes = "查询产品规格详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "产品规格id", dataType = "Long"))
    @RequestMapping(value = "/getProdSku", method = RequestMethod.POST)
    public Map<String, Object> getProdSku(Long id){
        return getResult(prodService.getProdSku(id));
    }


    /**
     * 编辑产品规格
     * @param prodSku 编辑产品规格对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "编辑产品规格",notes = "编辑产品规格")
    @ApiImplicitParams(@ApiImplicitParam(name = "prodSku", value = "编辑产品规格对象", dataType = "ProdSku"))
    @RequestMapping(value = "/updateProdSku", method = RequestMethod.POST)
    public Map<String, Object> updateProdSku(@RequestBody ProdSku prodSku){
        try{
            prodService.updateProdSku(prodSku);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
        }
        return getResult("修改成功");
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

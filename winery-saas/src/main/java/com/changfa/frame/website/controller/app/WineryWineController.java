package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.model.app.Admin;
import com.changfa.frame.model.app.Winery;
import com.changfa.frame.model.app.WineryWine;
import com.changfa.frame.service.mybatis.app.WineryWineService;
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
import java.util.List;
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
     * 查询酒庄酒列表
     * @param pageNum 分页对象
     * @param pageSize 分页参数
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询酒庄酒列表",notes = "查询酒庄酒列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineryWine", value = "酒庄酒对象", dataType = "WineryWine"))
    @RequestMapping(value = "/getWineryWineList", method = RequestMethod.GET)
    public Map<String, Object> getWineryWineList(WineryWine wineryWine,int pageNum,int pageSize){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        PageInfo<WineryWine> list = wineryWineService.getWineryWineList(wineryWine,pageInfo);
        if(list != null && list.getList().size() > 0){
            return getResult(list);
        }
        return getResult("未查询到产品");

    }

    /**
     * 搜索酒庄酒
     * @param name 酒庄酒名称
     * @param status 状态 上架 下架
     * @return
     */
    @ApiOperation(value = "搜索酒庄酒",notes = "搜索酒庄酒")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "酒庄酒名称", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer")})
    @RequestMapping(value = "/findProdList", method = RequestMethod.GET)
    public Map<String, Object> findProdList(PageInfo pageInfo,String name, Integer status){
        return getResult(wineryWineService.findWindery(pageInfo, name, status));
    }


    /**
     * 新建酒庄酒
     * @param wineryWine  酒庄酒对象
     * @param request   获取请求
     * @return Map<String,Object>
     */
    @ApiOperation(value = "新建酒庄酒",notes = "新建酒庄酒")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineryWine", value = "酒庄酒对象", dataType = "WineryWine"))
    @RequestMapping(value = "/addWineryWine", method = RequestMethod.POST)
    public Map<String, Object> addWineryWine(@RequestBody WineryWine wineryWine, HttpServletRequest request){
        Admin admin = getCurAdmin(request);
        try{
            wineryWineService.addWineryWine(wineryWine,admin.getWineryId());
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.ADD_FAILED);
        }
        return getResult("添加成功");
    }

    /**
     * 获取产品列表
     * @return Map<String, Object>
     */
    @ApiOperation(value = "获取产品列表",notes = "获取产品列表")
    @RequestMapping(value = "/getProdList", method = RequestMethod.GET)
    public Map<String, Object> getProdList(){
            return getResult(wineryWineService.getProdList());
    }

    /**
     * 获取酒庄酒详情
     * @param id 产品规格id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "获取酒庄酒详情",notes = "获取酒庄酒详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "酒庄酒id", dataType = "Long"))
    @RequestMapping(value = "/getwineryWine", method = RequestMethod.GET)
    public Map<String, Object> getwineryWine(Long id){
        return getResult(wineryWineService.getwineryWine(id));
    }

    /**
     * 编辑酒庄酒
     * @param wineryWine 酒庄酒对象
     * @return Map<String,Object>
     */
    @ApiOperation(value = "编辑酒庄酒",notes = "编辑酒庄酒")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineryWine", value = "酒庄酒对象", dataType = "WineryWine"))
    @RequestMapping(value = "/updateWineryWine", method = RequestMethod.POST)
    public Map<String, Object> updateWineryWine(@RequestBody WineryWine wineryWine){
        try{
            wineryWineService.updateWineryWine(wineryWine,1L);
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
        }
        return getResult("修改成功");
    }


    /**
     * 酒庄酒上下架
     * @param id 酒庄酒id
     * @return Map<String, Object>
     */
   @ApiOperation(value = "酒庄酒上下架",notes = "酒庄酒上下架")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "酒庄酒id", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "下上架参数", dataType = "Integer")})
    @RequestMapping(value = "/wineryWineOut", method = RequestMethod.POST)
    public Map<String, Object> wineryWineOut(Long id,Integer status){
        if(wineryWineService.wineryWineOut(id,status)){
            return getResult("操作成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.UPDATE_FAILED);
    }

    /**
     * 删除酒庄酒
     * @param id 产品规格id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "删除酒庄酒",notes = "删除酒庄酒")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "酒庄酒id", dataType = "Long"))
    @RequestMapping(value = "/deleteWineryWine", method = RequestMethod.POST)
    public Map<String, Object> deleteWineryWine(Long id){
        if(wineryWineService.deleteWineryWine(id)){
            return getResult("删除成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.DELETE_FAILED);
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

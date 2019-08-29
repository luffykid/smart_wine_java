package com.changfa.frame.website.controller.common;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.service.mybatis.app.SystemConfigService;
import com.changfa.frame.website.utils.SettingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器编码范例
 *
 * @author wyy
 * @date 2019-08-20 19:57
 */
@Api(value = "Demo编码范例", tags = "Demo编码范例")
@RestController("wxMiniDemoController")
@RequestMapping("/wxMini/auth/demo")
public class DemoController extends BaseController {

    // service层必须是接口、实现类方式，Spring为面向接口编程
    private SystemConfigService systemConfigService;

    /**
     * 普通控制器接口书写规范
     *
     * @param setting 系统设置对象
     * @return
     */
    @ApiOperation(value = "普通接口书写范例", notes = "普通接口书写范例", httpMethod = "GET")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "setting", value = "系统配置对象", dataType = "Setting"))
    @RequestMapping(value = "/updateSysConfig", method = RequestMethod.GET)
    public Map<String, Object> updateSysConfig(Setting setting) {
        // 测试Setting获取
        System.out.println(SettingUtils.get().getCopyRight());

        // 正常Service层调用，控制层不能直接调用DAO层
        systemConfigService.set(setting);

        // controller层异常方式
        if (true) {
            // 必要的地方打印日志【日志使用占位符方式，如果打印堆栈信息，可以使用ExceptionUtils】
            log.info("此处有错误:{}", "错误信息");

            // 如果需要返回错误提示【框架中错误返回均以全局异常处理】
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }

        // 如果返回对象信息，可以为简单类型、复合类型[Object、Map、List、model实体等]
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("name", "小栗子");
        returnMap.put("model实体类", new Setting());
        returnMap.put("集合", new ArrayList<String>());
        returnMap.put("数字", 1231312L);
        return getResult(returnMap);
    }

    /**
     * 文件上传接口书写规范
     *
     * @param testFile 上传文件
     * @return
     */
    @ApiOperation(value = "文件上传范例", notes = "文件上传范例", httpMethod = "POST")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uploadFile", value = "上传文件", dataType = "MultipartFile"))
    @PostMapping(value = "/uploadFile")
    public Map<String, Object> uploadFile(MultipartFile testFile, String org) {
        // 1、图片异步上传返回图片URL，此时图片保存在临时文件夹,
        String orgFileName = FileUtil.getNFSFileName(testFile);

        // 2、表单提交，需将临时文件夹中文件拷贝到图片仓库文件夹中，并返回图片URL
        String fileUrl = FileUtil.copyNFSByFileName(orgFileName, FilePathConsts.TEST_FILE_PATH);

        // 3、如果是编辑页面，需要先删除原图片
        //FileUtil.deleteNFSByFileUrl(orgFileUrl,newFileUrl);

        // 4、如果是直接图片上传到NFS服务器，返回文件NFS访问URL
        String nfsUrl = FileUtil.getNFSUrl(testFile, FilePathConsts.TEST_FILE_PATH);

        return getResult(org);
    }

    /**
     * 测试微信支付统一下单
     */
    @ApiOperation(value = "测试微信支付统一下单", notes = "测试微信支付统一下单", httpMethod = "POST")
    @PostMapping(value = "/unifiedOrder")
    public Map<String, Object> unifiedOrderOfWxMini(HttpServletRequest request) {
        // 微信支付预下单
        Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini("123098",
                new BigDecimal("0.01"), "o1tUJ42W_-5d0DTBB-lI-S7ukgow",
                "/paymentNotify/async_notify/MBR_LEVEL_ORDER.jhtml", "测试订单", request);

        return getResult(returnMap);
    }

}

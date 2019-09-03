package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.model.app.MbrWineCustom;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "会员定制接口", tags = "会员定制接口")
@RestController("wxMiniMbrWineCustomOrderController")
@RequestMapping("/wxMini/auth/MbrWineCustomOrder")
public class MbrWineCustomOrderController extends BaseController {

    @Resource(name = "mbrWineCustomOrderServiceImpl")
    private MbrWineCustomOrderService mbrWineCustomOrderService;

    @Resource(name = "wineCustomServiceImpl")
    private WineCustomService wineCustomService;

    /**
     * 上传定制图片接口
     *
     * @param file 上传文件
     * @return
     */
    @ApiOperation(value = "上传定制图片接口", notes = "上传定制图片接口", httpMethod = "POST")
    @ApiImplicitParams(@ApiImplicitParam(name = "file", value = "上传文件", dataType = "MultipartFile"))
    @PostMapping(value = "/uploadCustomFile")
    public Map<String, Object> uploadCustomFile(MultipartFile file) {
        // 1、图片异步上传返回图片URL，此时图片保存在临时文件夹
        String orgFileName = FileUtil.getNFSFileName(file);
        return getResult(orgFileName);
    }

    /**
     * 保存会员定制信息
     *
     * @param mbrWineCustom 定制信息
     * @param request
     * @return
     */
    @ApiOperation(value = "保存会员定制信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbrWineCustomDetails", value = "定制元素集合", dataType = "List"),
            @ApiImplicitParam(name = "mbrWineCustom", value = "定制信息", dataType = "MbrWineCustom")
    })
    @PostMapping("/saveMbrCustomInfo")
    public Map<String, Object> saveMbrCustomInfo(@RequestBody MbrWineCustom mbrWineCustom, HttpServletRequest request) {
        // 获取当前会员
        Member member = getCurMember(request);
        List<MbrWineCustomDetail> mbrWineCustomDetails = mbrWineCustom.getMbrWineCustomDetails();

        // 校验参数
        if (mbrWineCustom.getWineCustomId() == null || CollectionUtils.isEmpty(mbrWineCustomDetails)) {
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }

        // 保存会员定制信息
        Map<String, Object> resultMap = mbrWineCustomOrderService.saveMbrCustomInfo(mbrWineCustomDetails, mbrWineCustom, member);
        if (!(Boolean) resultMap.get("result")) {
            throw new CustomException(RESPONSE_CODE_ENUM.SAVE_CUSTOM_INFO_FAIL);
        }

        // 查询定制信息,并返回
        WineCustom wineCustom = wineCustomService.getById(mbrWineCustom.getWineCustomId());
        HashMap<Object, Object> returnMap = new HashMap<>();
        returnMap.put("wineCustom", wineCustom);
        returnMap.put("mbrWineCustomId", resultMap.get("mbrWineCustomId"));

        return getResult(returnMap);
    }

    /**
     * 会员定制订单统一下单
     *
     * @param mbrWineCustomId 会员定制ID
     * @param customCnt       定制数量
     * @param request
     * @return
     */
    @ApiOperation(value = "会员定制订单统一下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mbrWineCustomId", value = "会员定制ID", dataType = "Long"),
            @ApiImplicitParam(name = "mbrAddressId", value = "会员地址ID", dataType = "Long"),
            @ApiImplicitParam(name = "customCnt", value = "定制数量", dataType = "Integer")
    })
    @PostMapping("/saveCustomOrder")
    public Map<String, Object> saveCustomOrder(Long mbrWineCustomId, Long mbrAddressId, Integer customCnt, HttpServletRequest request) {
        // 校验参数
        if (mbrWineCustomId == null || mbrAddressId == null || customCnt == null || customCnt.equals("")) {
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }

        // 处理订单预下单业务
        Map<String, Object> resultMap = mbrWineCustomOrderService.handleCustomPreOrder(getCurMember(request), mbrWineCustomId, mbrAddressId, customCnt);
        if (!(Boolean) resultMap.get("result")) {
            throw new CustomException(RESPONSE_CODE_ENUM.SAVE_CUSTOM_INFO_FAIL);
        }

        // 微信支付统一下单
        String orderNo = String.valueOf(resultMap.get("orderNo"));
        BigDecimal payTotalAmt = (BigDecimal) resultMap.get("payTotalAmt");
        Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(orderNo, payTotalAmt, getCurMember(request).getOpenId(),
                "/paymentNotify/async_notify/WINE_CUSTOM_ORDER.jhtml", "定制酒订单", request);

        return getResult(returnMap);

    }

}

package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWineCustomOrderService;
import com.changfa.frame.website.controller.common.BaseController;
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

@Api(value = "会员定制接口", tags = "会员定制接口")
@RestController("wxMiniMbrWineCustomOrderController")
@RequestMapping("/wxMini/auth/MbrWineCustomOrder")
public class MbrWineCustomOrderController extends BaseController {

    @Resource(name = "mbrWineCustomOrderServiceImpl")
    private MbrWineCustomOrderService mbrWineCustomOrderService;

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
        // 1、图片异步上传返回图片URL，此时图片保存在临时文件夹,
        String orgFileName = FileUtil.getNFSFileName(file);
        return getResult(orgFileName);
    }

    /**
     * @param mbrWineCustomDetails
     * @param request
     * @return
     */
    @ApiOperation(value = "保存会员定制信息")
    @PostMapping("/saveMbrCustomInfo")
    public Map<String, Object> saveMbrCustomInfo(@RequestBody List<MbrWineCustomDetail> mbrWineCustomDetails, HttpServletRequest request) {
        // 获取当前会员
        Member member = getCurMember(request);
        mbrWineCustomOrderService.saveMbrCustomInfo(mbrWineCustomDetails,member);

        return getResult(null);
    }

    @PutMapping("{id}/member/{mbrId}/memberAddress/{memberAddressId}")
    public Map<String, Object> payForOrder(@PathVariable("id") Long mbrWineCustomOrderId,
                                           @PathVariable("memberAddressId") Long memberAddressId,
                                           HttpServletRequest request) {

        Member member = getCurMember(request);

        mbrWineCustomOrderService.addShipInfoForTheOrder(member.getId(), memberAddressId, mbrWineCustomOrderId);
       /* Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(order.getOrderNo(),
                                                                            order.getPayRealAmt(),
                                                                            order.getOpenId(),
                                                                            "",
                                                                            "定制酒订单",
                                                                            request);

        order.setUnifiedOrderReturnMap(returnMap);*/

        return getResult(new HashMap<>());

    }

}

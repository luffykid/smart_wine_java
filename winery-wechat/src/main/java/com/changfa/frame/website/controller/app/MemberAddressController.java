package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.MemberAddress;
import com.changfa.frame.service.mybatis.app.MemberAddressService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会员地址接口
 */
@Api(value = "会员地址接口", tags = "会员地址接口")
@RestController("wxMiniMemberAddressController")
@RequestMapping("/wxMini/auth/memberAddress")
public class MemberAddressController extends BaseController {

    @Resource(name = "memberAddressServiceImpl")
    private MemberAddressService memberAddressServiceImpl;

    /**
     * 获取我的管理地址列表
     *
     * @return
     */
    @ApiOperation(value = "获取我的管理地址列表", notes = "获取我的管理地址列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(HttpServletRequest request) {
        Member member = getCurMember(request);
        List<MemberAddress> list = memberAddressServiceImpl.getList(member.getId());
        return getResult(list);
    }

    /**
     * 添加新的地址
     *
     * @return
     */
    @ApiOperation(value = "添加新的地址", notes = "添加新的地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contact", value = "收货人", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "String"),
            @ApiImplicitParam(name = "provinceCode", value = "省编码", dataType = "String"),
            @ApiImplicitParam(name = "cityCode", value = "市编码", dataType = "String"),
            @ApiImplicitParam(name = "countryCode", value = "县编码", dataType = "String"),
            @ApiImplicitParam(name = "detailAddress", value = "详细地址", dataType = "String"),
            @ApiImplicitParam(name = "isDefault", value = "是否默认地址", dataType = "Boolean")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(String contact, String phone, String provinceCode, String cityCode, String countryCode, String detailAddress, Boolean isDefault, HttpServletRequest request) {
        Member member = getCurMember(request);
        Long wineryId = getCurWineryId();
        try {
            memberAddressServiceImpl.add(member.getId(),wineryId, contact, phone, provinceCode, cityCode, countryCode, detailAddress, isDefault);
        } catch (Exception e) {
            log.info("此处有错误:{}", "插入数据失败");
            throw new CustomException(RESPONSE_CODE_ENUM.INSERT_EXIST);
        }
        return getResult("success");
    }

    /**
     * 修改地址
     *
     * @return
     */
    @ApiOperation(value = "修改地址", notes = "修改地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", dataType = "Long"),
            @ApiImplicitParam(name = "contact", value = "收货人", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "String"),
            @ApiImplicitParam(name = "provinceCode", value = "省编码", dataType = "String"),
            @ApiImplicitParam(name = "cityCode", value = "市编码", dataType = "String"),
            @ApiImplicitParam(name = "countryCode", value = "县编码", dataType = "String"),
            @ApiImplicitParam(name = "detailAddress", value = "详细地址", dataType = "String"),
            @ApiImplicitParam(name = "isDefault", value = "是否默认地址", dataType = "Boolean")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> update(Long id, String contact, String phone, String provinceCode, String cityCode, String countryCode, String detailAddress, Boolean isDefault, HttpServletRequest request) {
        Member member = getCurMember(request);
        try {
            memberAddressServiceImpl.modify(id, contact, phone, provinceCode, cityCode, countryCode, detailAddress, isDefault);
        } catch (Exception e) {
            log.info("此处有错误:{}", "修改数据失败");
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult("success");
    }

    /**
     * 删除的地址
     *
     * @return
     */
    @ApiOperation(value = "删除的地址", notes = "删除的地址")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Map<String, Object> delete(Long id, HttpServletRequest request) {
        Member member = getCurMember(request);
        try {
            memberAddressServiceImpl.delete(id);
        } catch (Exception e) {
            log.info("此处有错误:{}", "删除数据失败");
            throw new CustomException(RESPONSE_CODE_ENUM.DELETE_EXIST);
        }
        return getResult("success");
    }
}

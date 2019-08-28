package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.MbrTakeOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.MemberAddress;
import com.changfa.frame.service.mybatis.app.MemberAddressService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员地址接口
 *
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
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Map<String, Object> add(MemberAddress memberAddress, HttpServletRequest request) {
        Member member = getCurMember(request);
        memberAddress.setId(IDUtil.getId());
        memberAddress.setMbrId(member.getId());
        try {
            memberAddressServiceImpl.save(memberAddress);
        }catch (Exception e){
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
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Map<String, Object> update(MemberAddress memberAddress, HttpServletRequest request) {
        Member member = getCurMember(request);
        memberAddress.setModifyDate(new Date());
        try {
            memberAddressServiceImpl.update(memberAddress);
        }catch (Exception e){
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
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Map<String, Object> delete(Long id, HttpServletRequest request) {
        Member member = getCurMember(request);
        try {
            memberAddressServiceImpl.delete(id);
        }catch (Exception e){
            log.info("此处有错误:{}", "删除数据失败");
            throw new CustomException(RESPONSE_CODE_ENUM.DELETE_EXIST);
        }
        return getResult("success");
    }
}

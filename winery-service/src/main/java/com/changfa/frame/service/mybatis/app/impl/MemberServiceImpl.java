package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrIntegralRecordMapper;
import com.changfa.frame.mapper.app.MbrWechatMapper;
import com.changfa.frame.mapper.app.MemberMapper;
import com.changfa.frame.model.app.MbrIntegralRecord;
import com.changfa.frame.model.app.MbrWechat;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MemberService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 会员service实现
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MbrWechatMapper mbrWechatMapper;
    @Autowired
    private MbrIntegralRecordMapper mbrIntegralRecordMapper;

    /**
     * 根据手机号查询会员
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public Member selectByPhone(String phone) {
        return memberMapper.selectByPhone(phone);
    }

    /**
     * 获取招募会员列表
     *
     * @param mbrId
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo getSubList(Long mbrId, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(memberMapper.selectSubList(mbrId));
    }

    /**
     * 获取会员积分列表
     *
     * @return
     */
    @Override
    public PageInfo getIntegralList(Long mbrId, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        MbrIntegralRecord mbrIntegralRecord = new MbrIntegralRecord();
        mbrIntegralRecord.setMbrId(mbrId);
        return new PageInfo(mbrIntegralRecordMapper.selectList(mbrIntegralRecord));
    }

    /**
     * 获取招募会员数据统计
     *
     * @param mbrId
     * @return
     */
    @Override
    public List<Map<String, Object>> getSubStatis(Long mbrId) {
        return memberMapper.selectSubStatis(mbrId);
    }


    /**
     * 修改会员信息
     *
     * @param mbrId
     * @param mbrName  会员名称
     * @param phone    手机
     * @param gender   性别
     * @param age      年龄
     * @param phone    电话
     */
    @Transactional
    @Override
    public void updateMember(Long mbrId, String mbrName, String phone, Integer gender,Integer age) {
        Member member = memberMapper.getById(mbrId);
        member.setMbrName(mbrName);
        member.setGender(gender);
        member.setPhone(phone);
        member.setAge(age);
        memberMapper.update(member);

    }

    /**
     * 根据会员openId查询会员对象
     *
     * @param openId 会员openId
     * @return
     */
    @Override
    public Member getByOpenId(String openId) {
        return memberMapper.selectByOpenId(openId);
    }
}

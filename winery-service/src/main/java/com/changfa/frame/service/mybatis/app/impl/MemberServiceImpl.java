package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrWechatMapper;
import com.changfa.frame.mapper.app.MemberMapper;
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
     * @param userIcon 头像
     * @param nickName 名称
     * @param birthday 生日
     * @param sex      性别
     * @param phone    电话
     */
    @Transactional
    @Override
    public void updateMember(Long mbrId, String userIcon, String nickName, String birthday, String sex, String phone) {
        Member member = memberMapper.getById(mbrId);
        member.setNickName(nickName);
        member.setPhone(phone);
        memberMapper.update(member);
        List<MbrWechat> mbrWechats = mbrWechatMapper.selectListByMbrIdAndWineryId(member.getId(), member.getWineryId());
        if (mbrWechats.size() == 0) {
            MbrWechat mbrWechat = new MbrWechat();
            mbrWechat.setNickName(nickName);
            mbrWechat.setBirthday(new Date(birthday));
            mbrWechat.setSex(sex);
            mbrWechatMapper.save(mbrWechat);
        } else {
            MbrWechat mbrWechat = mbrWechats.get(0);
            mbrWechat.setNickName(nickName);
            mbrWechat.setBirthday(new Date(birthday));
            mbrWechat.setSex(sex);
            mbrWechatMapper.update(mbrWechat);
        }
    }
}

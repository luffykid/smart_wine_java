package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 会员service
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
public interface MemberService extends BaseService<Member,Long> {

    /**
     * 根据手机号查询会员
     *
     * @param phone 手机号
     * @return
     */
    Member selectByPhone(String phone);

    /**
     * 获取招募会员列表
     * @param mbrId
     * @param pageInfo
     * @return
     */
    PageInfo getSubList(Long mbrId, PageInfo pageInfo);

    /**
     * 获取招募会员数据统计
     * @param mbrId
     * @return
     */
    List<Map<String, Object>> getSubStatis(Long mbrId);

    /**
     * 修改会员信息
     * @param mbrId
     * @param userIcon 头像
     * @param nickName 名称
     * @param birthday 生日
     * @param sex 性别
     * @param phone 电话
     */
    void updateMember(Long mbrId, String userIcon, String nickName, String birthday, String sex, String phone);
}


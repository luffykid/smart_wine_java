package com.changfa.frame.service.jpa.user;


import com.changfa.frame.data.dto.wechat.MemberLevelDTO;
import com.changfa.frame.data.dto.wechat.UserMemberLevelDTO;
import com.changfa.frame.data.entity.point.PointRewardRule;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.entity.user.UserExperience;
import com.changfa.frame.data.repository.point.PointRewardRuleRepository;
import com.changfa.frame.data.repository.user.MemberLevelRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.data.repository.user.UserExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberWechatService {

    private static Logger log = LoggerFactory.getLogger(MemberWechatService.class);

    @Autowired
    private MemberWechatRepository memberWechatRepository;

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Autowired
    private UserExperienceRepository userExperienceRepository;
    @Autowired
    private PointRewardRuleRepository pointRewardRuleRepository;

    /* *
     * 根据userId获取会员信息
     * @Author        zyj
     * @Date          2018/10/15 12:25
     * @Description
     * */
    public MemberWechat findByUserId(Integer userId) {
        return memberWechatRepository.findByMbrId(userId);
    }


    /* *
     * 会员等级资料
     * @Author        zyj
     * @Date          2018/10/16 11:47
     * @Description
     * */
    public UserMemberLevelDTO userLevelDetail(Member user) {
        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        MemberLevel userMemberLevel = memberLevelRepository.findMemberLevelById(memberUser.getMemberLevel());
        UserMemberLevelDTO userMemberLevelDTO = new UserMemberLevelDTO();
        if (userMemberLevel != null) {
            userMemberLevelDTO.setUserLevelName(userMemberLevel.getName());
        }
        userMemberLevelDTO.setUserExperience(userExperienceRepository.findByUserId(Integer.valueOf(user.getId().toString())).getExperience().toString());
        userMemberLevelDTO.setUsefulTime("2019-12-31");
        userMemberLevelDTO.setUserLevelContent1("第一年会员等级跟随经验值进行提升，享受对应的优惠； 第二年会员等级基于第一年最终积分结算，如第一年最终会员积分达到钻石会员经验，则第二年会员初始等级为钻石会员，且享受该等级会员优惠。");
        userMemberLevelDTO.setUserLevelContent2("一积分等于一经验值");
        PointRewardRule rule = pointRewardRuleRepository.findByWineryId(Integer.valueOf(user.getWineryId().toString()));
        userMemberLevelDTO.setUserLevelContent3("充值"+rule.getDepositMoneyPoint()+"元送1积分，"+"消费"+rule.getConsumeMoneyPoint()+"元送1积分");
        List<MemberLevelDTO> memberLevelDTOList = new ArrayList<>();
        List<MemberLevel> memberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(Integer.valueOf(user.getWineryId().toString()), "A");
        for (MemberLevel memberLevel : memberLevelList) {
                MemberLevelDTO memberLevelDTO = new MemberLevelDTO();
                memberLevelDTO.setName(memberLevel.getName());
                memberLevelDTO.setUpgradeExperience(memberLevel.getUpgradeExperience().toString());
                memberLevelDTOList.add(memberLevelDTO);
        }
        userMemberLevelDTO.setMemberLevelDTOList(memberLevelDTOList);
        return userMemberLevelDTO;
    }


    public void updateMemberUserLevel(Integer levelId, Long winerId) {
        List<MemberWechat> memberUserList = memberWechatRepository.findByWineryId(winerId);
        if (memberUserList != null && memberUserList.size() > 0) {
            List<MemberLevel> upgradeMemberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(Integer.valueOf(winerId.toString()), "A");
            for (MemberWechat memberUser : memberUserList) {
                UserExperience userExperience = userExperienceRepository.findByUserId(memberUser.getMbrId());
                for (MemberLevel memberLevel : upgradeMemberLevelList) {
                    if (userExperience.getExperience()>= memberLevel.getUpgradeExperience()) {
                            memberUser.setMemberLevel(memberLevel.getId());
                            memberWechatRepository.saveAndFlush(memberUser);
                    }
                }
            }
        }
    }


}

package com.changfa.frame.service.user;


import com.changfa.frame.data.dto.wechat.MemberLevelDTO;
import com.changfa.frame.data.dto.wechat.UserMemberLevelDTO;
import com.changfa.frame.data.entity.point.PointRewardRule;
import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.entity.user.MemberUser;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.user.UserExperience;
import com.changfa.frame.data.repository.point.PointRewardRuleRepository;
import com.changfa.frame.data.repository.user.MemberLevelRepository;
import com.changfa.frame.data.repository.user.MemberUserRepository;
import com.changfa.frame.data.repository.user.UserExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberUserService {

    private static Logger log = LoggerFactory.getLogger(MemberUserService.class);

    @Autowired
    private MemberUserRepository memberUserRepository;

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
    public MemberUser findByUserId(Integer userId) {
        return memberUserRepository.findByUserId(userId);
    }


    /* *
     * 会员等级资料
     * @Author        zyj
     * @Date          2018/10/16 11:47
     * @Description
     * */
    public UserMemberLevelDTO userLevelDetail(User user) {
        MemberUser memberUser = memberUserRepository.findByUserId(user.getId());
        MemberLevel userMemberLevel = memberLevelRepository.findMemberLevelById(memberUser.getMemberLevelId());
        UserMemberLevelDTO userMemberLevelDTO = new UserMemberLevelDTO();
        if (userMemberLevel != null) {
            userMemberLevelDTO.setUserLevelName(userMemberLevel.getName());
        }
        userMemberLevelDTO.setUserExperience(userExperienceRepository.findByUserId(user.getId()).getExperience().toString());
        userMemberLevelDTO.setUsefulTime("2019-12-31");
        userMemberLevelDTO.setUserLevelContent1("第一年会员等级跟随经验值进行提升，享受对应的优惠； 第二年会员等级基于第一年最终积分结算，如第一年最终会员积分达到钻石会员经验，则第二年会员初始等级为钻石会员，且享受该等级会员优惠。");
        userMemberLevelDTO.setUserLevelContent2("一积分等于一经验值");
        PointRewardRule rule = pointRewardRuleRepository.findByWineryId(user.getWineryId());
        userMemberLevelDTO.setUserLevelContent3("充值"+rule.getDepositMoneyPoint()+"元送1积分，"+"消费"+rule.getConsumeMoneyPoint()+"元送1积分");
        List<MemberLevelDTO> memberLevelDTOList = new ArrayList<>();
        List<MemberLevel> memberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(user.getWineryId(), "A");
        for (MemberLevel memberLevel : memberLevelList) {
                MemberLevelDTO memberLevelDTO = new MemberLevelDTO();
                memberLevelDTO.setName(memberLevel.getName());
                memberLevelDTO.setUpgradeExperience(memberLevel.getUpgradeExperience().toString());
                memberLevelDTOList.add(memberLevelDTO);
        }
        userMemberLevelDTO.setMemberLevelDTOList(memberLevelDTOList);
        return userMemberLevelDTO;
    }


    public void updateMemberUserLevel(Integer levelId, Integer winerId) {
        List<MemberUser> memberUserList = memberUserRepository.findByWineryId(winerId);
        if (memberUserList != null && memberUserList.size() > 0) {
            List<MemberLevel> upgradeMemberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(winerId, "A");
            for (MemberUser memberUser : memberUserList) {
                UserExperience userExperience = userExperienceRepository.findByUserId(memberUser.getUserId());
                for (MemberLevel memberLevel : upgradeMemberLevelList) {
                    if (userExperience.getExperience()>= memberLevel.getUpgradeExperience()) {
                            memberUser.setMemberLevelId(memberLevel.getId());
                            memberUserRepository.saveAndFlush(memberUser);
                    }
                }
            }
        }
    }


}

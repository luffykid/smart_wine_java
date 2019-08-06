package com.changfa.frame.service.jpa.banner;


import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.saas.SBannerDTO;
import com.changfa.frame.data.dto.wechat.BannerDTO;
import com.changfa.frame.data.entity.activity.AcitivityRange;
import com.changfa.frame.data.entity.activity.Activity;
import com.changfa.frame.data.entity.banner.Banner;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.market.MarketActivityRange;
import com.changfa.frame.data.entity.market.MarketActivityType;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.repository.activity.ActivityRangeRepository;
import com.changfa.frame.data.repository.activity.ActivityRepository;
import com.changfa.frame.data.repository.banner.BannerRepository;
import com.changfa.frame.data.repository.market.MarketActivityRangeRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.market.MarketActivityTypeRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.data.repository.user.UserLoginLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BannerService {

    private static Logger log = LoggerFactory.getLogger(BannerService.class);

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Autowired
    private MemberWechatRepository memberWechatRepository;

    @Autowired
    private ActivityRangeRepository activityRangeRepository;

    @Autowired
    private MarketActivityRangeRepository marketActivityRangeRepository;

    @Autowired
    private MarketActivityTypeRepository marketActivityTypeRepository;


    /* *
     * 获取轮播图
     * @Author        zyj
     * @Date          2018/10/19 10:15
     * @Description
     * */
    public List<BannerDTO> getBanner(Member user, String type) {
        List<Banner> bannerList = bannerRepository.findByWineryIdAndTypeAndStatus(user.getWineryId().intValue(), type, "A");
        if (bannerList != null) {
            List<BannerDTO> bannerDTOList = new ArrayList<>();
            for (Banner banner : bannerList) {
                BannerDTO bannerDTO = new BannerDTO();
                bannerDTO.setBannerId(banner.getId());
                if (banner.getLogo() != null) {
                    bannerDTO.setLogo((banner.getLogo().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(banner.getLogo())) : banner.getLogo());
                }
                if (banner.getActivityId() != null) {
                    bannerDTO.setType("A");
                    bannerDTO.setActivityId(banner.getActivityId());
                }
                if (banner.getMarketActivityId() != null) {
                    bannerDTO.setType("M");
                    bannerDTO.setActivityId(banner.getMarketActivityId());
                }
                if (banner.getProdId() != null) {
                    bannerDTO.setType("P");
                    bannerDTO.setProdId(banner.getProdId());
                }
                if (user != null) {
                    MemberWechat memberUser = memberWechatRepository.findByMbrId(user.getId().intValue());
                    if (banner.getActivityId() != null) {
                        List<AcitivityRange> acitivityRange = activityRangeRepository.findByActivityIdAndMemberLevelId(banner.getActivityId(), memberUser.getMemberLevel());
                        if (acitivityRange != null && acitivityRange.size() > 0) {
                            bannerDTOList.add(bannerDTO);
                        }
                    } else if (banner.getMarketActivityId() != null) {
                        MarketActivity marketActivity = marketActivityRepository.getOne(banner.getMarketActivityId());
                       /* MarketActivityType marketActivityType = marketActivityTypeRepository.findByWineryIdAndLike(user.getWineryId(),"生日赠券");*/
                        if (marketActivity!=null) {
                            MarketActivityType marketActivityType = marketActivityTypeRepository.getOne(marketActivity.getMarketActivityTypeId());
                            if (marketActivityType!=null && marketActivityType.getName().equals("新会员赠券")){
                                bannerDTOList.add(bannerDTO);
                            }
                            List<MarketActivityRange> marketActivityRangeList = marketActivityRangeRepository.findByMarketActivityIdAndMemberLevelId(banner.getMarketActivityId(), memberUser.getMemberLevel());
                            if (marketActivityRangeList != null && marketActivityRangeList.size() > 0) {
                                bannerDTOList.add(bannerDTO);
                            }
                        }/*else{
                            bannerDTOList.add(bannerDTO);
                        }*/
                    }

                }

            }
            return bannerDTOList;
        }
        return null;
    }


    /* *
     * 添加首页轮播图
     * @Author        zyj
     * @Date          2018/11/5 17:51
     * @Description
     * */
    public Boolean addBanner(AdminUser adminUser, SBannerDTO sBannerDTO, String bannerType) {
        Banner bannerLike = bannerRepository.findByWineryIdAndNameAndType(adminUser.getWineryId(), sBannerDTO.getName(), bannerType);
        if (bannerLike != null) {
            return false;
        }
        Banner banner = new Banner();
        if (sBannerDTO.getActivityType() != null) {
            if (sBannerDTO.getActivityType().equals("A")) {
                banner.setActivityId(sBannerDTO.getActivityId());
            } else {
                banner.setMarketActivityId(sBannerDTO.getActivityId());
            }
        } else {
            banner.setActivityId(sBannerDTO.getActivityId());
        }
        banner.setType(bannerType);
        banner.setWineryId(adminUser.getWineryId());
        banner.setCreateUserId(adminUser.getId());
        banner.setName(sBannerDTO.getName());
        banner.setStatus(sBannerDTO.getStatus());
        banner.setLogo(sBannerDTO.getLogo().substring(sBannerDTO.getLogo().indexOf("/vimg")));
        banner.setDescri(sBannerDTO.getDescri());
        Integer sum = bannerRepository.findSumByTypeAndStatus(adminUser.getWineryId(), bannerType);
        if (sum != null) {
            if (bannerType.equals("B")) {
                if (sum >= 3) {
                    banner.setStatus("P");
                }
            } else {
                if (sum >= 1) {
                    banner.setStatus("P");
                }
            }
        }
        banner.setCreateTime(new Date());
        banner.setStatusTime(new Date());
        bannerRepository.saveAndFlush(banner);
        return true;
    }

    /* *
     * 首页轮播图列表
     * @Author        zyj
     * @Date          2018/11/6 10:18
     * @Description
     * */
    public List<SBannerDTO> getBannerList(AdminUser adminUser, String bannerType) {
        List<Banner> bannerList = bannerRepository.findByWineryIdAndType(adminUser.getWineryId(), bannerType);
        if (bannerList != null) {
            List<SBannerDTO> sBannerDTOList = new ArrayList<>();
            for (Banner banner : bannerList) {
                SBannerDTO sBannerDTO = new SBannerDTO();
                sBannerDTO.setId(banner.getId());
                sBannerDTO.setWineryId(adminUser.getWineryId());
                sBannerDTO.setName(banner.getName());
                if (banner.getLogo() != null) {
                    sBannerDTO.setLogo((banner.getLogo().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(banner.getLogo())) : banner.getLogo());
                }
                if (banner.getActivityId() != null) {
                    sBannerDTO.setActivityId(banner.getActivityId());
                    sBannerDTO.setActivityType("A");
                    Activity activity = activityRepository.getOne(banner.getActivityId());
                    if (activity != null) {
                        sBannerDTO.setActivityName(activity.getName());
                    }
                } else {
                    sBannerDTO.setActivityId(banner.getMarketActivityId());
                    sBannerDTO.setActivityType("M");
                    MarketActivity marketActivity = marketActivityRepository.getOne(banner.getMarketActivityId());
                    if (marketActivity != null) {
                        sBannerDTO.setActivityName(marketActivity.getName());
                    }
                }
                sBannerDTO.setStatus(banner.getStatus());
                sBannerDTOList.add(sBannerDTO);
            }
            return sBannerDTOList;
        }
        return null;
    }

    /* *
     * 查看详情
     * @Author        zyj
     * @Date          2018/11/6 17:14
     * @Description
     * */
    public SBannerDTO getBannerDetail(AdminUser adminUser, Integer bannerId) {
        Banner banner = bannerRepository.getOne(bannerId);
        SBannerDTO sBannerDTO = new SBannerDTO();
        sBannerDTO.setId(banner.getId());
        sBannerDTO.setName(banner.getName());
        sBannerDTO.setWineryId(adminUser.getWineryId());
        if (banner.getLogo() != null) {
            sBannerDTO.setLogo((banner.getLogo().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(banner.getLogo())) : banner.getLogo());
        }
        if (banner.getActivityId() != null) {
            sBannerDTO.setActivityId(banner.getActivityId());
            sBannerDTO.setActivityType("A");
            Activity activity = activityRepository.getOne(banner.getActivityId());
            if (activity != null) {
                sBannerDTO.setActivityName(activity.getName());
            }
        } else {
            sBannerDTO.setActivityId(banner.getMarketActivityId());
            sBannerDTO.setActivityType("M");
            MarketActivity marketActivity = marketActivityRepository.getOne(banner.getMarketActivityId());
            if (marketActivity != null) {
                sBannerDTO.setActivityName(marketActivity.getName());
            }
        }
        sBannerDTO.setDescri(banner.getDescri());
        sBannerDTO.setStatus(banner.getStatus());
        return sBannerDTO;
    }

    /* *
     * 操做（启用停用）
     * 0@Author        zyj
     * @Date          2018/11/6 17:17
     * @Description
     * */
    public String bannerOperade(AdminUser adminUser, Integer bannerId, String Operade, String bannerType) {
        if (Operade.equals("A")) {
            Integer sum = bannerRepository.findSumByTypeAndStatus(adminUser.getWineryId(), bannerType);
            if (bannerType.equals("A")) {
                if (sum >= 1) {
                    return "已有进行中的banner请先暂停";
                }
            } else {
                if (sum >= 3) {
                    return "已有进行中的banner请先暂停";
                }
            }
        }

        Banner banner = bannerRepository.getOne(bannerId);
        if (banner.getMarketActivityId()!=null){
           MarketActivity marketActivity = marketActivityRepository.getOne(banner.getMarketActivityId());
           if (marketActivity.getStatus().equals("P")){
               return "活动已禁用，请先启用关联活动";
           }
        }else{
           Activity activity = activityRepository.getOne(banner.getActivityId());
            if (activity.getStatus().equals("P")){
                return "活动已禁用，请先启用关联活动";
            }
        }
        banner.setStatus(Operade);
        banner.setStatusTime(new Date());
        bannerRepository.saveAndFlush(banner);
        return "成功";
    }

    /* *
     * 删除一条banner
     * @Author        zyj
     * @Date          2018/11/6 17:29
     * @Description
     * */
    public Boolean deleteBanner(Integer bannerId) {
        Banner banner = bannerRepository.getOne(bannerId);
        if (banner.getStatus().equals("P")) {
            bannerRepository.deleteById(bannerId);
        } else {
            return false;
        }
        return true;
    }


    /* *
     * 编辑
     * @Author        zyj
     * @Date          2018/11/6 18:01
     * @Description
     * */
    public String updateBanner(AdminUser adminUser, SBannerDTO sBannerDTO, String bannerType) {
        Banner banerLike = bannerRepository.findByWineryIdAndNameAndType(adminUser.getWineryId(), sBannerDTO.getName(), bannerType);
        if (banerLike != null && !banerLike.getId().equals(sBannerDTO.getId())) {
            return "banner名称不可重复";
        }
        Banner banner = bannerRepository.getOne(sBannerDTO.getId());
        if (sBannerDTO.getActivityType() != null) {
            if (sBannerDTO.getActivityType().equals("A")) {
                banner.setActivityId(sBannerDTO.getActivityId());
                banner.setMarketActivityId(null);
            }
            if (sBannerDTO.getActivityType().equals("M")) {
                banner.setMarketActivityId(sBannerDTO.getActivityId());
                banner.setActivityId(null);
            }
        } else {
            banner.setActivityId(sBannerDTO.getActivityId());
        }
        if (sBannerDTO != null) {
            banner.setName(sBannerDTO.getName());
        }
        if (sBannerDTO.getStatus() != null) {
            String op = bannerOperade(adminUser, banner.getId(), sBannerDTO.getStatus(), bannerType);
            if (op.equals("成功")) {
                banner.setStatus(sBannerDTO.getStatus());
                banner.setStatusTime(new Date());
            } else {
                banner.setStatus("P");
                banner.setStatusTime(new Date());
            }
        }
        if (sBannerDTO.getLogo() != null) {
            banner.setLogo(sBannerDTO.getLogo().substring(sBannerDTO.getLogo().indexOf("/vimg")));
        }
        if (sBannerDTO.getDescri() != null) {
            banner.setDescri(sBannerDTO.getDescri());
        }

        bannerRepository.saveAndFlush(banner);
        return "成功";
    }


    /* *
     * 获取banner链接活动列表
     * @Author        zyj
     * @Date          2018/11/8 9:37
     * @Description
     * */
    public List<Map<String, Object>> getActivityList(AdminUser adminUser, String type) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (type.equals("B")) {
            List<MarketActivity> marketActivityList = marketActivityRepository.findByWineryIdAndStatus(adminUser.getWineryId(), "A");
            if (marketActivityList != null && marketActivityList.size() > 0) {
                for (MarketActivity marketActivity : marketActivityList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("activityType", "M");
                    map.put("activityName", marketActivity.getName());
                    map.put("activityId", marketActivity.getId());
                    mapList.add(map);
                }
            }
        }

        if (type.equals("A") || type.equals("B")) {
            List<Activity> activityList = activityRepository.findByWineryIdAndStatus(adminUser.getWineryId(), "A");
            if (activityList != null && activityList.size() > 0) {
                for (Activity activity : activityList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("activityType", "A");
                    map.put("activityName", activity.getName());
                    map.put("activityId", activity.getId());
                    mapList.add(map);
                }
            }
        }

        return mapList;
    }

    public Banner checkActivityId(Integer id) {
        return bannerRepository.findByActivityId(id);
    }

    public Banner checkActivityIdAndStatus(Integer id) {
        return bannerRepository.findByActivityIdAndStatus(id);
    }

    public Banner checkMarketActivityId(Integer id) {
        return bannerRepository.findByMarketActivityId(id);
    }

    public Banner checkMarketActivityIdAndStatus(Integer id) {
        return bannerRepository.findByMarketActivityIdAndStatus(id);
    }

    public Map activityStatus(String bannerType, AdminUser adminUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "A");
        Integer sum = bannerRepository.findSumByTypeAndStatus(adminUser.getWineryId(), bannerType);
        if (sum != null) {
            if (bannerType.equals("B")) {
                if (sum >= 3) {
                    map.put("status", "P");
                }
            } else {
                if (sum >= 1) {
                    map.put("status", "P");
                }
            }
        }
        return map;
    }

    public Banner checkProdId(Integer prodId) {
        return bannerRepository.findByProdId(prodId);
    }
}

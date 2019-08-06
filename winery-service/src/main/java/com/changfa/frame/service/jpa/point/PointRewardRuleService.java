package com.changfa.frame.service.jpa.point;


import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.point.*;

import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.*;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.point.*;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.user.*;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class PointRewardRuleService {

    @Autowired
    private PointRewardRuleRepository pointRewardRuleRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private UserPointDetailRepository userPointDetailRepository;

    @Autowired
    private UserExperienceRepository userExperienceRepository;

    @Autowired
    private UserExperienceDetailRepository userExperienceDetailRepository;

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Autowired
    private MemberWechatRepository memberWechatRepository;

    @Autowired
    private MemberLevelRightRepository memberLevelRightRepository;

    @Autowired
    private PointExchangeVoucherRepository pointExchangeVoucherRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private VoucherInstRepository voucherInstRepository;

    @Autowired
    private PointToVoucherRepository pointToVoucherRepository;

    public void updateUserPoint(Member user, BigDecimal payMoney, String action, String orderNo, Integer orderId) {
        PointRewardRule pointRewardRule = pointRewardRuleRepository.findByWineryIdAndStatus(Integer.valueOf(user.getWineryId().toString()), "A");
        if (pointRewardRule != null) {
            if (pointRewardRule.getIsLongTime().equals("Y")) {
                if (pointRewardRule.getIsLimit().equals("Y")) {
                    addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                } else {
                    Integer pointSum = null;
                    if (action.equals("D")) {
                        pointSum = userPointDetailRepository.findByUserIdAndAction(Integer.valueOf(user.getId().toString()));
                        if (pointSum < pointRewardRule.getEveryDayLimit()) {
                            addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                        }
                    } else if (action.equals("W")) {
                        pointSum = userPointDetailRepository.findByUserIdAndActionCon(Integer.valueOf(user.getId().toString()));
                        if (pointSum < pointRewardRule.getEveryDayLimit()) {
                            addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                        }
                    } else {
                        addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                    }

                }
            } else {
                if (new Date().after(pointRewardRule.getBeginTime()) && new Date().before(pointRewardRule.getEndTime())) {
                    if (pointRewardRule.getIsLimit().equals("Y")) {
                        addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                    } else {
                        Integer pointSum = null;
                        if (action.equals("D")) {
                            pointSum = userPointDetailRepository.findByUserIdAndAction(Integer.valueOf(user.getId().toString()));
                            if (pointSum < pointRewardRule.getEveryDayLimit()) {
                                addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                            }
                        } else if (action.equals("W")) {
                            pointSum = userPointDetailRepository.findByUserIdAndActionCon(Integer.valueOf(user.getId().toString()));
                            if (pointSum < pointRewardRule.getEveryDayLimit()) {
                                addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                            }
                        } else {
                            addPoint(action, payMoney, pointRewardRule, user, orderNo, orderId);
                        }
                    }
                }
            }
        } /*else {
            if (action.equals("W")) {
                MemberWechat memberUser = memberWechatRepository.findByUserId(user.getId());
                if (memberUser != null) {
                    MemberLevelRight memberLevelRight = memberLevelRightRepository.findByMemberLevelId(memberUser.getMemberLevelId());
                    if (payMoney.compareTo(memberLevelRight.getConsumptionAmount()) >= 0) {
                        addUserPoint(user, memberLevelRight.getPoint(), action, orderNo, orderId);
                        addExperience(user, memberLevelRight.getPoint(), action, orderId);
                    }
                }
            }
        }*/

    }

    public void addUserPoint(Member user, Integer point, String action, String orderNo, Integer orderId) {
        if (point != null) {
            UserPoint userPoint = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
            if (action.equals("D") || action.equals("W") || action.equals("L")) {
                userPoint.setPoint(userPoint.getPoint() + point);
            } else {
                userPoint.setPoint(userPoint.getPoint() - point);
            }
            userPoint.setUpdateTime(new Date());
            UserPoint userPointSave = userPointRepository.saveAndFlush(userPoint);

            UserPointDetail userPointDetail = new UserPointDetail();
            userPointDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            userPointDetail.setUserId(Integer.valueOf(user.getWineryId().toString()));
            userPointDetail.setAction(action);
            userPointDetail.setPoint(point);
            if (action.equals("D") || action.equals("W") || action.equals("L")) {
                userPointDetail.setPointType("A");
            } else {
                userPointDetail.setPointType("M");
            }
            userPointDetail.setLatestPoint(userPointSave.getPoint());
            userPointDetail.setOrderId(orderId);
            userPointDetail.setOrderNo(orderNo);
            userPointDetail.setCreateTime(new Date());
            userPointDetailRepository.saveAndFlush(userPointDetail);
        }
    }


    /* *
     * 增加经验值
     * @Author        zyj
     * @Date          2018/10/26 11:41
     * @Description
     * */
    public void addExperience(Member user, Integer point, String action, Integer orderId) {
        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        UserExperience userExperience = userExperienceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        userExperience.setExperience(userExperience.getExperience() + point);
        userExperience.setUpdateTime(new Date());
        UserExperience userExperienceSave = userExperienceRepository.saveAndFlush(userExperience);
        //判断是否需要升级
        List<MemberLevel> upgradeMemberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(Integer.valueOf(user.getWineryId().toString()), "A");
        if (upgradeMemberLevelList != null) {
            for (MemberLevel memberLevel : upgradeMemberLevelList) {
                MemberLevel userMemberLevel = memberLevelRepository.getOne(memberUser.getMemberLevel());
                if (userExperienceSave.getExperience() >= memberLevel.getUpgradeExperience()) {
                    if (memberLevel.getUpgradeExperience() > userMemberLevel.getUpgradeExperience()) {
                        memberUser.setMemberLevel(memberLevel.getId());
                        memberWechatRepository.saveAndFlush(memberUser);
                    }
                }
            }
        }

        //经验验值明细
        UserExperienceDetail userExperienceDetail = new UserExperienceDetail();
        userExperienceDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userExperienceDetail.setUserId(Integer.valueOf(user.getWineryId().toString()));
        userExperienceDetail.setAction(action);
        userExperienceDetail.setExperience(point);
        userExperienceDetail.setExperienceType("A");
        userExperienceDetail.setLatestExperience(userExperienceSave.getExperience());
        userExperienceDetail.setOrderId(orderId);
        userExperienceDetail.setCreateTime(new Date());
        userExperienceDetailRepository.saveAndFlush(userExperienceDetail);

    }

    public void addPoint(String action, BigDecimal payMoney, PointRewardRule pointRewardRule, Member user, String orderNo, Integer orderId) {
        if (action.equals("D")) {
            if (payMoney.compareTo(pointRewardRule.getDepositMoneyPoint()) >= 0) {
                Integer point = payMoney.divide(pointRewardRule.getDepositMoneyPoint(), 0, BigDecimal.ROUND_HALF_UP).intValue();
                addUserPoint(user, point, action, orderNo, orderId);
                addExperience(user, point, action, orderId);
            }
        }
        if (action.equals("W")) {
            if (payMoney.compareTo(pointRewardRule.getConsumeMoneyPoint()) >= 0) {
                Integer point = payMoney.divide(pointRewardRule.getConsumeMoneyPoint(), 0, BigDecimal.ROUND_HALF_UP).intValue();
                addUserPoint(user, point, action, orderNo, orderId);
                addExperience(user, point, action, orderId);
            }
        }
    }

    /* *
     * 获取积分可兑换的券列表
     * @Author        zyj
     * @Date          2018/11/22 17:01
     * @Description
     * */
    public List<VoucherInstDTO> getPointToVoucher(Member user) {
        PointRewardRule pointRewardRule = pointRewardRuleRepository.findByWineryIdAndStatus(Integer.valueOf(user.getWineryId().toString()), "A");
        if (pointRewardRule != null) {
            if (pointRewardRule.getIsLongTime().equals("Y")) {
                return getVoucherInstDTOList(pointRewardRule);
            } else {
                if (pointRewardRule.getBeginTime().before(new Date()) && pointRewardRule.getEndTime().after(new Date())) {
                    return getVoucherInstDTOList(pointRewardRule);
                }
            }
        }
        return null;
    }

    public List<VoucherInstDTO> getVoucherInstDTOList(PointRewardRule pointRewardRule) {
        List<VoucherInstDTO> voucherInstDTOList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<PointExchangeVoucher> pointExchangeVoucherList = pointExchangeVoucherRepository.findByPointRewardRuleIdAndStatus(pointRewardRule.getId(), "A");
        if (pointExchangeVoucherList != null && pointExchangeVoucherList.size() > 0) {
            for (PointExchangeVoucher pointExchangeVoucher : pointExchangeVoucherList) {
                Voucher voucher = voucherRepository.getOne(pointExchangeVoucher.getVoucherId());
                if (voucher != null) {
                    VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
                    voucherInstDTO.setName(voucher.getName());
                    voucherInstDTO.setVoucherInstId(voucher.getId());
                    voucherInstDTO.setScope(voucher.getScope());
                    voucherInstDTO.setPoint(pointExchangeVoucher.getPoint());
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.DAY_OF_YEAR, voucher.getUsefulTime());
                    voucherInstDTO.setUsefulTime(format.format(now.getTime()));
                    if (voucher.getType().equals("D")) {
                        voucherInstDTO.setDisCount(voucher.getDiscount() + "%");
                        voucherInstDTO.setType("D");
                    }
                    if (voucher.getType().equals("G")) {
                        Prod prod = prodRepository.getOne(voucher.getExchangeProdId());
                        if (prod != null) {
                            voucherInstDTO.setGift(prod.getName());
                        }
                        voucherInstDTO.setType("G");
                    }
                    if (voucher.getType().equals("M")) {
                        voucherInstDTO.setMoney(voucher.getMoney());
                        voucherInstDTO.setType("M");
                    }
                    voucherInstDTOList.add(voucherInstDTO);
                }
            }
            return voucherInstDTOList;

        }
        return null;
    }


    /* *
     * 使用积分兑换对应的券
     * @Author        zyj
     * @Date          2018/11/23 9:25
     * @Description
     * */
    public Boolean pointToVoucher(Member user, Integer voucherId) {
        //积分奖励券记录规则
        PointExchangeVoucher pointExchangeVoucher = pointExchangeVoucherRepository.findByWineryIdAndVoucherId(Integer.valueOf(user.getWineryId().toString()), voucherId);
        //赠券模板
        UserPoint userPoint = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        //判断用户积分是否可以兑换
        if (userPoint.getPoint() >= pointExchangeVoucher.getPoint()) {
            Voucher voucher = voucherRepository.getOne(voucherId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            String tempUserId = String.format("%02d", user.getId());
            String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
            //计算生效时间
            Calendar caEff = Calendar.getInstance();
            caEff.add(Calendar.DATE, voucher.getEnableDay());// num为增加的天数，可以改变的
            Date effectiveTime = caEff.getTime();
            Date ineffectiveTime = voucher.getIneffectiveTime();
            if (voucher.getUsefulTime() != null) {
                Calendar caIne = Calendar.getInstance();
                caIne.add(Calendar.DATE, voucher.getUsefulTime());
                ineffectiveTime = caIne.getTime();
            }
            //添加实体券
            VoucherInst voucherInst = new VoucherInst(Integer.valueOf(user.getWineryId().toString()), voucher.getName(), voucher.getId(), format, voucher.getType(), voucher.getScope(), voucher.getCanPresent(), voucher.getMoney(), voucher.getDiscount(), voucher.getExchangeProdId(), voucher.getEnableType(), voucher.getEnableMoeny(), effectiveTime, ineffectiveTime, "A", new Date(), new Date());
            VoucherInst voucherInstSave = voucherInstRepository.saveAndFlush(voucherInst);
            //添加用户券记录
            UserVoucher userVoucher = new UserVoucher();
            userVoucher.setUserId(Integer.valueOf(user.getId().toString()));
            userVoucher.setCreateTime(new Date());
            userVoucher.setVoucherInstId(voucherInstSave.getId());
            userVoucher.setSendTime(new Date());
            userVoucher.setGetTime(new Date());
            userVoucher.setIneffectiveTime(voucherInstSave.getIneffectiveTime());
            userVoucherRepository.saveAndFlush(userVoucher);
            //添加用户积分换券记录
            PointToVoucher pointToVoucher = new PointToVoucher();
            //生成ding订单号
            String orderNo = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
            pointToVoucher.setOrderNo(orderNo);
            pointToVoucher.setPoint(pointExchangeVoucher.getPoint());
            pointToVoucher.setUserId(Integer.valueOf(user.getId().toString()));
            pointToVoucher.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            pointToVoucher.setVoucherInstId(voucherInstSave.getId());
            pointToVoucher.setCreateTime(new Date());
            PointToVoucher pointToVoucherSave = pointToVoucherRepository.saveAndFlush(pointToVoucher);
            //修改用户积分
            userPoint.setPoint(userPoint.getPoint() - pointExchangeVoucher.getPoint());
            userPoint.setUpdateTime(new Date());
            UserPoint userPointUpdate = userPointRepository.saveAndFlush(userPoint);
            //添加积分详情
            UserPointDetail userPointDetail = new UserPointDetail();
            userPointDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            userPointDetail.setUserId(Integer.valueOf(user.getId().toString()));
            userPointDetail.setAction("G");
            userPointDetail.setPoint(pointExchangeVoucher.getPoint());
            userPointDetail.setPointType("M");
            userPointDetail.setLatestPoint(userPointUpdate.getPoint());
            userPointDetail.setOrderId(pointToVoucherSave.getId());
            userPointDetail.setOrderNo(pointToVoucherSave.getOrderNo());
            userPointDetail.setCreateTime(new Date());
            userPointDetailRepository.saveAndFlush(userPointDetail);
            return true;
        } else {
            return false;
        }
    }

    /* *
     * 兑换券规则详情
     * @Author        zyj
     * @Date          2018/11/23 10:43
     * @Description
     * */
    public Map<String, Object> getVoucherDetail(Member user, Integer voucherId) {
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Voucher voucher = voucherRepository.getOne(voucherId);
        PointExchangeVoucher pointExchangeVoucher = pointExchangeVoucherRepository.findByWineryIdAndVoucherId(Integer.valueOf(user.getWineryId().toString()), voucherId);
        VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
        voucherInstDTO.setName(voucher.getName());
        voucherInstDTO.setVoucherInstId(voucher.getId());
        voucherInstDTO.setScope(voucher.getScope());
        voucherInstDTO.setPoint(pointExchangeVoucher.getPoint());
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, voucher.getUsefulTime());
        voucherInstDTO.setUsefulTime(format.format(now.getTime()));
        if (voucher.getType().equals("D")) {
            voucherInstDTO.setDisCount(voucher.getDiscount() + "%");
            voucherInstDTO.setType("D");
        }
        if (voucher.getType().equals("G")) {
            Prod prod = prodRepository.getOne(voucher.getExchangeProdId());
            if (prod != null) {
                voucherInstDTO.setGift(prod.getName());
            }
            voucherInstDTO.setType("G");
        }
        if (voucher.getType().equals("M")) {
            voucherInstDTO.setMoney(voucher.getMoney());
            voucherInstDTO.setType("M");
        }
        map.put("voucher", voucherInstDTO);
        String scope = null;
        switch (voucher.getScope()) {
            case "A":
                scope = "活动";
                break;
            case "B":
                scope = "商城消费";
                break;
            default:
                scope = "活动、商城消费";
                break;
        }
        map.put("rule", Arrays.asList("1.消费满" + voucher.getEnableMoeny() + "元可用", "2.每次消费最多使用" + voucher.getQuantityLimit() + "张", "3.领券" + voucher.getEnableDay() + "日后可用", "4.有效期：发券之日起" + voucher.getUsefulTime() + "日内有效", "5.使用范围：" + scope + "", voucher.getCanPresent().equals("Y") ? "6.可以转增" : "6.不可以转增"));
        map.put("descri", Arrays.asList("1.一经兑换，一律不退换积分。", "2.通过非法途径获得积分后进行的正常兑换行为，商家有权不提供服务", "3.凡以不正当手段（包括但不限于作弊，干扰系统、实施网络攻击等）进行兑换，平台有权终止该次兑换)"));
        return map;
    }
}
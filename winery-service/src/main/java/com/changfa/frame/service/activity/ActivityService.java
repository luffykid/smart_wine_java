package com.changfa.frame.service.activity;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.saas.ActivityListDTO;
import com.changfa.frame.data.dto.saas.ActivityOrderListDTO;
import com.changfa.frame.data.dto.saas.ActivitytDTO;
import com.changfa.frame.data.dto.wechat.ActivityDTO;
import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.activity.*;
import com.changfa.frame.data.entity.common.CacheUtil;
import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.deposit.UserDepositDetail;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.market.MarketActivitySpecDetail;
import com.changfa.frame.data.entity.order.OrderPay;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.activity.*;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import com.changfa.frame.data.repository.deposit.UserDepositDetailRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.market.MarketActivitySpecDetailRepository;
import com.changfa.frame.data.repository.order.OrderPayRepository;
import com.changfa.frame.data.repository.user.MemberLevelRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.PicturePathUntil;
import com.changfa.frame.service.dict.DictService;
import com.changfa.frame.service.market.MarketActivityService;
import com.changfa.frame.service.point.PointRewardRuleService;
import com.changfa.frame.service.user.MemberService;
import com.changfa.frame.service.util.QRcodeUtil2;
import com.changfa.frame.service.voucher.UserVoucherService;
import com.changfa.frame.service.wechat.conf.WeChatConts;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/* *
 * @Author        zyj
 * @Date          2018/10/12 18:05
 * @Description
 * */
@Service
public class ActivityService {

    private static Logger log = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private DictService dictService;

    @Autowired
    private ActivityContentService activityContentService;

    @Autowired
    private ActivityContentRepository activityContentRepository;

    @Autowired
    private ActivityLogoService activityLogoService;

    @Autowired
    private ActivityLogoRepository activityLogoRepository;

    @Autowired
    private ActivityOrderPriceRepository activityOrderPriceRepository;

    @Autowired
    private ActivityOrderPriceItemRepository activityOrderPriceItemRepository;

    @Autowired
    private ActivityOrderVoucherRepository activityOrderVoucherRepository;

    @Autowired
    private ActivityRangeRepository activityRangeRepository;

    @Autowired
    private ActivitySendVoucherRepository activitySendVoucherRepository;

    @Autowired
    private UserVoucherService userVoucherService;

    @Autowired
    private VoucherInstRepository voucherInstRepository;

    @Autowired
    private ActivityOrderRepository activityOrderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private OrderPayRepository orderPayRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserDepositDetailRepository userDepositDetailRepository;

    @Autowired
    private PointRewardRuleService pointRewardRuleService;

    @Autowired
    private UserActivityTicketRepository userActivityTicketRepository;

    @Autowired
    private UserActivityTicketService userActivityTicketService;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    @Autowired
    private MemberWechatRepository memberWechatRepository;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private MarketActivityService marketActivityService;
    @Autowired
    private MarketActivitySpecDetailRepository marketActivitySpecDetailRepository;

    /* *
     * 获取每个酒庄所有线下活动
     * @Author        zyj
     * @Date          2018/10/12 18:05
     * @Description
     * */
    public List<ActivityDTO> getActivityList(Member user) {
        List<Activity> activityList = activityRepository.findByWineryIdAndStatusOrderByBeginTimeDesc(Integer.valueOf(user.getWineryId().toString()), "A");
        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        if (activityList != null && activityList.size() > 0) {
            List<ActivityDTO> activityDTOList = new ArrayList<>();
            for (Activity activity : activityList) {
                List<AcitivityRange> acitivityRange = activityRangeRepository.findByActivityIdAndMemberLevelId(activity.getId(), memberUser.getMemberLevel());
                if (acitivityRange != null && acitivityRange.size() > 0) {
                    ActivityDTO activityDTO = new ActivityDTO();
                    activityDTO.setActivityId(activity.getId());
                    activityDTO.setName(activity.getName());
                    activityDTO.setAddress(activity.getFullAddress());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    activityDTO.setBeginTime(formatter.format(activity.getBeginTime()));
                    activityDTO.setEndTime(formatter.format(activity.getEndTime()));

                    Integer quantity = (activityOrderRepository.findSumByActivityAndStatus(activity.getId())) == null ? 0 : (activityOrderRepository.findSumByActivityAndStatus(activity.getId()));
                    if (activity.getEndTime().before(new Date())) {
                        activityDTO.setStatus("停止报名");
                    } else {
                        if (quantity != null) {
                            if (activity.getQuota() > quantity) {
                                activityDTO.setStatus("火热报名");
                            } else {
                                activityDTO.setStatus("停止报名");
                            }
                        } else {
                            activityDTO.setStatus("火热报名");
                        }

                    }
                    activityDTOList.add(activityDTO);
                }
            }
            return activityDTOList;
        }
        return null;
    }

    /* *
     * 活动详情
     * @Author        zyj
     * @Date          2018/10/15 11:08
     * @Description
     * */
    public ActivityDTO getActivityDetail(Member user, int activityId) {
        ActivityDTO activityDTO = new ActivityDTO();
        Activity activity = activityRepository.getOne(activityId);
        if (user != null) {
            MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
            List<AcitivityRange> acitivityRange = activityRangeRepository.findByActivityIdAndMemberLevelId(activity.getId(), memberUser.getMemberLevel());
            if (acitivityRange != null && acitivityRange.size() > 0) {
                activityDTO.setIsJoin("Y");
            } else {
                activityDTO.setIsJoin("N");
            }
        }
        if (activity != null) {
            activityDTO.setActivityId(activity.getId());
            activityDTO.setName(activity.getName());
            activityDTO.setAddress(activity.getFullAddress());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            activityDTO.setBeginTime(formatter.format(activity.getBeginTime()));
            activityDTO.setEndTime(formatter.format(activity.getEndTime()));
            activityDTO.setPrice(activity.getEnrollPrice());
            ActivityLogo activityLogo = activityLogoService.findByActivityIdAndIsDefault(activityId, "Y");
            if (activityLogo != null) {
                activityDTO.setLogo((activityLogo.getLogo().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(activityLogo.getLogo())) : activityLogo.getLogo());
            }
            ActivityContent activityContent = activityContentService.findByActivityId(activityId);
            if (activityContent != null) {
                activityDTO.setContent(activityContent.getContent());
            }
            Integer sum = (activityOrderRepository.findSumByActivityAndStatus(activity.getId())) == null ? 0 : (activityOrderRepository.findSumByActivityAndStatus(activity.getId()));
            if (activity.getEndTime().before(new Date())) {
                activityDTO.setStatus("P");
            } else {
                if (sum != null) {
                    if (activity.getQuota() > sum) {
                        activityDTO.setStatus("A");
                    } else {
                        activityDTO.setStatus("P");
                    }
                } else {
                    activityDTO.setStatus("A");
                }
            }
            return activityDTO;
        }
        return null;
    }

    public ActivityOrder findByOrderNo(String orderNo) {
        return activityOrderRepository.findByOrderNo(orderNo);
    }

    public ActivityOrder findByOrderId(Integer orderId) {
        return activityOrderRepository.getOne(orderId);
    }

    /* *
     * 线下活动立即报名
     * @Author        zyj
     * @Date          2018/10/16 11:15
     * @Description
     * */
    public ActivityDTO enroll(Member user, Integer activityId) {
        Activity activity = activityRepository.getOne(activityId);
        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        if (activity != null) {
            List<AcitivityRange> acitivityRange = activityRangeRepository.findByActivityIdAndMemberLevelId(activity.getId(), memberUser.getMemberLevel());
            if (acitivityRange != null && acitivityRange.size() > 0) {
                ActivityDTO activityDTO = new ActivityDTO();
                activityDTO.setActivityId(activity.getId());
                activityDTO.setName(activity.getName());
                activityDTO.setAddress(activity.getFullAddress());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                activityDTO.setBeginTime(formatter.format(activity.getBeginTime()));
                activityDTO.setEndTime(formatter.format(activity.getEndTime()));
                activityDTO.setPrice(activity.getEnrollPrice());
                return activityDTO;
            }
        }
        return null;
    }

    /* *
     * @Author        zyj
     * @Date          2018/10/19 16:26
     * @Description
     * */
    public BigDecimal getOrderPrice(Integer activityId, Integer quantity) {
        Activity activity = activityRepository.getOne(activityId);
        return activity.getEnrollPrice().multiply(new BigDecimal(quantity));
    }

    /* *
     * 线下活动报名所有优惠券
     * @Author        zyj
     * @Date          2018/10/19 16:21
     * @Description
     * */
    public List<VoucherInst> allVoucher(Integer userId, Integer activityId, Integer quantity) {
        BigDecimal orderPrice = getOrderPrice(activityId, quantity);
        List<VoucherInst> voucherInstList = userVoucherService.findCanUseVoucher(userId, "A", orderPrice);
        return voucherInstList;
    }

    /* *
     * 获取最大优惠券
     * @Author        zyj
     * @Date          2018/10/19 17:00
     * @Description
     * */
    public VoucherInstDTO MaxVoucher(Integer userId, Integer activityId, Integer quantity) {
        List<VoucherInst> voucherInstList = allVoucher(userId, activityId, quantity);
        if (voucherInstList != null && voucherInstList.size() > 0) {
            for (int i = 0; i < voucherInstList.size() - 1; i++) {
                for (int j = 0; j < voucherInstList.size() - 1 - i; j++) {
                    if (((voucherInstList.get(i).getMoney()) == null ? new BigDecimal(0) : voucherInstList.get(i).getMoney()).compareTo((voucherInstList.get(j + 1 + i).getMoney()) == null ? new BigDecimal(0) : voucherInstList.get(j + 1 + i).getMoney()) < 0) {
                        Collections.swap(voucherInstList, i, j + 1 + i);
                    }
                }
            }
            VoucherInst voucherInst = voucherInstList.get(0);
            if (!voucherInst.getType().equals("G")) {
                VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
                voucherInstDTO.setMoney(voucherInst.getMoney());
                voucherInstDTO.setVoucherInstId(voucherInst.getId());
                voucherInstDTO.setRule(dictService.findStatusName("voucher_inst", "enable_type", voucherInst.getEnableType()) + voucherInst.getEnableMoney() + "元可用");
                voucherInstDTO.setName(voucherInst.getName());
                voucherInstDTO.setType(voucherInst.getType());
                if (voucherInst.getType().equals("D")) {
                    voucherInstDTO.setDisCount(voucherInst.getDiscount().divide(new BigDecimal(100)).toString());
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                voucherInstDTO.setUsefulTime(formatter.format(voucherInst.getIneffectiveTime()));
                voucherInstDTO.setScope(voucherInst.getScope());
                return voucherInstDTO;
            }
        }
        return null;
    }

    public List<VoucherInstDTO> getAllVoucherDTO(Integer userId, Integer activityId, Integer quantity) {
        List<VoucherInst> voucherInstList = allVoucher(userId, activityId, quantity);
        if (voucherInstList != null && voucherInstList.size() > 0) {
            List<VoucherInstDTO> voucherInstDTOList = new ArrayList<>();
            for (VoucherInst voucherInst : voucherInstList) {
                if (!voucherInst.getType().equals("G")) {
                    VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
                    voucherInstDTO.setMoney(voucherInst.getMoney());
                    voucherInstDTO.setVoucherInstId(voucherInst.getId());
                    voucherInstDTO.setRule(dictService.findStatusName("voucher_inst", "enable_type", voucherInst.getEnableType()) + voucherInst.getEnableMoney() + "元可用");
                    voucherInstDTO.setName(voucherInst.getName());
                    voucherInstDTO.setType(voucherInst.getType());
                    if (voucherInst.getType().equals("D")) {
                        voucherInstDTO.setDisCount(voucherInst.getDiscount().divide(new BigDecimal(100)).toString());
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    voucherInstDTO.setUsefulTime(formatter.format(voucherInst.getIneffectiveTime()));
                    voucherInstDTO.setScope(voucherInst.getScope());
                    voucherInstDTOList.add(voucherInstDTO);
                }
            }
            return voucherInstDTOList;
        }
        return null;
    }


    /* *
     * 生成订单
     * @Author        zyj
     * @Date          2018/10/16 17:21
     * @Description
     * */
    public ActivityDTO addOrder(Member user, Map<String, Object> map) {
        Integer activityId = Integer.valueOf(map.get("activityId").toString());
        String name = map.get("name").toString();
        String phone = map.get("phone").toString();
        Integer quantity = Integer.valueOf(map.get("quantity").toString());
        Activity activity = activityRepository.getOne(activityId);
        //判断是剩余名额是否够报名
        Integer sum = (activityOrderRepository.findSumByActivityAndStatus(activity.getId())) == null ? 0 : (activityOrderRepository.findSumByActivityAndStatus(activity.getId()));
        if (activity.getEndTime().before(new Date())) {
            return null;
        } else {
            if (activity.getQuota() >= sum + quantity) {
                ActivityOrder activityOrder = new ActivityOrder();
                //生成订单号
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String tempUserId = String.format("%02d", user.getId());
                String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
                System.out.println(format);
                activityOrder.setOrderNo(format);
                activityOrder.setActivityId(activityId);
                activityOrder.setPhone(phone);
                activityOrder.setQuantity(quantity);
                activityOrder.setStatus("B");
                activityOrder.setStatusTime(new Date());
                activityOrder.setContacts(name);
                activityOrder.setCreateTime(new Date());
                activityOrder.setUserId(Integer.valueOf(user.getId().toString()));
                activityOrder.setWineryId(Integer.valueOf(user.getWineryId().toString()));
                ActivityOrder activityOrderSave = null;
                if (map.get("voucherId") != null && !map.get("voucherId").equals("")) {
                    Integer voucherId = Integer.valueOf(map.get("voucherId").toString());
                    VoucherInst voucherInst = voucherInstRepository.getOne(voucherId);
                    if (voucherInst.getType().equals("M")) {
                        activityOrder.setTotalPrice(activity.getEnrollPrice().multiply(new BigDecimal(quantity)).subtract(voucherInst.getMoney()));
                    }
                    if (voucherInst.getType().equals("D")) {
                        activityOrder.setTotalPrice(activity.getEnrollPrice().multiply(new BigDecimal(quantity)).multiply((voucherInst.getDiscount()).divide(new BigDecimal(100))));
                    }
                    activityOrderSave = activityOrderRepository.saveAndFlush(activityOrder);
                    ActivityOrderVoucher activityOrderVoucher = new ActivityOrderVoucher();
                    activityOrderVoucher.setActivityOrderId(activityOrderSave.getId());
                    activityOrderVoucher.setVoucherInstId(voucherId);
                    activityOrderVoucherRepository.saveAndFlush(activityOrderVoucher);
                } else {
                    activityOrder.setTotalPrice(activity.getEnrollPrice().multiply(new BigDecimal(quantity)));
                    activityOrderSave = activityOrderRepository.saveAndFlush(activityOrder);
                }


                //添加订单价格记录
                ActivityOrderPrice activityOrderPrice = new ActivityOrderPrice();
                activityOrderPrice.setActivityOrderId(activityOrderSave.getId());
                activityOrderPrice.setFinalPrice(activity.getEnrollPrice());
                activityOrderPrice.setTotalPrice(activityOrderSave.getTotalPrice());
                ActivityOrderPrice activityOrderPriceSave = activityOrderPriceRepository.saveAndFlush(activityOrderPrice);

                //添加订单价格详情记录
                ActivityOrderPriceItem activityOrderPriceItem = new ActivityOrderPriceItem();
                activityOrderPriceItem.setActivityId(activity.getId());
                activityOrderPriceItem.setActivityOrderId(activityOrderSave.getId());
                activityOrderPriceItem.setActivityOrderPriceId(activityOrderPriceSave.getId());
                activityOrderPriceItem.setFinalTotalPrice(activityOrder.getTotalPrice());
                activityOrderPriceItem.setFinalUnitPrice(activity.getEnrollPrice());
                activityOrderPriceItem.setOrigTotalPrice(activity.getEnrollPrice().multiply(new BigDecimal(quantity)));
                activityOrderPriceItem.setOrigUnitPrice(activity.getEnrollPrice());
                activityOrderPriceItemRepository.saveAndFlush(activityOrderPriceItem);

                /* *
                 * 确认订单
                 * @Author        zyj
                 * @Date          2018/10/16 18:29
                 * @Description
                 * */
                ActivityDTO activityDTO = new ActivityDTO();
                UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
                activityDTO.setUserBalance(userBalance.getBalance());
                if (userBalance.getBalance().compareTo(activityOrderSave.getTotalPrice()) >= 0) {
                    activityDTO.setBalancePayStatus("A");
                } else {
                    activityDTO.setBalancePayStatus("P");
                }
                activityDTO.setActivityId(activityId);
                activityDTO.setName(activity.getName());
                activityDTO.setAddress(activity.getFullAddress());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                activityDTO.setBeginTime(formatter.format(activity.getBeginTime()));
                activityDTO.setEndTime(formatter.format(activity.getEndTime()));
                activityDTO.setPrice(activity.getEnrollPrice());
                activityDTO.setContacts(name);
                activityDTO.setPhone(phone);
                ActivityOrderVoucher activityOrderVoucher = activityOrderVoucherRepository.findByActivityOrderId(activityOrderSave.getId());
                if (activityOrderVoucher != null) {
                    VoucherInst voucherInst = voucherInstRepository.getOne(activityOrderVoucher.getVoucherInstId());
                    if (voucherInst != null) {
                        if (voucherInst.getType().equals("M")) {
                            activityDTO.setMinus(voucherInst.getMoney());
                        }
                        if (voucherInst.getType().equals("D")) {
                            activityDTO.setMinus((activity.getEnrollPrice().multiply(new BigDecimal(quantity))).subtract(activityOrderSave.getTotalPrice()));
                        }
                    }
                }
                activityDTO.setQuantity(quantity);
                activityDTO.setTotalPrice(activityOrderSave.getTotalPrice());
                activityDTO.setOrderNo(activityOrder.getOrderNo());
                return activityDTO;
            } else {
                return null;
            }
        }

    }

    //二维码活动图片
    private String checkPath(String path) {
        String newPath = PicturePathUntil.PICTURE_ACTIVITY_PATH;
        int lastIndex = path.lastIndexOf("/");
        String substring = path.substring(0, lastIndex);
        substring += "/" + newPath;
        return substring;
    }

    //创建线下活动
    public void addActivity(AdminUser adminUser, ActivitytDTO activitytDTO) throws ParseException {
        Activity activity = new Activity();
        activity.setWineryId(adminUser.getWineryId());
        activity.setName(activitytDTO.getEventName());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (activitytDTO.getActiviTime() != null && activitytDTO.getActiviTime().size() != 0 && !"".equals(activitytDTO.getActiviTime().get(0).replaceAll("\"", ""))) {
            String str1 = activitytDTO.getActiviTime().get(0).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            String str2 = activitytDTO.getActiviTime().get(1).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            if (str1 != null && !"".equals(str1)) {
                log.info(str1);
                activity.setBeginTime(new Timestamp(sd.parse(str1).getTime()));
            }
            if (str2 != null && !"".equals(str2)) {
                log.info(str2);
                activity.setEndTime(new Timestamp(sd.parse(str2).getTime()));
            }
        }
        if (activitytDTO.getQuota() != null) {
            activity.setQuota(Integer.valueOf(activitytDTO.getQuota()));
        }
        if (activitytDTO.getMoney() != null) {
            activity.setEnrollPrice(new BigDecimal(activitytDTO.getMoney()));
        }
        String add = "";
        if (activitytDTO.getProvince() != null) {
            if (activitytDTO.getProvince().get(0) != null) {
                activity.setProvince(Integer.valueOf(activitytDTO.getProvince().get(0)));
                Area area = areaRepository.getOne(activity.getProvince());
                if (area != null) {
                    add += area.getName();
                }
            }
            if (activitytDTO.getProvince().get(1) != null) {
                activity.setCity(Integer.valueOf(activitytDTO.getProvince().get(1)));
                Area area = areaRepository.getOne(activity.getCity());
                if (area != null) {
                    add += area.getName();
                }
            }
            if (activitytDTO.getProvince().get(2) != null) {
                activity.setCounty(Integer.valueOf(activitytDTO.getProvince().get(2)));
                Area area = areaRepository.getOne(activity.getCounty());
                if (area != null) {
                    add += area.getName();
                }
            }
        }
        activity.setDetailAddress(activitytDTO.getDetailAddress());
        activity.setFullAddress(add + activity.getDetailAddress());
        activity.setCreateUserId(adminUser.getId());
        activity.setStatus("A");
        activity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        activity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        activityRepository.saveAndFlush(activity);
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(adminUser.getWineryId());
        String url = null;
        if (wineryConfigure.getWineryId() == 1) {
            url = PicturePathUntil.QRCode + PicturePathUntil.PICTURE_ACTIVITY_URL_PATH + activity.getId();
        } else {
            url = wineryConfigure.getDomainName() + PicturePathUntil.PICTURE_ACTIVITY_URL_PATH + activity.getId();
        }
        String path = System.getProperty("java.io.tmpdir");
        String newPath = checkPath(path);
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + activity.getId() + ".jpg";
        System.out.println(fileName);
        String qrCode = QRcodeUtil2.createQrCode(url, newPath, fileName);
        System.out.println("/" + PicturePathUntil.PICTURE_ACTIVITY_PATH + qrCode);
        activity.setQrCode("/" + PicturePathUntil.PICTURE_ACTIVITY_PATH + qrCode);
        ActivityContent activityContent = new ActivityContent();
        activityContent.setActivityId(activity.getId());
        if (!"".equals(activitytDTO.getContent())) {
            activityContent.setContent(activitytDTO.getContent());
            activityContentRepository.saveAndFlush(activityContent);
        }
        List<ActivityLogo> activityLogos = new ArrayList<>();
        for (int i = 0; i < activitytDTO.getPicurl().size(); i++) {
            if (!"".equals(activitytDTO.getPicurl().get(i))) {
                ActivityLogo activityLogo = new ActivityLogo();
                activityLogo.setActivityId(activity.getId());
                activityLogo.setLogo(activitytDTO.getPicurl().get(i).substring(activitytDTO.getPicurl().get(i).indexOf("/vimg")));
                activityLogo.setSeq(i + 1);
                activityLogo.setIsDefault("N");
                activityLogos.add(activityLogo);
            }
        }
        activityLogoRepository.saveAll(activityLogos);
        if (activitytDTO.getBanner() != null) {
            ActivityLogo activityLogo = new ActivityLogo();
            activityLogo.setActivityId(activity.getId());
            activityLogo.setLogo(activitytDTO.getBanner().substring(activitytDTO.getBanner().indexOf("/vimg")));
            activityLogo.setSeq(0);
            activityLogo.setIsDefault("Y");
            activityLogoRepository.saveAndFlush(activityLogo);
        }
        List<AcitivityRange> acitivityRanges = new ArrayList<>();
        for (String str : activitytDTO.getLevel()) {
            if (!"".equals(str)) {
                AcitivityRange range = new AcitivityRange();
                range.setActivityId(activity.getId());
                if (str != null) {
                    range.setMemberLevelId(Integer.valueOf(str));
                }
                acitivityRanges.add(range);
            }
        }
        activityRangeRepository.saveAll(acitivityRanges);
        List<AcitivitySendVoucher> acitivitySendVouchers = new ArrayList<>();
        for (Map<String, Integer> map : activitytDTO.getVoucherId()) {
            AcitivitySendVoucher voucher = new AcitivitySendVoucher();
            voucher.setActivityId(activity.getId());
            if (map.get("id") != null) {
                voucher.setVoucherId(map.get("id"));
            }
            acitivitySendVouchers.add(voucher);
        }
        activitySendVoucherRepository.saveAll(acitivitySendVouchers);
    }

    /* *
     * 用户线下活动门票列表
     * @Author        zyj
     * @Date          2018/10/18 15:27
     * @Description
     * */
    public List<ActivityDTO> getActivityTicket(Member user) {
        //只显示可使用得门票
        List<UserActivityTicket> userActivityTicketList = userActivityTicketRepository.findByUserIdAndStatus(Integer.valueOf(user.getId().toString()), "A");
        if (userActivityTicketList != null) {
            List<ActivityDTO> activityDTOList = new ArrayList<>();
            for (UserActivityTicket userActivityTicket : userActivityTicketList) {
                ActivityDTO activityDTO = new ActivityDTO();
                Activity activity = activityRepository.getOne(userActivityTicket.getActivityId());
                if (activity != null) {
                    if (activity.getEndTime().after(new Date())) {
                        activityDTO.setName(activity.getName());
                        activityDTO.setAddress(activity.getFullAddress());
                        activityDTO.setTicketBeginTime(activity.getBeginTime());
                        activityDTO.setTicketEndTime(activity.getEndTime());
                        activityDTO.setQuantity(userActivityTicket.getQuantity());
                        activityDTO.setActivityId(userActivityTicket.getActivityId());
                        ActivityLogo activityLogo = activityLogoService.findByActivityIdAndIsDefault(userActivityTicket.getActivityId(), "Y");
                        if (activityLogo != null) {
                            activityDTO.setLogo((activityLogo.getLogo().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(activityLogo.getLogo())) : activityLogo.getLogo());
                        }
                        activityDTOList.add(activityDTO);
                    }
                }
            }
            return activityDTOList;
        }
        return null;
    }

    public List<ActivityListDTO> activityList(AdminUser adminUser, String search) {
        List<Dict> dicts = CacheUtil.getDicts();
        List<Activity> list = activityRepository.findByWineryIdAndName(adminUser.getWineryId(), search);
        List<ActivityListDTO> activityLists = new ArrayList<>();
        for (Activity activity : list) {
            ActivityListDTO activityList = new ActivityListDTO();
            activityList.setOper(activity.getStatus().equals("P") ? "启用" : "禁用");
            activityList.setId(activity.getId());
            activityList.setName(activity.getName());
            activityList.setQuota(activity.getQuota());
            if (activity.getEnrollPrice() != null) {
                activityList.setMoney(String.valueOf(activity.getEnrollPrice()));
            }
            activityList.setQrCode(PicturePathUntil.SERVER + activity.getQrCode());
            List<ActivityOrder> list1 = activityOrderRepository.findByActivity(activity.getId());
            int count = 0;
            for (ActivityOrder activityOrder : list1) {
                if (activityOrder.getQuantity() != null) {
                    count += activityOrder.getQuantity();
                }
            }
            activityList.setPeopleNum(count);
            for (Dict dict : dicts) {
                if (dict.getTableName().equals("activity") && dict.getColumnName().equals("status") && dict.getStsId().equals(activity.getStatus())) {
                    activityList.setState(dict.getStsWords());
                }
            }
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (activity.getBeginTime() != null) {
                activityList.setBeginTime(sd.format(activity.getBeginTime()));
            }
            if (activity.getEndTime() != null) {
                activityList.setEndTime(sd.format(activity.getEndTime()));
            }
            activityLists.add(activityList);
        }
        return activityLists;
    }

    public Activity findActivityById(Integer id) {
        return activityRepository.getOne(id);
    }

    @Transactional
    public void updateActivity(Activity activity, AdminUser adminUser, ActivitytDTO activitytDTO) throws ParseException {
        activity.setWineryId(adminUser.getWineryId());
        activity.setName(activitytDTO.getEventName());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (activitytDTO.getActiviTime() != null && activitytDTO.getActiviTime().size() != 0 && !"".equals(activitytDTO.getActiviTime().get(0).replaceAll("\"", ""))) {
            String str1 = activitytDTO.getActiviTime().get(0).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            String str2 = activitytDTO.getActiviTime().get(1).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            if (str1 != null && !"".equals(str1)) {
                log.info(str1);
                activity.setBeginTime(new Timestamp(sd.parse(str1).getTime()));
            }
            if (str2 != null && !"".equals(str2)) {
                log.info(str2);
                activity.setEndTime(new Timestamp(sd.parse(str2).getTime()));
            }
        }
        if (activitytDTO.getQuota() != null) {
            activity.setQuota(Integer.valueOf(activitytDTO.getQuota()));
        }
        if (activitytDTO.getMoney() != null) {
            activity.setEnrollPrice(new BigDecimal(activitytDTO.getMoney()));
        }
        if (activitytDTO.getProvince() != null) {
            if (activitytDTO.getProvince().get(0) != null) {
                activity.setProvince(Integer.valueOf(activitytDTO.getProvince().get(0)));
            }
            if (activitytDTO.getProvince().get(1) != null) {
                activity.setCity(Integer.valueOf(activitytDTO.getProvince().get(1)));
            }
            if (activitytDTO.getProvince().get(2) != null) {
                activity.setCounty(Integer.valueOf(activitytDTO.getProvince().get(2)));
            }
        }
        activity.setDetailAddress(activitytDTO.getDetailAddress());
        activity.setStatus("A");
        activity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        activityRepository.saveAndFlush(activity);
        if (!"".equals(activitytDTO.getContent()) && activitytDTO.getContent() != null) {
            activityContentRepository.deleteByActivityId(activity.getId());
            ActivityContent activityContent = activityContentRepository.findByActivityId(activity.getId());
            if (activityContent == null) {
                activityContent = new ActivityContent();
            }
            activityContent.setActivityId(activity.getId());
            activityContent.setContent(activitytDTO.getContent());
            activityContentRepository.saveAndFlush(activityContent);
        }
        if (activitytDTO.getPicurl().size() != 0) {
            activityLogoRepository.deleteByActivityId(activity.getId());
            List<ActivityLogo> activityLogos = new ArrayList<>();
            for (int i = 0; i < activitytDTO.getPicurl().size(); i++) {
                if (!"".equals(activitytDTO.getPicurl().get(i))) {
                    ActivityLogo activityLogo = new ActivityLogo();
                    activityLogo.setActivityId(activity.getId());
                    activityLogo.setLogo(activitytDTO.getPicurl().get(i).substring(activitytDTO.getPicurl().get(i).indexOf("/vimg")));
                    activityLogo.setSeq(i + 1);
                    activityLogo.setIsDefault("N");
                    activityLogos.add(activityLogo);
                }
            }
            activityLogoRepository.saveAll(activityLogos);
        }
        if (activitytDTO.getBanner() != null && !activitytDTO.getBanner().equals("")) {
            ActivityLogo activityLogo = activityLogoRepository.findByActivityIdAndIsDefault(activity.getId(), "Y");
            if (activityLogo == null) {
                activityLogo = new ActivityLogo();
                activityLogo.setActivityId(activity.getId());
                activityLogo.setIsDefault("Y");
                activityLogo.setSeq(0);
            }
            activityLogo.setLogo(activitytDTO.getBanner().substring(activitytDTO.getBanner().indexOf("/vimg")));
            activityLogoRepository.saveAndFlush(activityLogo);
        }
        if (activitytDTO.getLevel() != null) {
            activityRangeRepository.deleteByActivityId(activity.getId());
            List<AcitivityRange> acitivityRanges = new ArrayList<>();
            for (String str : activitytDTO.getLevel()) {
                if (!"".equals(str)) {
                    AcitivityRange range = new AcitivityRange();
                    range.setActivityId(activity.getId());
                    if (str != null) {
                        range.setMemberLevelId(Integer.valueOf(str));
                    }
                    acitivityRanges.add(range);
                }
            }
            activityRangeRepository.saveAll(acitivityRanges);
        }
        if (activitytDTO.getVoucherId() != null) {
            activitySendVoucherRepository.deleteByActivityId(activity.getId());
            List<AcitivitySendVoucher> acitivitySendVouchers = new ArrayList<>();
            for (Map<String, Integer> map : activitytDTO.getVoucherId()) {
                AcitivitySendVoucher voucher = new AcitivitySendVoucher();
                voucher.setActivityId(activity.getId());
                if (map.get("id") != null) {
                    voucher.setVoucherId(map.get("id"));
                }
                acitivitySendVouchers.add(voucher);
            }
            activitySendVoucherRepository.saveAll(acitivitySendVouchers);
        }
    }

    public void deleteActivity(Activity activity) {
        activityRepository.deleteById(activity.getId());
        activityRangeRepository.deleteByActivityId(activity.getId());
        activitySendVoucherRepository.deleteByActivityId(activity.getId());
        activityLogoRepository.deleteByActivityId(activity.getId());
        userActivityTicketRepository.deleteByActivityId(activity.getId());
    }

    public void stopActivity(Activity activity, AdminUser adminUser) {
        activity.setStatus("P");
        activity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        activity.setCreateUserId(adminUser.getId());
        activityRepository.saveAndFlush(activity);
    }

    public List<ActivityOrderListDTO> orderList(Integer activityId, String search) {
        List<ActivityOrder> list = activityOrderRepository.findByActivityAndName(activityId, search);
        List<ActivityOrderListDTO> activityOrderLists = new ArrayList<>();
        List<Dict> dicts = CacheUtil.getDicts();
        for (ActivityOrder order : list) {
            ActivityOrderListDTO activityOrderList = new ActivityOrderListDTO();
            activityOrderList.setId(order.getId());
            OrderPay pay = orderPayRepository.findByOrderId(order.getId(), "A");
            if (pay != null && !pay.getPayType().equals("")) {
                activityOrderList.setPayMethod(dictService.findStatusName("order_pay", "pay_type", pay.getPayType()));
            }
            activityOrderList.setMoney(String.valueOf(order.getTotalPrice()));
            activityOrderList.setPayTime(new Timestamp(order.getStatusTime().getTime()));
            for (Dict dict : dicts) {
                if (dict.getTableName().equals("activity_order") && dict.getColumnName().equals("status") && dict.getStsId().equals(order.getStatus())) {
                    activityOrderList.setStatus(dict.getStsWords());
                }
            }
            /*MemberWechat memberUser = memberWechatRepository.findByUserId(order.getUserId());*/
            activityOrderList.setUserName(order.getContacts());
            Activity activity = activityRepository.getOne(order.getActivityId());
            if (activity != null) {
                activityOrderList.setName(activity.getName());
                activityOrderList.setBeginTime(activity.getBeginTime());
                activityOrderList.setEndTime(activity.getEndTime());
            }
            activityOrderLists.add(activityOrderList);
        }
        return activityOrderLists;
    }

    public ActivitytDTO activityDetail(String id) {
        Activity activity = activityRepository.getOne(Integer.valueOf(id));
        ActivitytDTO activitytDTO = new ActivitytDTO();
        activitytDTO.setId(activity.getId());
        activitytDTO.setEventName(activity.getName());
        List<ActivityLogo> logoList = activityLogoRepository.findByActivityId(Integer.parseInt(id));
        List<String> logo = new ArrayList<>();
        List<Map<String, String>> maps = new ArrayList<>();
        for (ActivityLogo activityLogo : logoList) {
            if (activityLogo != null && activityLogo.getIsDefault().equals("N")) {
                logo.add(PicturePathUntil.SERVER + activityLogo.getLogo());
            }
            if (activityLogo != null && activityLogo.getIsDefault().equals("Y")) {
                activitytDTO.setBanner(PicturePathUntil.SERVER + activityLogo.getLogo());
                Map<String, String> map = new HashMap<>();
                map.put("name", activityLogo.getLogo().substring(activityLogo.getLogo().lastIndexOf("/") + 1));
                map.put("url", PicturePathUntil.SERVER + activityLogo.getLogo());
                maps.add(map);
                activitytDTO.setBannerName(maps);
            }
        }
        activitytDTO.setPicurl(logo);
        ActivityContent content = activityContentRepository.findByActivityId(Integer.parseInt(id));
        if (content != null) {
            activitytDTO.setContent(content.getContent());
        }
        List<Map<String, Integer>> voucher = new ArrayList<>();
        List<AcitivitySendVoucher> list1 = activitySendVoucherRepository.findByActivityId(Integer.valueOf(id));
        List<Map<String, String>> voucherName = new ArrayList<>();
        if (list1 != null && list1.size() != 0) {
            for (AcitivitySendVoucher voucher1 : list1) {
                if (voucher1.getVoucherId() != null) {
                    Voucher inst = voucherRepository.getOne(voucher1.getVoucherId());
                    if (inst != null) {
                        Map<String, Integer> map2 = new HashMap<>();
                        map2.put("id", inst.getId());
                        voucher.add(map2);
                        Map<String, String> map = new HashMap<>();
                        map.put("value", inst.getName());
                        voucherName.add(map);
                    }
                }
            }
        } else {
            Map<String, String> map2 = new HashMap<>();
            map2.put("value", "请选择");
            voucherName.add(map2);
            Map<String, Integer> map = new HashMap<>();
            map.put("id", 0);
            voucher.add(map);
        }
        activitytDTO.setVoucherId(voucher);
        activitytDTO.setVoucherName(voucherName);
        List<String> level = new ArrayList<>();
        List<Integer> levelId = new ArrayList<>();
        List<AcitivityRange> list = activityRangeRepository.findByActivityId(Integer.valueOf(id));
        for (AcitivityRange range : list) {
            if (range.getMemberLevelId() != null) {
                levelId.add(range.getMemberLevelId());
                MemberLevel level1 = memberLevelRepository.getOne(range.getMemberLevelId());
                if (level1 != null) {
                    level.add(String.valueOf(level1.getName()));
                }
            }
        }
        activitytDTO.setLevelId(levelId);
        activitytDTO.setLevel(level);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> time = new ArrayList<>();
        time.add(sd.format(activity.getBeginTime()));
        time.add(sd.format(activity.getEndTime()));
        activitytDTO.setActiviTime(time);
        activitytDTO.setBeginTime(sd.format(activity.getBeginTime()));
        activitytDTO.setEndTime(sd.format(activity.getEndTime()));
        activitytDTO.setQuota(String.valueOf(activity.getQuota()));
        activitytDTO.setMoney(String.valueOf(activity.getEnrollPrice()));
        activitytDTO.setErweima(PicturePathUntil.SERVER + activity.getQrCode());
        List<String> province = new ArrayList<>();
        List<String> provinceName = new ArrayList<>();
        province.add(String.valueOf(activity.getProvince()));
        Area area = areaRepository.getOne(activity.getProvince());
        if (area != null) {
            provinceName.add(area.getName());
        }
        province.add(String.valueOf(activity.getCity()));
        Area area2 = areaRepository.getOne(activity.getCity());
        if (area2 != null) {
            provinceName.add(area2.getName());
        }
        province.add(String.valueOf(activity.getCounty()));
        Area area3 = areaRepository.getOne(activity.getCounty());
        if (area3 != null) {
            provinceName.add(area3.getName());
        }
        activitytDTO.setProvince(province);
        activitytDTO.setProvinceName(provinceName);
        activitytDTO.setDetailAddress(activity.getDetailAddress());
        return activitytDTO;
    }

    /* *
     * 活动订单支付成功后的逻辑
     * @Author        zyj
     * @Date          2018/10/25 9:30
     * @Description
     * */
    public void paySuccess(String orderNo, String type, Map<String, String> map) throws ClientException {
        ActivityOrder activityOrder = activityOrderRepository.findByOrderNo(orderNo);
        Member user = memberRepository.getOne(activityOrder.getUserId());
        OrderPay orderPay = new OrderPay();
        //使用余额支付不够的用微信
        if (type.equals("B")) {
            UserDepositDetail userDepositDetail = new UserDepositDetail();
            UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
            orderPay.setTotalPrice(activityOrder.getTotalPrice().subtract(userBalance.getBalance()));
            userDepositDetail.setBalance(userBalance.getBalance());
            userBalance.setBalance(new BigDecimal(0));
            userBalance.setUpdateTime(new Date());
            userBalanceRepository.saveAndFlush(userBalance);
            userDepositDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            userDepositDetail.setUserId(Integer.valueOf(user.getId().toString()));
            userDepositDetail.setAction("A");
            userDepositDetail.setBalanceType("M");
            userDepositDetail.setLatestBalance(new BigDecimal(0));
            userDepositDetail.setOrderId(activityOrder.getId());
            userDepositDetail.setOrderNo(activityOrder.getOrderNo());
            userDepositDetail.setCreateTime(new Date());
            userDepositDetailRepository.saveAndFlush(userDepositDetail);
        } else {
            orderPay.setTotalPrice(activityOrder.getTotalPrice());
        }
        //添加支付信息

        orderPay.setOrderNo(orderNo);
        orderPay.setOrderId(activityOrder.getId());
        orderPay.setOrderType("A");
        orderPay.setPayType("W");
        orderPay.setUserId(activityOrder.getUserId());
        orderPay.setPlatformOrderNo(map.get("transaction_id"));
        orderPay.setNotifyStatus("A");
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        activityOrder.setStatus("P");
        activityOrder.setType("A");
        activityOrder.setStatusTime(new Date());
        //修改订单状态
        activityOrderRepository.saveAndFlush(activityOrder);
        //将订单使用的券作废
        ActivityOrderVoucher activityOrderVoucher = activityOrderVoucherRepository.findByActivityOrderId(activityOrder.getId());
        if (activityOrderVoucher != null) {
            VoucherInst voucherInst = voucherInstRepository.getOne(activityOrderVoucher.getVoucherInstId());
            voucherInst.setStatus("U");
            voucherInst.setStatusTime(new Date());
            voucherInstRepository.saveAndFlush(voucherInst);
            UserVoucher userVoucher = userVoucherRepository.findByVoucherInstId(voucherInst.getId());
            userVoucher.setUseTime(new Date());
            userVoucherRepository.saveAndFlush(userVoucher);
        }

        //计算积分
        pointRewardRuleService.updateUserPoint(user, activityOrder.getTotalPrice(), "W", activityOrder.getOrderNo(), activityOrder.getId());
        //赠送券
        sendActivityVoucher(user, activityOrder.getActivityId(), activityOrder.getId());

        userActivityTicketService.addUserActivityTicket(user, activityOrder, activityOrder.getActivityId());

        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        //满额赠券
        List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("满额", activityOrder.getWineryId());
        for (MarketActivity newUserActivity : activityList) {
            if (newUserActivity != null) {
                MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                    if (newUserActivity.getSendSetting() != null && newUserActivity.getSendSetting().equals("O")) {
                        if (user != null && marketActivitySpecDetail.getDepositMoney() != null && activityOrder.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                        }
                    } else {
                        if (user != null && marketActivitySpecDetail.getDepositMoney() != null && activityOrder.getTotalPrice().compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                            Integer count = activityOrder.getTotalPrice().divide(marketActivitySpecDetail.getDepositMoney(), 0, BigDecimal.ROUND_HALF_DOWN ).intValue();
                            for (int i = 0; i < count; i++) {
                                marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "M");
                            }
                        }
                    }

                }
            }
        }
        //累计消费增券
        BigDecimal decimal = orderPayRepository.findSumByUserId(activityOrder.getUserId());
        List<MarketActivity> activityListm = marketActivityRepository.findByStatusAndMarketActivityTypeLike("累计", activityOrder.getWineryId());
        for (MarketActivity newUserActivity : activityListm) {
            if (newUserActivity != null) {
                MarketActivitySpecDetail marketActivitySpecDetail = marketActivitySpecDetailRepository.findByMarketActivityIdLimit(newUserActivity.getId());
                if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                    if (user != null && marketActivitySpecDetail.getDepositMoney() != null && decimal.compareTo(marketActivitySpecDetail.getDepositMoney()) >= 0) {
                        marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity, "O");
                    }
                }
            }
        }
    }


    public void balancePay(Member user, String orderNo) {
        ActivityOrder activityOrder = activityOrderRepository.findByOrderNo(orderNo);
        UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        userBalance.setBalance(userBalance.getBalance().subtract(activityOrder.getTotalPrice()));
        userBalance.setUpdateTime(new Date());
        UserBalance userBalanceSave = userBalanceRepository.saveAndFlush(userBalance);
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(orderNo);
        orderPay.setOrderId(activityOrder.getId());
        orderPay.setOrderType("A");
        orderPay.setPayType("D");
        orderPay.setUserId(activityOrder.getUserId());
        orderPay.setTotalPrice(activityOrder.getTotalPrice());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        ActivityOrderVoucher activityOrderVoucher = activityOrderVoucherRepository.findByActivityOrderId(activityOrder.getId());
        if (activityOrderVoucher != null) {
            VoucherInst voucherInst = voucherInstRepository.getOne(activityOrderVoucher.getVoucherInstId());
            voucherInst.setStatus("U");
            voucherInst.setStatusTime(new Date());
            voucherInstRepository.saveAndFlush(voucherInst);
            UserVoucher userVoucher = userVoucherRepository.findByVoucherInstId(voucherInst.getId());
            userVoucher.setUseTime(new Date());
            userVoucherRepository.saveAndFlush(userVoucher);
        }


        UserDepositDetail userDepositDetail = new UserDepositDetail();
        userDepositDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userDepositDetail.setUserId(Integer.valueOf(user.getId().toString()));
        userDepositDetail.setAction("A");
        userDepositDetail.setBalance(activityOrder.getTotalPrice());
        userDepositDetail.setBalanceType("M");
        userDepositDetail.setLatestBalance(userBalanceSave.getBalance());
        userDepositDetail.setOrderId(activityOrder.getId());
        userDepositDetail.setOrderNo(activityOrder.getOrderNo());
        userDepositDetail.setCreateTime(new Date());
        userDepositDetailRepository.saveAndFlush(userDepositDetail);
        sendActivityVoucher(user, activityOrder.getActivityId(), activityOrder.getId());
        activityOrder.setStatus("P");
        activityOrder.setType("A");
        activityOrder.setStatusTime(new Date());
        activityOrderRepository.saveAndFlush(activityOrder);
        pointRewardRuleService.updateUserPoint(user, activityOrder.getTotalPrice(), "W", activityOrder.getOrderNo(), activityOrder.getId());
        userActivityTicketService.addUserActivityTicket(user, activityOrder, activityOrder.getActivityId());
    }

    public void zero(Member user, String orderNo) {
        ActivityOrder activityOrder = activityOrderRepository.findByOrderNo(orderNo);
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(orderNo);
        orderPay.setOrderId(activityOrder.getId());
        orderPay.setOrderType("A");
        orderPay.setPayType("D");
        orderPay.setUserId(activityOrder.getUserId());
        orderPay.setTotalPrice(activityOrder.getTotalPrice());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        ActivityOrderVoucher activityOrderVoucher = activityOrderVoucherRepository.findByActivityOrderId(activityOrder.getId());
        if (activityOrderVoucher != null) {
            VoucherInst voucherInst = voucherInstRepository.getOne(activityOrderVoucher.getVoucherInstId());
            voucherInst.setStatus("U");
            voucherInst.setStatusTime(new Date());
            voucherInstRepository.saveAndFlush(voucherInst);
            UserVoucher userVoucher = userVoucherRepository.findByVoucherInstId(voucherInst.getId());
            userVoucher.setUseTime(new Date());
            userVoucherRepository.saveAndFlush(userVoucher);
        }

        sendActivityVoucher(user, activityOrder.getActivityId(), activityOrder.getId());
        activityOrder.setStatus("P");
        activityOrder.setType("A");
        activityOrder.setStatusTime(new Date());
        activityOrderRepository.saveAndFlush(activityOrder);
        pointRewardRuleService.updateUserPoint(user, activityOrder.getTotalPrice(), "W", activityOrder.getOrderNo(), activityOrder.getId());
        userActivityTicketService.addUserActivityTicket(user, activityOrder, activityOrder.getActivityId());
    }


    /* *
     * 活动订单报名支付成功后赠券
     * @Author        zyj
     * @Date          2018/10/29 15:23
     * @Description
     * */
    public void sendActivityVoucher(Member user, Integer activityId, Integer activityOrderId) {
        List<AcitivitySendVoucher> activitySendVoucherList = activitySendVoucherRepository.findByActivityId(activityId);
        if (activitySendVoucherList != null) {
            for (AcitivitySendVoucher acitivitySendVoucher : activitySendVoucherList) {
                if (acitivitySendVoucher.getVoucherId() != null) {
                    Voucher voucher = voucherRepository.getOne(acitivitySendVoucher.getVoucherId());
                    if (voucher != null) {
                        //流水号
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                        Date date = new Date();
                        Random random = new Random();
                        int end2 = random.nextInt(9);
                        String format = sdf.format(date) + end2 + user.getId();

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

                        VoucherInst voucherInst = new VoucherInst(Integer.valueOf(user.getWineryId().toString()), voucher.getName(), voucher.getId(), format, voucher.getType(), voucher.getScope(), voucher.getCanPresent(), voucher.getMoney(), voucher.getDiscount(), voucher.getExchangeProdId(), voucher.getEnableType(), voucher.getEnableMoeny(), effectiveTime, ineffectiveTime, "A", new Date(), new Date());
                        voucherInst.setComActivityType("A");
                        voucherInst.setComeActivityId(activityId);
                        voucherInst.setOrderType("A");
                        voucherInst.setOrderId(activityOrderId);
                        VoucherInst voucherInstSave = voucherInstRepository.saveAndFlush(voucherInst);
                        UserVoucher userVoucher = new UserVoucher();
                        userVoucher.setUserId(Integer.valueOf(user.getId().toString()));
                        userVoucher.setCreateTime(new Date());
                        userVoucher.setVoucherInstId(voucherInstSave.getId());
                        userVoucher.setSendTime(new Date());
                        userVoucher.setGetTime(new Date());
                        userVoucher.setIneffectiveTime(voucherInstSave.getIneffectiveTime());
                        userVoucherRepository.saveAndFlush(userVoucher);

                    }
                }
            }
        }
    }

    public void openActivity(Activity activity, AdminUser adminUser) {
        activity.setStatus("A");
        activity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        activity.setCreateUserId(adminUser.getId());
        activityRepository.saveAndFlush(activity);
    }

    /* *
     * 线下活动扫码签到
     * @Author        zyj
     * @Date          2018/11/28 11:29
     * @Description
     * */
    public void activityLogin(String appId, String code, Integer activityId) {
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByAppId(appId);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", WeChatConts.grantType);
        params.add("appId", appId);
        params.add("js_code", code);
        params.add("secret", wineryConfigure.getAppSecret());

        String responseBody = MemberService.sendPostRequest(WeChatConts.requestUrl, params);
        JSONObject jsonObject = JSONObject.fromString(responseBody);
        String openId = jsonObject.getString("openid");
        System.out.print(openId);

        Member user = memberRepository.findByOpenId(openId);
        userActivityTicketRepository.updateByActivityIdAndUserId(activityId, Integer.valueOf(user.getId().toString()));
    }

    public Activity checkActivitytName(String eventName, AdminUser adminUser) {
        return activityRepository.findByName(eventName, adminUser.getWineryId());
    }

    public void offlinePay(ActivityOrder activityOrder) {
        OrderPay orderPay = new OrderPay();
        orderPay.setTotalPrice(activityOrder.getTotalPrice());
        orderPay.setOrderNo(activityOrder.getOrderNo());
        orderPay.setOrderId(activityOrder.getId());
        orderPay.setOrderType("A");
        orderPay.setPayType("O");
        orderPay.setUserId(activityOrder.getUserId());
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("W");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
    }


    /* *
     * 编辑状态页面详情
     * @Author        zyj
     * @Date          2018/12/22 11:28
     * @Description
     * */
    public Map<String, Object> getOrderDetail(Integer orderId) {
        ActivityOrder activityOrder = activityOrderRepository.getOne(orderId);
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", activityOrder.getOrderNo());
        String status = dictService.findStatusName("activity_order", "status", activityOrder.getStatus());

        map.put("status", status);
        Map<String, String> mapList = dictService.findMapByTableNameAndColumnName("activity_order", "status");
        map.put("statusList", mapList);
        return map;
    }

    public Boolean updateOrderStatus(ActivityOrder activityOrder, String status) throws ClientException {
        Activity activity = activityRepository.getOne(activityOrder.getActivityId());
        activityOrder.setCreateUserId(activityOrder.getUserId());
        activityOrder.setStatus(status);
        activityOrder.setStatusTime(new Date());
        if (status.equals("U")) {
            Integer sum = (activityOrderRepository.findSumByActivityAndStatus(activity.getId())) == null ? 0 : (activityOrderRepository.findSumByActivityAndStatus(activity.getId()));
            if (sum != null) {
                if (activity.getQuota() <= sum) {
                    return false;
                }
            }
        }
        if (status.equals("P")) {
            Integer sum = (activityOrderRepository.findSumByActivityAndStatus(activity.getId())) == null ? 0 : (activityOrderRepository.findSumByActivityAndStatus(activity.getId()));
            if (sum != null) {
                if (activity.getQuota() <= sum) {
                    return false;
                }
            }
            Member user = memberRepository.getOne(activityOrder.getUserId());
            OrderPay orderPay = null;
            OrderPay orderPaySelect = orderPayRepository.findByOrderId(activityOrder.getId(), "A");
            if (orderPaySelect != null) {
                orderPay = orderPaySelect;
            } else {
                orderPay = new OrderPay();
                orderPay.setOrderNo(activityOrder.getOrderNo());
                orderPay.setTotalPrice(activityOrder.getTotalPrice());
                orderPay.setOrderId(activityOrder.getId());
                orderPay.setOrderType("A");
                orderPay.setPayType("O");
                orderPay.setUserId(activityOrder.getUserId());
                orderPay.setPayStatus("W");
                orderPay.setPayTime(new Date());
            }
            orderPay.setPayStatus("A");
            orderPay.setPayTime(new Date());
            orderPayRepository.saveAndFlush(orderPay);
            activityOrder.setType("A");
            activityOrder.setStatusTime(new Date());
            //将订单使用的券作废
            ActivityOrderVoucher activityOrderVoucher = activityOrderVoucherRepository.findByActivityOrderId(activityOrder.getId());
            if (activityOrderVoucher != null) {
                VoucherInst voucherInst = voucherInstRepository.getOne(activityOrderVoucher.getVoucherInstId());
                voucherInst.setStatus("U");
                voucherInst.setStatusTime(new Date());
                voucherInstRepository.saveAndFlush(voucherInst);
                UserVoucher userVoucher = userVoucherRepository.findByVoucherInstId(voucherInst.getId());
                userVoucher.setUseTime(new Date());
                userVoucherRepository.saveAndFlush(userVoucher);
            }
            activityOrderRepository.saveAndFlush(activityOrder);
            //计算积分
            pointRewardRuleService.updateUserPoint(user, activityOrder.getTotalPrice(), "W", activityOrder.getOrderNo(), activityOrder.getId());
            //赠送券
            sendActivityVoucher(user, activityOrder.getActivityId(), activityOrder.getId());

            userActivityTicketService.addUserActivityTicket(user, activityOrder, activityOrder.getActivityId());
        }
        activityOrderRepository.saveAndFlush(activityOrder);
        return true;
    }

    /* *
     * 是否可以购买门票
     * @Author        zyj
     * @Date          2019/1/3 15:52
     * @Description
     * */
    public Boolean isBuy(Integer activityId, Integer quantity) {
        Activity activity = activityRepository.getOne(activityId);
        Integer sum = (activityOrderRepository.findSumByActivityAndStatus(activity.getId())) == null ? 0 : (activityOrderRepository.findSumByActivityAndStatus(activity.getId()));
        if (activity.getEndTime().before(new Date())) {
            return false;
        } else {
            if (sum != null) {
                if (activity.getQuota() >= sum + quantity) {
                    return true;
                } else {
                    return false;
                }
            }

        }
        return true;
    }
}
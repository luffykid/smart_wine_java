package com.changfa.frame.service.jpa.user;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.core.util.Constant;
import com.changfa.frame.core.util.MD5Util;
import com.changfa.frame.data.dto.saas.AdminDTO;
import com.changfa.frame.data.dto.saas.LevelDTO;
import com.changfa.frame.data.dto.saas.MemberListDTO;
import com.changfa.frame.data.dto.saas.OrderListDTO;
import com.changfa.frame.data.dto.wechat.UserDTO;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.offline.OfflineOrder;
import com.changfa.frame.data.entity.offline.OfflineOrderPrice;
import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.user.ActivationCodeSMS;
import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.entity.common.CacheUtil;
import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.entity.deposit.DepositOrder;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.entity.point.UserPointDetail;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.*;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.activity.ActivityOrderRepository;
import com.changfa.frame.data.repository.deposit.DepositOrderRepository;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderPriceRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderRepository;
import com.changfa.frame.data.repository.order.OrderRepository;
import com.changfa.frame.data.repository.point.UserPointDetailRepository;
import com.changfa.frame.data.repository.point.UserPointRepository;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.user.*;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.service.jpa.PicturePathUntil;
import com.changfa.frame.service.jpa.dict.DictService;
import com.changfa.frame.service.jpa.market.MarketActivityService;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
@Service
public class AdminUserService {

    private static Logger log = LoggerFactory.getLogger(AdminUserService.class);

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private MemberLevelRepository memberLevelRepository;
    @Autowired
    private MemberLevelRightRepository memberLevelRightRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MemberWechatRepository memberWechatRepository;
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserPointRepository userPointRepository;
    @Autowired
    private UserVoucherRepository userVoucherRepository;
    @Autowired
    private UserExperienceRepository userExperienceRepository;
    @Autowired
    private ActivityOrderRepository activityOrderRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private DepositOrderRepository depositOrderRepository;
    @Autowired
    private UserPointDetailRepository userPointDetailRepository;
    @Autowired
    private VoucherInstRepository voucherInstRepository;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private AdminUserLoginRepository adminUserLoginRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private DictService dictService;
    @Autowired
    private ActivationCodeSMSRepository activationCodeRepository;
    @Autowired
    private OfflineOrderRepository offlineOrderRepository;
    @Autowired
    private OfflineOrderPriceRepository offlineOrderPriceRepository;
    @Autowired
    private MarketActivityRepository marketActivityRepository;
    @Autowired
    private MarketActivityService marketActivityService;

    //根据token查saas用户
    public AdminUser findAdminUserByToken(String token) {
//        return adminUserRepository.findAdminUserByToken(token);
        return adminUserRepository.findAdminUserByToken(token);
    }

    //会员等级规则列表
    public List<MemberLevel> memberLevellist(AdminUser adminUser, String search) {
        List<Dict> dicts = CacheUtil.getDicts();
        List<MemberLevel> list = memberLevelRepository.findMemberLevelsByWineryIdAnd(adminUser.getWineryId(), search);
        for (MemberLevel memberLevel : list) {
            for (Dict dict : dicts) {
                if (dict.getTableName().equals("member_level") && dict.getColumnName().equals("status") && dict.getStsId().equals(memberLevel.getStatus())) {
                    memberLevel.setStatusName(dict.getStsWords());
                }
            }
        }
        return list;
    }

    //创建会员等级规则
    public void addMemberLevel(AdminUser adminUser, String name, String upgradeExperience, String money, String point, String descri, String isFreightFree) {
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setWineryId(adminUser.getWineryId());
        memberLevel.setName(name);
        if (upgradeExperience != null) {
            memberLevel.setUpgradeExperience(Integer.valueOf(upgradeExperience));
        }
        memberLevel.setDescri(descri);
        memberLevel.setCreateUserId(adminUser.getId());
        memberLevel.setStatus("A");
        memberLevel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        memberLevel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        memberLevel.setStatusTime(new Timestamp(System.currentTimeMillis()));
        memberLevelRepository.saveAndFlush(memberLevel);
        if (money != null && point != null) {
            if (!"".equals(money) && !"".equals(point)) {
                MemberLevelRight right = new MemberLevelRight();
                right.setMemberLevelId(memberLevel.getId());
                right.setConsumptionAmount(new BigDecimal(money));
                right.setPoint(Integer.valueOf(point));
                right.setIsFreightFree(isFreightFree);
                memberLevelRightRepository.saveAndFlush(right);
            }
        }
    }

    //根据id查会员等级规则
    public MemberLevel findMemberLevel(String id) {
        return memberLevelRepository.getOne(Integer.valueOf(id));
    }

    //编辑会员等级规则
    public void updateMemberLevel(MemberLevel memberLevel, AdminUser adminUser, String name, String upgradeExperience, String money, String point, String descri, String isFreightFree) {
        memberLevel.setWineryId(adminUser.getWineryId());
        memberLevel.setName(name);
        //经验暂时不能修改
        if (upgradeExperience != null) {
            memberLevel.setUpgradeExperience(Integer.valueOf(upgradeExperience));
        }
        memberLevel.setDescri(descri);
        memberLevel.setCreateUserId(adminUser.getId());
        memberLevel.setStatus("A");
        memberLevel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        memberLevel.setStatusTime(new Timestamp(System.currentTimeMillis()));
        memberLevelRepository.saveAndFlush(memberLevel);
        MemberLevelRight right = memberLevelRightRepository.findByMemberLevelId(memberLevel.getId());
        if (right == null) {
            right = new MemberLevelRight();
        }
        right.setMemberLevelId(memberLevel.getId());
        if (money != null) {
            right.setConsumptionAmount(new BigDecimal(money));
        }
        if (point != null) {
            right.setPoint(Integer.valueOf(point));
        }
        right.setIsFreightFree(isFreightFree);
        memberLevelRightRepository.saveAndFlush(right);
    }

    //删除会员等级规则
    public void delMemberLevel(MemberLevel memberLevel, AdminUser adminUser) {
        memberLevelRepository.deleteById(memberLevel.getId());
    }

    public MemberLevel memberLevelDetail(String id) {
        MemberLevel level = memberLevelRepository.getOne(Integer.valueOf(id));
        if (level != null) {
            MemberLevelRight right = memberLevelRightRepository.findByMemberLevelId(level.getId());
            if (right != null) {
                if (right.getConsumptionAmount() != null) {
                    level.setConsumptionAmount(String.valueOf(right.getConsumptionAmount()));
                }
                if (right.getPoint() != null) {
                    level.setPoint(String.valueOf(right.getPoint()));
                }
                if (right.getIsFreightFree() != null) {
                    level.setIsFreightFree(right.getIsFreightFree());
                }
            }
        }
        return level;
    }

    //所有会员等级规则
    public List<MemberLevel> allMemberLeve(AdminUser adminUser) {
        return memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(adminUser.getWineryId(), "A");
    }

    //营销活动图片
    private String checkPath(String path) {
        path = path.replaceAll("\\\\","/");
        String newPath = PicturePathUntil.PICTURE_MARKET_PATH;
        int lastIndex = path.lastIndexOf("/");
        String substring = path.substring(0, lastIndex);
        substring += "/" + newPath;
        return substring;
    }

    public String upload(MultipartFile logo, String type) {
        try {
            String path = System.getProperty("java.io.tmpdir");
            String newPath = "";
            newPath = checkPath(path);
            String file = UUID.randomUUID().toString().substring(0, 10) + logo.getOriginalFilename();//随机名字加文件名字
            File filePath = new File(newPath);
            if (!filePath.exists()) {
                filePath.mkdirs();//没有文件夹创建
            }
            logo.transferTo(new File(filePath, file));
            String fileName = "";
            if (type.equals("M")) {
                fileName = "/" + PicturePathUntil.PICTURE_MARKET_PATH + file;
            }
            return Constant.XINDEQI_ICON_PATH + fileName;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<MemberListDTO> memberllist(AdminUser adminUser, String search ,String memberLevelId) {
        List<MemberListDTO> memberLists = new ArrayList<>();
        List<Member> list;
        if ((search == null || "".equals(search)) && (memberLevelId == null || "".equals(memberLevelId))) {
            list = memberRepository.findByWineryIdOrderByCreateTimeDesc(new BigInteger(String.valueOf(adminUser.getWineryId())));
        } else if ((search != null || !"".equals(search)) && (memberLevelId == null || "".equals(memberLevelId))) {
            list = memberRepository.findByWineryIdAndLikeAndLevelIdIsNull(new BigInteger(String.valueOf(adminUser.getWineryId())), search);
        } else {
            list = memberRepository.findByWineryIdAndLike(adminUser.getWineryId().intValue(), search ,memberLevelId);
        }
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        List<Dict> dicts = CacheUtil.getDicts();
        for (Member user : list) {
            MemberListDTO memberList = new MemberListDTO();
            memberList.setUserId(Integer.valueOf(user.getId().toString()));
            memberList.setName(user.getNickName());
            memberList.setPhone(user.getPhone());
            memberList.setWechat(user.getWechat());
            MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
            if (memberUser != null) {
                memberList.setNickName(EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(memberUser.getNickName())));
                memberList.setGender(memberUser.getSex());
                if (memberUser.getBirthday() != null) {
                    memberList.setBirth(sd.format(memberUser.getBirthday()));
                }
                MemberLevel level = memberLevelRepository.findMemberLevelById(memberUser.getMemberLevel());
                if (level != null) {
                    memberList.setLevelName(level.getName());
                }
            }
            UserBalance balance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
            if (balance != null) {
                memberList.setStorageValue(String.valueOf(balance.getBalance()));
            }
            UserPoint point = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
            if (point != null) {
                memberList.setPoint(String.valueOf(point.getPoint()));
            }
            memberLists.add(memberList);
        }
        return memberLists;
    }


    public MemberListDTO memberlDetail(Integer userId) {
        Member user = memberRepository.getOne(userId);
        SimpleDateFormat sd = new SimpleDateFormat("MM月dd日");
        List<Dict> dicts = CacheUtil.getDicts();
        MemberListDTO memberList = new MemberListDTO();
        memberList.setUserId(Integer.valueOf(user.getId().toString()));
        memberList.setName(user.getNickName());
        memberList.setPhone(user.getPhone());
        memberList.setWechat(user.getWechat());
        memberList.setUserIcon(user.getUserIcon());
        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        if (memberUser != null) {
            memberList.setGender(memberUser.getSex());
            if (memberUser.getIdNo() != null) {
                memberList.setIdNo(memberUser.getIdNo());
            }
            memberList.setNickName(EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(memberUser.getNickName())));
            if (memberUser.getBirthday() != null) {
                memberList.setBirth(sd.format(memberUser.getBirthday()));
            }
            MemberLevel level = memberLevelRepository.findMemberLevelById(memberUser.getMemberLevel());
            if (level != null) {
                memberList.setLevelName(level.getName());
            }
        }
        UserBalance balance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        if (balance != null) {
            memberList.setStorageValue(balance.getBalance().intValue() + "元");
        }
        UserPoint point = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        if (point != null) {
            memberList.setPoint(point.getPoint() + "分");
        }
//        List<UserVoucher> list = userVoucherRepository.findByUserIdOrderByCreateTimeDesc(userId);
//        memberList.setVoucher(list.size()+"个");
        UserExperience experience = userExperienceRepository.findByUserId(userId);
        if (experience != null) {
            memberList.setExperience(String.valueOf(experience));
        }
        return memberList;
    }

    public List<LevelDTO> memberlDetailExperience(Integer userId, AdminUser adminUser) {
        List<LevelDTO> levelDTOS = new ArrayList<>();
        List<MemberLevel> list = memberLevelRepository.findMemberLevelsByWineryIdAnd(adminUser.getWineryId(), "");
        UserExperience experience = userExperienceRepository.findByUserId(userId);
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            LevelDTO levelDTO = new LevelDTO();
            levelDTO.setName(list.get(i).getName());
            levelDTO.setUpgradeExperience(list.get(i).getUpgradeExperience());
            if (experience != null) {
                if (experience.getExperience() >= list.get(i).getUpgradeExperience()) {
                    count = i;
                    levelDTO.setWwc("Y");
                } else {
                    levelDTO.setWwc("N");
                }
            }
            levelDTOS.add(levelDTO);
        }
        if (experience != null) {

            levelDTOS.get(0).setExperience(experience.getExperience());

        } else {

            levelDTOS.get(0).setExperience(0);

        }
        return levelDTOS;
    }

    public List<OrderListDTO> memberlDetailOrder(AdminUser adminUser, Integer userId) {
        List<OrderListDTO> orderLists = new ArrayList<>();
        List<Order> list = orderRepository.findByUserIdAndStatus(userId);
        for (Order order : list) {
            MemberWechat memberUser = memberWechatRepository.findByMbrId(userId);
            OrderListDTO orderList = new OrderListDTO();
            if (order.getCreateUserId() != null) {
                AdminUser createUser = adminUserRepository.getOne(order.getCreateUserId());
                orderList.setCreateName(createUser.getUserName());
            } else {
                orderList.setCreateName(memberUser.getNickName());
            }
            orderList.setFinalPrice(String.valueOf(order.getTotalPrice()));
            orderList.setTotalPrice(String.valueOf(order.getTotalPrice()));
            orderList.setOrderNo(order.getOrderNo());
            orderList.setTime(order.getCreateTime());
            orderList.setType("消费");
            orderLists.add(orderList);
        }
        List<OfflineOrder> offlineOrderList = offlineOrderRepository.findByUserIdAndStatusOrderByCreateTimeDesc(userId, "P");
        for (OfflineOrder offlineOrder : offlineOrderList) {
            MemberWechat memberUser = memberWechatRepository.findByMbrId(userId);
            OrderListDTO orderList = new OrderListDTO();
            if (offlineOrder.getCreateUserId() != null) {
                AdminUser createUser = adminUserRepository.getOne(offlineOrder.getCreateUserId());
                orderList.setCreateName(createUser.getUserName());
            } else {
                orderList.setCreateName(memberUser.getNickName());
            }
            OfflineOrderPrice offlineOrderPrice = offlineOrderPriceRepository.findByOfflineOrderId(offlineOrder.getId());
            orderList.setFinalPrice(String.valueOf(offlineOrder.getTotalPrice()));
            orderList.setTotalPrice(String.valueOf(offlineOrderPrice.getTotalPrice()));
            orderList.setOrderNo(offlineOrder.getOrderNo());
            orderList.setTime(offlineOrder.getCreateTime());
            orderList.setType("消费");
            if (offlineOrder.getConsumeType() != null) {
                if (offlineOrder.getConsumeType().equals("1")) {
                    orderList.setConsumeType("菜品");
                } else if (offlineOrder.getConsumeType().equals("2")) {
                    orderList.setConsumeType("酒品");
                }
            }
            orderList.setDescri(offlineOrder.getDescri());
            orderLists.add(orderList);
        }
        List<ActivityOrder> activityOrderList = activityOrderRepository.findByUserIdAndStatus(userId, "P");
        for (ActivityOrder activityOrder : activityOrderList) {
            MemberWechat memberUser = memberWechatRepository.findByMbrId(userId);
            OrderListDTO orderList = new OrderListDTO();
            if (activityOrder.getCreateUserId() != null) {
                AdminUser createUser = adminUserRepository.getOne(activityOrder.getCreateUserId());
                if (createUser!=null) {
                    orderList.setCreateName(createUser.getUserName());
                }
            } else {
                orderList.setCreateName(memberUser.getNickName());
            }
            orderList.setFinalPrice(String.valueOf(activityOrder.getTotalPrice()));
            orderList.setTotalPrice(String.valueOf(activityOrder.getTotalPrice()));
            orderList.setOrderNo(activityOrder.getOrderNo());
            orderList.setTime(activityOrder.getCreateTime());
            orderList.setType("消费");
            orderLists.add(orderList);
        }
        for (int i = 0; i < orderLists.size() - 1; i++) {
            for (int j = 0; j < orderLists.size() - 1 - i; j++) {
                if (orderLists.get(i).getTime().compareTo(orderLists.get(j+1+i).getTime()) < 0) {
                    Collections.swap(orderLists, i, j + 1+i);
                }
            }
        }
        return orderLists;
    }

    public MemberLevel findMemberLevelByUpgradeExperience(String upgradeExperience, AdminUser adminUser) {
        return memberLevelRepository.findMemberLevelByUpgradeExperience(Integer.valueOf(upgradeExperience), adminUser.getWineryId());
    }

    public AdminUser findAdminUserByPhone(String phone, Integer wineryId) {
        return adminUserRepository.findAdminUserByPhone(phone, wineryId);
    }

    public AdminUser findAdminUserByPhone(String phone) {
        return adminUserRepository.findAdminUserByPhone(phone);
    }

    public AdminUser findAdminUserByuserName(String userName, AdminUser adminUser) {
        return adminUserRepository.findAdminUserByUserName(userName, adminUser.getWineryId());
    }

    public void addUser(AdminUser adminUserByToken, String userName, String phone, String pwd, List<String> roleId) {
        AdminUser adminUser = new AdminUser();
        adminUser.setWineryId(adminUserByToken.getWineryId());
        adminUser.setToken(UUID.randomUUID() + "" + System.currentTimeMillis());
        adminUser.setUserName(userName);
        adminUser.setName(userName);
        adminUser.setPhone(phone);
        adminUser.setPassword(MD5Util.MD5Encode(pwd, "utf-8"));
        adminUser.setStatus("A");
        adminUser.setStatusTime(new Timestamp(System.currentTimeMillis()));
        adminUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        adminUserRepository.saveAndFlush(adminUser);
        if (roleId != null && roleId.size() != 0 && !"".equals(roleId.get(0).replaceAll("\"", ""))) {
            for (String str : roleId) {
                str = str.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.valueOf(str));
                userRole.setUserId(adminUser.getId());
                userRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userRoleRepository.saveAndFlush(userRole);
            }
        }
    }

    public AdminUser findAdminUserById(Integer userId) {
        return adminUserRepository.getOne(userId);
    }

    public void updateUser(AdminUser adminUserByToken, String userName, String phone, List<String> roleId, AdminUser adminUser) {
        adminUser.setUserName(userName);
        adminUser.setName(userName);
        adminUser.setPhone(phone);
        adminUser.setStatusTime(new Timestamp(System.currentTimeMillis()));
        adminUser.setWineryId(adminUserByToken.getWineryId());
        adminUserRepository.saveAndFlush(adminUser);
        if (roleId != null && roleId.size() != 0 && !"".equals(roleId.get(0).replaceAll("\"", ""))) {
            userRoleRepository.deleteByUserId(adminUser.getId());
            for (String str : roleId) {
                str = str.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.valueOf(str));
                userRole.setUserId(adminUser.getId());
                userRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userRoleRepository.saveAndFlush(userRole);
            }
        }
    }

    public AdminDTO detailUser(AdminUser adminUser) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUserName(adminUser.getUserName());
        adminDTO.setPhone(adminUser.getPhone());
        adminDTO.setPwd(adminUser.getPassword());
        adminDTO.setStatus(adminUser.getStatus());
        List<Integer> integers = new ArrayList<>();
        List<UserRole> byUserId = userRoleRepository.findByUserId(adminUser.getId());

        for (UserRole userRole : byUserId) {
            integers.add(userRole.getRoleId());
        }
        adminDTO.setRoleId(integers);
        return adminDTO;
    }

    public List<Role> roleList(AdminUser adminUser) {
        return roleRepository.findByWineryId(adminUser.getWineryId());
    }

    public List<AdminDTO> adminList(AdminUser adminUserByToken, String search) {
        List<AdminDTO> adminDTOS = new ArrayList<>();
        List<AdminUser> users = adminUserRepository.findAdminUserByWId(adminUserByToken.getWineryId(), search);
        Integer counta = 0;
        for (AdminUser adminUser : users) {
            counta++;
            AdminDTO adminDTO = new AdminDTO();
            adminDTO.setIndex(counta);
            adminDTO.setUserId(String.valueOf(adminUser.getId()));
            adminDTO.setPwd(adminUser.getPassword());
            adminDTO.setPhone(adminUser.getPhone());
            adminDTO.setUserName(adminUser.getUserName());
            String roleName = "";
            List<UserRole> list = userRoleRepository.findByUserId(adminUser.getId());
            int count = 0;
            for (UserRole userRole : list) {
                Role role = roleRepository.getOne(userRole.getRoleId());
                if (role != null) {
                    if (count == 0) {
                        roleName += role.getName();
                    } else {
                        roleName += "," + role.getName();
                    }
                }
                count++;
            }
            adminDTO.setRoleName(roleName);
            adminDTOS.add(adminDTO);
        }
        return adminDTOS;
    }

    public void delUser(AdminUser adminUser) {
        adminUserRepository.deleteById(adminUser.getId());
    }

    public List<OrderListDTO> depositOrder(Integer userId) {
        List<OrderListDTO> orderLists = new ArrayList<>();
        List<DepositOrder> list = depositOrderRepository.findByUserId(userId);
        for (DepositOrder order : list) {
            Member user = memberRepository.getOne(userId);
            MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
            OrderListDTO orderList = new OrderListDTO();
            if (order.getCreateUserId() != null) {
                AdminUser createUser = adminUserRepository.getOne(order.getCreateUserId());
                orderList.setCreateName(createUser.getUserName());
            } else {
                orderList.setCreateName(memberUser.getNickName());
            }

            if (user != null) {
                orderList.setUserName(user.getNickName());
            }
            orderList.setFinalPrice(order.getTotalPrice()==null?String.valueOf(order.getFinalPrice()):String.valueOf(order.getTotalPrice()));
            orderList.setTotalPrice(order.getFinalPrice()==null?String.valueOf(order.getTotalPrice()):String.valueOf(order.getFinalPrice()));
            orderList.setRewardMoney(order.getRewardMoney()==null?String.valueOf(order.getRewardMoney()):String.valueOf(order.getRewardMoney()));
            orderList.setOrderNo(order.getOrderNo());
            orderList.setTime(new Timestamp(order.getCreateTime().getTime()));
            orderList.setType("储值");
            orderLists.add(orderList);
        }
        return orderLists;
    }

    public List<OrderListDTO> userPointDetail(Integer userId) {
        List<OrderListDTO> orderLists = new ArrayList<>();
        List<UserPointDetail> list = userPointDetailRepository.findByUserIdOrderByCreateTimeDesc(userId);
        for (UserPointDetail order : list) {
            if (order.getPoint()>0) {
                OrderListDTO orderList = new OrderListDTO();
                Member user = memberRepository.getOne(userId);
                MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                orderList.setCreateName(memberUser.getNickName());
                if (user != null) {
                    orderList.setUserName(user.getNickName());
                }
                orderList.setCreateName(memberUser.getNickName());
                if (order.getPointType().equals("A")) {
                    orderList.setFinalPrice("+"+String.valueOf(order.getPoint()));
                }else{
                    orderList.setFinalPrice("-"+String.valueOf(order.getPoint()));
                }
                orderList.setTotalPrice(String.valueOf(order.getLatestPoint()));
                orderList.setOrderNo(order.getOrderNo());
                orderList.setTime(new Timestamp(order.getCreateTime().getTime()));
                orderList.setType(dictService.findStatusName("user_point_detail", "action", order.getAction()));
                orderLists.add(orderList);
            }
        }
        return orderLists;
    }

    public Integer voucherInstCount(Integer userId) {
        return userVoucherRepository.findEffective(userId).size();//未使用
    }

    public List<OrderListDTO> voucherInst(Integer userId) {
        List<OrderListDTO> orderLists = new ArrayList<>();
        List<UserVoucher> list = userVoucherRepository.findUseVoucher(userId);//已经使用券
        for (UserVoucher order : list) {
            String status = userVoucherRepository.findOrderByUseVoucher(order.getId());
            if (status != null) {
                if (status.equals("F") || status.equals("H") || status.equals("R") || status.equals("S")) {
                    VoucherInst inst = voucherInstRepository.getOne(order.getVoucherInstId());
                    if (inst != null) {
                        OrderListDTO orderList = new OrderListDTO();
                        Member user = memberRepository.getOne(userId);
                        if (user != null) {
                            orderList.setUserName(user.getNickName());
                        }
                        if (inst.getType().equals("M")) {
                            orderList.setFinalPrice(String.valueOf(inst.getMoney()));
                        } else if (inst.getType().equals("D")) {
                            orderList.setFinalPrice(String.valueOf(inst.getDiscount().doubleValue() / 10) + "折");
                        } else if (inst.getType().equals("G")) {
                            Prod one = prodRepository.getOne(inst.getExchangeProdId());
                            if (one != null) {
                                orderList.setFinalPrice(one.getName());
                            }
                        }
                        orderList.setTotalPrice(dictService.findStatusName("voucher", "scope", inst.getScope()));
                        orderList.setOrderNo(inst.getVoucharNo());
                        orderList.setTime(new Timestamp(order.getUseTime().getTime()));
                        orderList.setType("消费");
                        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                        orderList.setCreateName(memberUser.getNickName());
                        orderLists.add(orderList);
                    }
                }
            }
        }
        return orderLists;
    }

    public void adminUserLogin(AdminUser adminUser) {
        AdminUserLogin adminUserLogin = new AdminUserLogin();
        adminUserLogin.setWineryId(adminUser.getWineryId());
        adminUserLogin.setUserId(adminUser.getId());
        adminUserLogin.setCreateTime(new Date());
        adminUserLoginRepository.save(adminUserLogin);
    }

    /*public List<Map<String, Object>> returnMean(AdminUser adminUser) {
        Object[] roleList = userRoleRepository.findRoleIdList(adminUser.getId());
        if (roleList != null && roleList.length > 0) {
            List<Map<String, Object>> mapList = new ArrayList<>();
            List<Integer> menuIdList = roleMenuRepository.findMenuIdList(roleList);
            List<Menu> menuList
            for (Integer menuId : menuIdList) {
                Menu menu = menuRepository.findOne(menuId);
                if (menu != null && menu.getLevel().equals("1") && menu.getStatus().equals("A")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", menu.getName());
                    map.put("url", menu.getUrl());
                    map.put("img", menu.getIcon());
                    List<Menu> childMenuList = menuRepository.findByParentMenuId(menu.getId());
                    List<Map<String, Object>> childMenuMap = new ArrayList<>();
                    if (childMenuList != null && childMenuList.size() > 0) {
                        for (Menu childMenu : childMenuList) {
                            if (childMenu.getStatus().equals("A")) {
                                Map<String, Object> mapChild = new HashMap<>();
                                mapChild.put("label", childMenu.getName());
                                mapChild.put("url", childMenu.getUrl());
                                mapChild.put("img", childMenu.getIcon());
                                childMenuMap.add(mapChild);
                            }
                        }
                    }
                    if (childMenuMap != null && childMenuMap.size() != 0) {
                        map.put("children", childMenuMap);
                    }
                    mapList.add(map);
                }
            }
            return mapList;
        } else {
            return null;
        }
    }*/

    public List<Map<String, Object>> returnMean(AdminUser adminUser) {
        Object[] roleList = userRoleRepository.findRoleIdList(adminUser.getId());
        if (roleList != null && roleList.length > 0) {
            List<Map<String, Object>> mapList = new ArrayList<>();
            List<Integer> menuIdList = roleMenuRepository.findMenuIdList(roleList);


            List<Menu> parentMenuList = menuRepository.findAllById(menuIdList);

            List<Menu> allChildList = menuRepository.findByParentMenuIdIn(menuIdList);

            for (Menu menu : parentMenuList) {
//                Menu menu = menuRepository.findAll(menuId);

                if (menu != null && menu.getLevel().equals("1") && menu.getStatus().equals("A")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", menu.getName());
                    map.put("url", menu.getUrl());
                    map.put("img", menu.getIcon());


                    List<Menu> childMenuList = new ArrayList<Menu>();

                    if (allChildList != null && allChildList.size() >0) {
                        for (Menu childMenu : allChildList) {
                            if (childMenu.getParentMenuId().equals(menu.getId())) {
                                childMenuList.add(childMenu);
                            }
                        }
                    }

//                    List<Menu> childMenuList = menuRepository.findByParentMenuId(menu.getId());

                    List<Map<String, Object>> childMenuMap = new ArrayList<>();
                    if (childMenuList != null && childMenuList.size() > 0) {
                        for (Menu childMenu : childMenuList) {
                            if (childMenu.getStatus().equals("A")) {
                                Map<String, Object> mapChild = new HashMap<>();
                                mapChild.put("label", childMenu.getName());
                                mapChild.put("url", childMenu.getUrl());
                                mapChild.put("img", childMenu.getIcon());
                                childMenuMap.add(mapChild);
                            }
                        }
                    }

                    if (childMenuMap != null && childMenuMap.size() != 0) {
                        map.put("children", childMenuMap);
                    }
                    mapList.add(map);
                }
            }
            return mapList;
        } else {
            return null;
        }
    }

    public MemberLevel findMemberLevelByName(String name, AdminUser adminUser) {
        return memberLevelRepository.findMemberLevelByName(name, adminUser.getWineryId());
    }

    public List<MemberWechat> checkMemberLevel(Integer integer, AdminUser adminUser) {
        List<Integer> integers = new ArrayList<>();
        integers.add(integer);
        return memberWechatRepository.findByMemberLevelId(integers, adminUser.getWineryId().longValue());
    }

    public void updatePwd(AdminUser adminUser, String newPwd) {
        adminUser.setPassword(newPwd);
        adminUser.setStatusTime(new Date());
        adminUserRepository.save(adminUser);
    }


    public String addNewUser(AdminUser adminUser, String name, String phone, String code, String sex, String idNo, String birthday) throws ParseException {
        ActivationCodeSMS activationCode = activationCodeRepository.findByPhoneAndType(phone, "C");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (activationCode == null || !code.equals(activationCode.getCode())) {
            return "验证码错误，请重新输入";
        } else {
            Date afterDate = new Date(activationCode.getCreateTime().getTime() + 300000);
            if (new Date().compareTo(afterDate) > 0) {
                return "验证码失效";
            }
        }
        Member firstUser = new Member();
        MemberWechat memberUser = new MemberWechat();
        memberUser.setNickName(name);
        if (birthday != null && !birthday.equals("")) {
            memberUser.setBirthday(format.parse(birthday));
        }
        if (idNo != null && !idNo.equals("")) {
            memberUser.setIdNo(idNo);
        }
        memberUser.setSex(sex);
        String token = UUID.randomUUID() + "" + System.currentTimeMillis();
        firstUser.setToken(token);
        firstUser.setStatus("A");
        firstUser.setNickName(name);
        firstUser.setWineryId(adminUser.getWineryId().longValue());
        firstUser.setStatusTime(new Date());
        firstUser.setCreateTime(new Date());
        firstUser.setPhone(phone);
        Member userSave = memberRepository.saveAndFlush(firstUser);
        memberUser.setMbrId(Integer.valueOf(userSave.getId().toString()));
        List<MemberLevel> memberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(adminUser.getWineryId(), "A");
        memberUser.setMemberLevel(memberLevelList.get(0).getId());
        MemberWechat memberUserSave = memberWechatRepository.saveAndFlush(memberUser);
        //首次登录添加积分记录
        UserPoint userPoint = new UserPoint();
        userPoint.setPoint(0);
        userPoint.setUserId(Integer.valueOf(userSave.getId().toString()));
        userPoint.setWineryId(adminUser.getWineryId());
        userPoint.setUpdateTime(new Date());
        userPointRepository.save(userPoint);
        //添加经验值
        UserExperience userExperience = new UserExperience();
        userExperience.setUserId(Integer.valueOf(userSave.getId().toString()));
        userExperience.setExperience(0);
        userExperience.setUpdateTime(new Date());
        userExperienceRepository.saveAndFlush(userExperience);
        //储值
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(Integer.valueOf(userSave.getId().toString()));
        userBalance.setBalance(new BigDecimal(0));
        userBalance.setDonationAmount(new BigDecimal(0));
        userBalance.setWineryId(adminUser.getWineryId());
        userBalance.setUpdateTime(new Date());
        userBalanceRepository.saveAndFlush(userBalance);
        UserDTO userDTO = new UserDTO();
        userDTO.setToken(userSave.getToken());
        userDTO.setWineryId(Integer.valueOf(userSave.getWineryId().toString()));
        activationCode.setStatus("U");
        activationCodeRepository.saveAndFlush(activationCode);
        log.info("营销活动新会员赠券");
        List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("新会员", Constant.wineryId);
        if (activityList != null && activityList.size() > 0) {
            for (MarketActivity newUserActivity : activityList) {
                if (newUserActivity != null) {
                    if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                        try {
                            marketActivityService.birthdaySendVoucher(memberUserSave, userSave, newUserActivity,"O");
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        //添加用户登录日志
        return "添加成功";
    }




    /**
     * 运营端接口**********************************************************************************
     *
     */

    //根据token查运营端用户
    public AdminUser findAdminUserByToken2(String token) {
        return adminUserRepository.findAdminUserByToken2(token);
    }

    //运营端登录校验
    public AdminUser findAdminUserByPhone2(String phone) {
        return adminUserRepository.findAdminUserByPhone2(phone);
    }


    //运营端 用户管理列表
    public List<AdminDTO> adminList2(String search) {
        List<AdminDTO> adminDTOS = new ArrayList<>();
        List<AdminUser> users = adminUserRepository.findAdminUserByWId2(search);
        Integer counta = 0;
        for (AdminUser adminUser : users) {
            counta++;
            AdminDTO adminDTO = new AdminDTO();
            adminDTO.setIndex(counta);
            adminDTO.setUserId(String.valueOf(adminUser.getId()));
            adminDTO.setPwd(adminUser.getPassword());
            adminDTO.setPhone(adminUser.getPhone());
            adminDTO.setUserName(adminUser.getUserName());
            String roleName = "";
            List<UserRole> list = userRoleRepository.findByUserId(adminUser.getId());
            int count = 0;
            for (UserRole userRole : list) {
                Role role = roleRepository.getOne(userRole.getRoleId());
                if (role != null) {
                    if (count == 0) {
                        roleName += role.getName();
                    } else {
                        roleName += "," + role.getName();
                    }
                }
                count++;
            }
            adminDTO.setRoleName(roleName);
            adminDTOS.add(adminDTO);
        }
        return adminDTOS;
    }



}

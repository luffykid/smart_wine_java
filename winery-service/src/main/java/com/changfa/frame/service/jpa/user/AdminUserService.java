package com.changfa.frame.service.jpa.user;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.AdminUserLogin;
import com.changfa.frame.data.entity.user.Menu;
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
import com.changfa.frame.service.jpa.dict.DictService;
import com.changfa.frame.service.jpa.market.MarketActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户登录service
 * Created by Administrator on 2018/10/11 0011.
 */
@Service("adminUserService")
public class AdminUserService {
    private static Logger log = LoggerFactory.getLogger(AdminUserService.class);
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private AdminUserLoginRepository adminUserLoginRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private MenuRepository menuRepository;


    /**
     *
     * @param phone
     * @return
     */
    public AdminUser findAdminUserByPhone(String phone) {
        return adminUserRepository.findAdminUserByPhone(phone);
    }


    /**
     *
     * @param adminUser
     */
    public void adminUserLogin(AdminUser adminUser) {
        AdminUserLogin adminUserLogin = new AdminUserLogin();
        adminUserLogin.setWineryId(adminUser.getWineryId().intValue());
        adminUserLogin.setUserId(adminUser.getId().intValue());
        adminUserLogin.setCreateTime(new Date());
        adminUserLoginRepository.save(adminUserLogin);
    }


    /**
     *
     * @param adminUser
     * @return
     */
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


}

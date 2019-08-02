package com.changfa.frame.service.winery;

import com.changfa.frame.core.util.MD5Util;
import com.changfa.frame.data.dto.operate.WineryDTO;
import com.changfa.frame.data.dto.operate.WineryDetailDTO;
import com.changfa.frame.data.dto.operate.WineryListDTO;
import com.changfa.frame.data.dto.saas.AssembleCommodityDTO;
import com.changfa.frame.data.entity.assemble.AssembleCommodity;
import com.changfa.frame.data.entity.bargaining.BargainingCommodity;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.prod.ProdCategory;
import com.changfa.frame.data.entity.theme.Theme;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Role;
import com.changfa.frame.data.entity.user.UserRole;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.user.AdminUserRepository;
import com.changfa.frame.data.repository.user.RoleRepository;
import com.changfa.frame.data.repository.user.UserRoleRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.data.repository.winery.WineryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2018/11/9.
 */
@Service
public class WineryService {

    @Autowired
    private WineryRepository wineryRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;
    @Autowired
    private RoleRepository roleRepository;

    //根据酒庄名称获取酒庄信息
    public Winery getWineryByName(String wineryName){
        Winery winery  = wineryRepository.findByName(wineryName);
        return  winery;
    }


    //添加酒庄
    public Winery addWinery( WineryDTO wineryDTO) {
        Winery winery = new Winery();
        winery.setName(wineryDTO.getWineryName());
        winery.setAddress(wineryDTO.getAddress());
        winery.setStatus("A"); //A：正常，P：失效
        winery.setCreateTime(new Date());
        winery.setStatusTime(winery.getCreateTime());
        winery.setIsDelete(1);
        wineryRepository.saveAndFlush(winery);
        return winery;
    }

    public AdminUser findAdminUserByuserName(String userName, Integer wineryId) {
        return adminUserRepository.findAdminUserByUserName(userName, wineryId);
    }

    //添加超级管理员用户
    public AdminUser addSuperAdmin( Winery winery, String userName, String phone, String pwd, List<String> roleId) {
        AdminUser adminUser = new AdminUser();
        adminUser.setWineryId(winery.getId());
        adminUser.setToken(UUID.randomUUID() + "" + System.currentTimeMillis());
        adminUser.setUserName(userName);
        adminUser.setName(userName);
        adminUser.setPhone(phone);
        adminUser.setPassword(MD5Util.MD5Encode(pwd, "utf-8"));
        adminUser.setStatus("A");
        adminUser.setStatusTime(new Timestamp(System.currentTimeMillis()));
        adminUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        adminUser.setIsSuper("Y");  //是否为超级管理员  Y是，N不是，operate运营端账号就一个
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
        return adminUser;
    }


    //添加绑定小程序信息
    public void addWineryConfigure( WineryDTO wineryDTO, AdminUser adminUser) {
        WineryConfigure wineryConfigure = new WineryConfigure();
        wineryConfigure.setWineryId(adminUser.getWineryId());
        wineryConfigure.setAppId(wineryDTO.getAppId());
        wineryConfigure.setAppSecret(wineryDTO.getAppSecret());
        wineryConfigure.setWxPayId(wineryDTO.getWxPayId());
        wineryConfigure.setWxPayKey(wineryDTO.getWxPayKey());
        wineryConfigure.setDomainName(wineryDTO.getDomainName());
        wineryConfigure.setQrCodeUrl(wineryDTO.getQrCodeUrl());
        wineryConfigure.setCreateTime(new Timestamp(System.currentTimeMillis()));
        wineryConfigure.setAppName(wineryDTO.getAppName());
        wineryConfigureRepository.saveAndFlush(wineryConfigure);
    }


    public List<WineryListDTO> wineryList(String name) {
        List<Winery> wineryList = wineryRepository.findAllLikeName(name);
        List<WineryListDTO>  wineryListDTOList = new ArrayList<>();
        for (Winery winery : wineryList) {
            WineryListDTO wineryListDTO = new WineryListDTO();
            wineryListDTO.setId(winery.getId());
            wineryListDTO.setWineryName(winery.getName());
            AdminUser adminUser = adminUserRepository.findAdminUserByWineryId(winery.getId());
            if(null != adminUser){
                wineryListDTO.setUserName(adminUser.getUserName());
            }
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(winery.getId());
            if(null != wineryConfigure){
                wineryListDTO.setAppName(wineryConfigure.getAppName());
        }
            int workerNum = adminUserRepository.countAdminUsersByWineryIdAndStatus(winery.getId(),"A");
            wineryListDTO.setWorkerNum(workerNum);
            wineryListDTOList.add(wineryListDTO);
        }
        return wineryListDTOList;
    }



    /**
     * 酒庄详情
     *
     * @param wineryId     酒庄id
     * @return
     */
    public WineryDetailDTO wineryDetail(Integer wineryId) {
        WineryDetailDTO wineryDetailDTO = new WineryDetailDTO();
        Winery winery = wineryRepository.getOne(wineryId);
        if (winery != null) {
            wineryDetailDTO.setId(winery.getId());
            wineryDetailDTO.setWineryName(winery.getName());
            wineryDetailDTO.setAddress(winery.getAddress());
            AdminUser adminUser = adminUserRepository.findAdminUserByWineryId(winery.getId());
            if(null != adminUser){
                wineryDetailDTO.setUserName(adminUser.getUserName());
                wineryDetailDTO.setPhone(adminUser.getPhone());
                //返回所有可用角色
                List<Role> roleAllList = roleRepository.findByWineryId(adminUser.getWineryId());
                wineryDetailDTO.setRoleAllList(roleAllList);
                //返回该用户选定的角色
                List<Role> roleEdList = roleRepository.findRoleList(winery.getId(),adminUser.getId());
                wineryDetailDTO.setRoleEdList(roleEdList);
            }
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(winery.getId());
            if(null != wineryConfigure){
                wineryDetailDTO.setAppId(wineryConfigure.getAppId());
                wineryDetailDTO.setAppSecret(wineryConfigure.getAppSecret());
                wineryDetailDTO.setWxPayId(wineryConfigure.getWxPayId());
                wineryDetailDTO.setWxPayKey(wineryConfigure.getWxPayKey());
                wineryDetailDTO.setDomainName(wineryConfigure.getDomainName());
                wineryDetailDTO.setQrCodeUrl(wineryConfigure.getQrCodeUrl());
                wineryDetailDTO.setAppName(wineryConfigure.getAppName());
            }
        }
        return wineryDetailDTO;
    }




    //获取酒庄的超级管理员用户
    public AdminUser findAdminUserByWineryId(Integer wineryId){
        AdminUser adminUser = adminUserRepository.findAdminUserByWineryId(wineryId);
        return  adminUser;
    }


    //修改酒庄
    public Winery updateWinery( WineryDTO wineryDTO, Winery winery) {
        winery.setName(wineryDTO.getWineryName());
        winery.setAddress(wineryDTO.getAddress());
        winery.setStatusTime(new Timestamp(System.currentTimeMillis()));
        winery.setIsDelete(1);
        wineryRepository.saveAndFlush(winery);
        return winery;
    }

    //修改超级管理员用户
    public AdminUser updateSuperAdmin( String userName, String phone, List<String> roleId, AdminUser adminUser) {
        adminUser.setUserName(userName);
        adminUser.setName(userName);
        adminUser.setPhone(phone);
        adminUser.setStatusTime(new Timestamp(System.currentTimeMillis()));
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
        return adminUser;
    }

    //修改绑定小程序信息
    public void updateWineryConfigure( WineryDTO wineryDTO, AdminUser adminUser) {
        //通过酒庄id 获取酒庄的绑定小程序信息
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(adminUser.getWineryId());
        if(null == wineryConfigure ){
            wineryConfigure = new WineryConfigure();
            wineryConfigure.setWineryId(adminUser.getWineryId());
            wineryConfigure.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        wineryConfigure.setAppId(wineryDTO.getAppId());
        wineryConfigure.setAppSecret(wineryDTO.getAppSecret());
        wineryConfigure.setWxPayId(wineryDTO.getWxPayId());
        wineryConfigure.setWxPayKey(wineryDTO.getWxPayKey());
        wineryConfigure.setDomainName(wineryDTO.getDomainName());
        wineryConfigure.setQrCodeUrl(wineryDTO.getQrCodeUrl());
        wineryConfigure.setAppName(wineryDTO.getAppName());
        wineryConfigureRepository.saveAndFlush(wineryConfigure);
    }

    //停用启用状态
    public void updateStatus(Integer wineryId, String status) {
        Winery winery = wineryRepository.getOne(wineryId);
        if (winery != null) {
            winery.setStatus(status);
            wineryRepository.save(winery);
        }
    }

    //删除酒庄
    public void delWinery(Integer wineryId) {
        Winery winery = wineryRepository.getOne(wineryId);
        if (winery != null) {
            winery.setIsDelete(0);
            wineryRepository.save(winery);
        }
    }













    //***************************************************************************************************************

}
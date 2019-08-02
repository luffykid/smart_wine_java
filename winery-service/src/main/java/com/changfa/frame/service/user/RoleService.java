package com.changfa.frame.service.user;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.core.util.Constant;
import com.changfa.frame.core.util.MD5Util;
import com.changfa.frame.data.dto.operate.*;
import com.changfa.frame.data.dto.saas.AdminDTO;
import com.changfa.frame.data.dto.saas.LevelDTO;
import com.changfa.frame.data.dto.saas.MemberListDTO;
import com.changfa.frame.data.dto.saas.OrderListDTO;
import com.changfa.frame.data.dto.wechat.UserDTO;
import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.entity.common.CacheUtil;
import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.entity.deposit.DepositOrder;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.offline.OfflineOrder;
import com.changfa.frame.data.entity.offline.OfflineOrderPrice;
import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.entity.point.UserPointDetail;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.*;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.entity.winery.WineryConfigure;
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
import com.changfa.frame.data.repository.winery.WineryRepository;
import com.changfa.frame.service.PicturePathUntil;
import com.changfa.frame.service.dict.DictService;
import com.changfa.frame.service.market.MarketActivityService;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
@Service
public class RoleService {

    private static Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private  RoleMenuRepository roleMenuRepository;
    @Autowired
    private WineryRepository wineryRepository;


    //根据角色名称获取角色信息
    public Role getRoleByName(Integer wineryId, String roleName){
        Role role = roleRepository.findRoleByWineryIdAndName(wineryId,roleName);
        return  role;
    }

    //添加角色
    public void addRole(AdminUser adminUser,RoleDTO roleDTO) {
        Role role = new Role();
        role.setWineryId(roleDTO.getWineryId());
        role.setName(roleDTO.getName());
        role.setDescri(roleDTO.getDescri());
        role.setCreateUserId(adminUser.getId());
        role.setStatus(roleDTO.getStatus());
        role.setCreateTime(new Date());
        role.setStatusTime(role.getCreateTime());
        roleRepository.saveAndFlush(role);
    }

    //角色列表
    public List<RoleListDTO> roleList(String name) {
        List<Role>   roleList = roleRepository.findAllLikeName(name);
        List<RoleListDTO>  roleListDTOList = new ArrayList<>();
        for (Role role : roleList) {
            RoleListDTO roleListDTO = new RoleListDTO();
            roleListDTO.setId(role.getId());
            Winery winery = wineryRepository.getOne(role.getWineryId());
            if(null != winery){
                roleListDTO.setWineryName(winery.getName());
            }
            roleListDTO.setName(role.getName());
            roleListDTO.setDescri(role.getDescri());
            roleListDTO.setStatus(role.getStatus());
            roleListDTOList.add(roleListDTO);
        }
        return roleListDTOList;
    }


    /**
     * 角色详情
     *
     * @param roleId     角色id
     * @return
     */
    public RoleDetailDTO roleDetail(Integer roleId) {
        RoleDetailDTO roleDetailDTO = new RoleDetailDTO();
        Role role = roleRepository.getOne(roleId);
        if (role != null) {
            roleDetailDTO.setId(role.getId());
            Winery winery = wineryRepository.getOne(role.getWineryId());
            if(null != winery){
                roleDetailDTO.setWineryName(winery.getName());
            }
            roleDetailDTO.setName(role.getName());
            roleDetailDTO.setDescri(role.getDescri());
            roleDetailDTO.setStatus(role.getStatus());
            List<Map<String, String>> menuList = menuList(role);
            roleDetailDTO.setMenuList(menuList);
        }
        return roleDetailDTO;
    }


    //修改角色
    public void updateRole( RoleDTO roleDTO, Role role) {
        role.setName(roleDTO.getName());
        role.setDescri(roleDTO.getDescri());
        role.setStatus(roleDTO.getStatus());
        role.setStatusTime(new Date());
        roleRepository.saveAndFlush(role);
    }

    //停用启用状态
    public void updateStatus(Integer roleId, String status) {
        Role role = roleRepository.getOne(roleId);
        if (role != null) {
            role.setStatus(status);
            roleRepository.save(role);
        }
    }

    //删除角色
    public void delRole(Integer roleId) {
        Role role = roleRepository.getOne(roleId);
        if (role != null) {
            roleMenuRepository.deleteByRoleId(roleId);
            roleRepository.delete(role);
        }
    }


    //菜单权限配置列表
    public List<Map<String, String>> menuList(Role role) {
        List<Map<String, String>> menuList = new ArrayList<>();
        //某个酒庄下的所有菜单权限
        List<Menu>  list = menuRepository.findAllByWineryId(role.getWineryId());
        //某个角色下的所有菜单权限id
        List<Integer> edList =  roleMenuRepository.findMenuIdListByRoleId(role.getId());
        for (Menu menu : list) {
            Map<String, String> map = new HashMap<>();
            map.put("id", menu.getId().toString());
            map.put("pId", menu.getParentMenuId()==null?null:menu.getParentMenuId().toString());
            map.put("name", menu.getName());
            // 默认展开树
            map.put("open", "true");
            // 如果角色已有该权限，则默认选中
            if (edList.contains(menu.getId())) {
                map.put("checked", "true");
            }
            menuList.add(map);
        }
        return menuList;
    }

    //保存菜单权限配置
    public void saveMenu(Integer roleId , List<Integer> menuIds) {
        //删除之前的记录
        roleMenuRepository.deleteByRoleId(roleId);
        for(Integer i : menuIds){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(i);
            roleMenu.setCreateTime(new Date());
            roleMenuRepository.saveAndFlush(roleMenu);
        }
    }




}

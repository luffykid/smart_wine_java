package com.changfa.frame.service.jpa.user;

import com.changfa.frame.data.dto.operate.*;
import com.changfa.frame.data.entity.user.*;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.repository.user.*;
import com.changfa.frame.data.repository.winery.WineryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Role role = roleRepository.findRoleByWineryIdAndName(wineryId.longValue(),roleName);
        return  role;
    }

    //添加角色
    public void addRole(AdminUser adminUser,RoleDTO roleDTO) {
        Role role = new Role();
        role.setWineryId(roleDTO.getWineryId());
        role.setName(roleDTO.getName());
        role.setDescri(roleDTO.getDescri());
        role.setCreateUserId(adminUser.getId().intValue());
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
        Role role = roleRepository.getOne(roleId.longValue());
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
        Role role = roleRepository.getOne(roleId.longValue());
        if (role != null) {
            role.setStatus(status);
            roleRepository.save(role);
        }
    }

    //删除角色
    public void delRole(Integer roleId) {
        Role role = roleRepository.getOne(roleId.longValue());
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

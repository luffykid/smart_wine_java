package com.changfa.frame.website.controller.role;

import com.changfa.frame.data.dto.operate.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Role;
import com.changfa.frame.data.repository.user.RoleRepository;
import com.changfa.frame.data.repository.winery.WineryRepository;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.service.jpa.user.RoleService;
import com.changfa.frame.service.jpa.winery.WineryService;
import com.changfa.frame.website.common.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "角色管理", tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private WineryRepository wineryRepository;
    @Autowired
    private WineryService wineryService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "添加角色", notes = "添加角色")
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addWinery(@RequestBody RoleDTO roleDTO) {
        try {
            log.info("添加角色:roleDTO:" + roleDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(roleDTO.getToken());
            Role role = null;
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + roleDTO.getToken());
            } else if (roleDTO.getName().equals("") || roleDTO.getName() == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "请填写角色名称");
            } else if (StringUtils.isNotBlank(roleDTO.getName()) && roleDTO.getWineryId() != null) {
                role  = roleService.getRoleByName(roleDTO.getWineryId(),roleDTO.getName());
            }
            if (role != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同的角色名称");
            }
            roleService.addRole(adminUser, roleDTO);
            return JsonReturnUtil.getJsonIntReturn(0, "添加角色成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }

    }

    @ApiOperation(value = "角色列表", notes = "角色列表")
    @RequestMapping(value = "/roleList", method = RequestMethod.POST)
    public String roleList(@RequestParam("token") String token) {
        try {
            log.info("角色列表搜索:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<RoleListDTO> roleList = roleService.roleList("");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", roleList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "角色列表搜索", notes = "角色列表搜索")
    @RequestMapping(value = "/roleListSearch", method = RequestMethod.POST)
    public String roleListSearch(@RequestParam("token") String token, @RequestParam("name") String name) {
        try {
            log.info("角色列表搜索:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<RoleListDTO> roleList = roleService.roleList(name);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", roleList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "角色详情", notes = "角色详情")
    @RequestMapping(value = "/roleDetail", method = RequestMethod.POST)
    public String roleDetail(@RequestParam("roleId") Integer roleId) {
        try {
            log.info("角色详情:roleId:" + roleId);
            RoleDetailDTO roleDetail  = roleService.roleDetail(roleId);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", roleDetail).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public String updateRole(@RequestBody RoleDTO roleDTO) {
        try {
            log.info("添加角色:roleDTO:" + roleDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(roleDTO.getToken());
            if(roleDTO.getId().equals(null) || roleDTO.getId() == null){
                return JsonReturnUtil.getJsonIntReturn(1, "角色信息有误");
            }
            Role roleOld = roleRepository.getOne(roleDTO.getId());
            if (roleOld == null) {
                return JsonReturnUtil.getJsonIntReturn(4, "找不到角色id" + roleDTO.getId());
            }
            Role role = null;
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + roleDTO.getToken());
            } else if (roleDTO.getName().equals("") || roleDTO.getName() == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "请填写角色名称");
            } else if (StringUtils.isNotBlank(roleDTO.getName())  && !roleOld.getName().equals(roleDTO.getName())  ) {
                role  = roleService.getRoleByName(roleOld.getWineryId(),roleDTO.getName());
            }
            if (role != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同的角色名称");
            }
            roleService.updateRole(roleDTO, roleOld);
            return JsonReturnUtil.getJsonIntReturn(0, "修改角色成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }

    }








    @ApiOperation(value = "启用停用状态", notes = "启用停用状态")
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public String updateStatus(@RequestParam("token") String token,@RequestParam("roleId") Integer roleId, @RequestParam("status") String status){
        try {
            log.info("启用停用状态：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            roleService.updateStatus(roleId,status);
            if(status.equals("A")){
                return JsonReturnUtil.getJsonIntReturn(0, "启用成功" );
            }else{
                return JsonReturnUtil.getJsonIntReturn(0, "停用成功" );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @RequestMapping(value = "/delRole", method = RequestMethod.POST)
    public String delRole(@RequestParam("token") String token, @RequestParam("roleId") Integer roleId) {
        try {
            log.info("删除角色:roleId:" + roleId);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }  else {
                roleService.delRole(roleId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除角色成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "菜单权限配置列表", notes = "菜单权限配置列表")
    @RequestMapping(value = "/menuList", method = RequestMethod.POST)
    public String menuList(@RequestParam("token") String token, @RequestParam("roleId") Integer roleId) {
        try {
            log.info("菜单权限配置列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                Role role = roleRepository.getOne(roleId);
                if(null == role){
                    return JsonReturnUtil.getJsonIntReturn(1, "角色信息有误" + token);
                }
                List<Map<String, String>>  menuList = roleService.menuList(role);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", menuList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "保存菜单权限配置", notes = "保存菜单权限配置")
    @RequestMapping(value = "/saveMenu", method = RequestMethod.POST)
    public String saveMenu(@RequestParam("token") String token, @RequestParam("roleId") Integer roleId, @RequestParam("menuIds") List<Integer> menuIds) {
        try {
            log.info("菜单权限配置列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                Role role = roleRepository.getOne(roleId);
                if(null == role){
                    return JsonReturnUtil.getJsonIntReturn(1, "角色信息有误" + token);
                }
                roleService.saveMenu(roleId,menuIds);
                return JsonReturnUtil.getJsonIntReturn(0, "保存菜单权限配置成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }














    //********************************************************************************************************************


}

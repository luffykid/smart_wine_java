package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.RoleMenu;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleMenuRepository extends AdvancedJpaRepository<RoleMenu,Integer> {
    List<RoleMenu> findByRoleId(Integer rid);

    @Query(value = "select distinct menu_id from role_menu where role_id in ?1",nativeQuery = true)
    List<Integer> findMenuIdList(Object[] roleIdList);

    /*某个角色下的所有菜单权限id*/
    @Query(value = "select distinct menu_id from role_menu where role_id = ?1",nativeQuery = true)
    List<Integer> findMenuIdListByRoleId(Integer roleId);


    @Modifying
    @Transactional
    void deleteByRoleId(Integer roleId);

}

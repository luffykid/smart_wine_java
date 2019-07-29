package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.Menu;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface MenuRepository extends AdvancedJpaRepository<Menu,Integer> {
    List<Menu> findByParentMenuId(Integer pId);

    List<Menu> findByParentMenuIdIn(List<Integer> menuIdList);

    /*某个酒庄下的所有菜单权限*/
    List<Menu> findAllByWineryId(Integer wineryId);



}

package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.Role;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends AdvancedJpaRepository<Role,Integer> {
    List<Role> findByWineryId(Integer wid);

    //该用户的角色列表
    @Query(value = "select r.* from user_role ur LEFT JOIN role r ON r.id = ur.role_id  where r.winery_id = ?1 and ur.user_id = ?2 ",nativeQuery = true)
    List<Role> findRoleList(Integer wineryId, Integer userId);

    Role findRoleByWineryIdAndName(Integer wineryId,String name);


    @Query(value = "select * from role  where  name LIKE CONCAT('%' ,?1, '%')  order by create_time desc ", nativeQuery = true)
    List<Role> findAllLikeName(String name);






}

package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface AdminUserRepository extends AdvancedJpaRepository<AdminUser,Integer> {
    @Query("select a  from AdminUser a where a.token = ?1")
    AdminUser findAdminUserByToken(String token);

    @Query("select a from AdminUser a where a.userName = ?1 and a.wineryId = ?2")
    AdminUser findAdminUserByUserName(String userName,Integer wid);

    @Query(value = "select * from admin_user where phone = ?1 and winery_id=?2 ",nativeQuery = true)
    AdminUser findAdminUserByPhone(String phone,Integer wineryId);

    @Query(value = "select * from admin_user where phone = ?1 ",nativeQuery = true)
    AdminUser findAdminUserByPhone(String phone);

    @Query(value = "SELECT DISTINCT au.* from admin_user au,user_role ur,role r where au.id = ur.user_id and ur.role_id = r.id and au.winery_id = ?1 and (au.user_name like CONCAT('%',?2,'%') or au.phone like CONCAT('%',?2,'%') or r.name like CONCAT('%',?2,'%')) order by id desc",nativeQuery = true)
    List<AdminUser> findAdminUserByWId(int wineryId, String search);

    @Query(value = "SELECT au.* \n" +
            "FROM admin_user au \n" +
            "LEFT JOIN user_role ur ON au.id = ur.user_id\n" +
            "LEFT JOIN role r ON ur.role_id = r.id\n" +
            "WHERE au.winery_id = ?1 AND r.name = ?2 AND au.status = 'A'",nativeQuery = true)
    List<AdminUser> findAdminUserByWIdAndRoleName(int wineryId, String roleName);

    //获取酒庄的超级管理员用户
    @Query("select a from AdminUser a where a.wineryId = ?1 and a.isSuper = 'Y' ")
    AdminUser findAdminUserByWineryId(Integer wineryId);


    /*酒庄下的工作人员数量*/
    int countAdminUsersByWineryIdAndStatus(Integer wineryId,  String status);



    //运营端登录用户校验
    @Query(value = "select * from admin_user where phone = ?1 and status = 'A' and is_super='operate' and winery_id=0",nativeQuery = true)
    AdminUser findAdminUserByPhone2(String phone);


    /*运营端   根据token查询运营端登录用户*/
    @Query("select a  from AdminUser a where a.token = ?1 and status='A' and isSuper='operate' ")
    AdminUser findAdminUserByToken2(String token);


    /*运营端 只显示超级管理员用户*/
    @Query(value = "SELECT DISTINCT au.* from admin_user au,user_role ur,role r where au.id = ur.user_id and ur.role_id = r.id and au.is_super='Y' and au.status='A' and (au.user_name like CONCAT('%',?2,'%') or au.phone like CONCAT('%',?2,'%') or r.name like CONCAT('%',?2,'%')) order by id desc",nativeQuery = true)
    List<AdminUser> findAdminUserByWId2(String search);




}

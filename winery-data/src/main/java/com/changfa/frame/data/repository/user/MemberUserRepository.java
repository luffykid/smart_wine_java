package com.changfa.frame.data.repository.user;


import com.changfa.frame.data.entity.user.MemberUser;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MemberUserRepository extends AdvancedJpaRepository<MemberUser, Integer> {

    MemberUser findByUserId(Integer userId);

    @Query(value = "select mu.* from member_user mu,user u where mu.user_id = u.id and member_level_id in(?1) and winery_id = ?2",nativeQuery = true)
    List<MemberUser> findByMemberLevelId(List<Integer> levelId,Integer wineryId);

    @Query(value = "select mu.* from member_user mu,user u where mu.user_id = u.id and winery_id = ?1",nativeQuery = true)
    List<MemberUser> findByWineryId(Integer wineryId);

    @Query(value = "select m.nick_name,u.phone from member_user m left join user u on u.id= m.user_id where u.winery_id = ?1 and m.nick_name like CONCAT('%',?2,'%')",nativeQuery = true)
    List<Object[]> findByNickNameLike(Integer wineryId,String nickName);

    List<MemberUser> findByMemberLevelId(Integer memberLevelId);

}

package com.changfa.frame.data.repository.user;


import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MemberWechatRepository extends AdvancedJpaRepository<MemberWechat, Integer> {

    MemberWechat findByMbrId(Integer mbrId);

    @Query(value = "select mw.* from m_member_wechat mw,m_member m where mw.mbr_id = m.id and member_level in(?1) and winery_id = ?2",nativeQuery = true)
    List<MemberWechat> findByMemberLevelId(List<Integer> levelId, Long wineryId);

    @Query(value = "select mw.* from m_member_wechat mw,m_member m where mw.mbr_id = m.id and winery_id = ?1",nativeQuery = true)
    List<MemberWechat> findByWineryId(Long wineryId);

    @Query(value = "select m.nick_name,m.phone from m_member_wechat mw left join m_member m on m.id= mw.mbr_id where mw.winery_id = ?1 and m.nick_name like CONCAT('%',?2,'%')",nativeQuery = true)
    List<Object[]> findByNickNameLike(Long wineryId,String nickName);

    List<MemberWechat> findByMemberLevel(Integer memberLevel);

}

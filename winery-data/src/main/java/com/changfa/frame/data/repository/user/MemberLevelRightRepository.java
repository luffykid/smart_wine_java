package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.entity.user.MemberLevelRight;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/12 0012.
 */
public interface MemberLevelRightRepository extends AdvancedJpaRepository<MemberLevelRight,Integer> {
    MemberLevelRight findByMemberLevelId(Integer mid);
}

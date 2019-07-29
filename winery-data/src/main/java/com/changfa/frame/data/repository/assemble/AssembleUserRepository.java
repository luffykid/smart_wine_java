package com.changfa.frame.data.repository.assemble;

import com.changfa.frame.data.entity.assemble.AssembleUser;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2019/05/09 0011.
 */
public interface AssembleUserRepository extends AdvancedJpaRepository<AssembleUser,Integer> {


	List<AssembleUser> findAssembleUsersByAssembleListIs(Integer assembleListId);



}

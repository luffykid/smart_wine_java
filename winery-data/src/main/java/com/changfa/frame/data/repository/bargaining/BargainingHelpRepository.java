package com.changfa.frame.data.repository.bargaining;

import com.changfa.frame.data.entity.bargaining.BargainingHelp;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2019/05/09 0011.
 */
public interface BargainingHelpRepository extends AdvancedJpaRepository<BargainingHelp,Integer> {

	List<BargainingHelp> findByBargainingUser(Integer bargainingUser);

}

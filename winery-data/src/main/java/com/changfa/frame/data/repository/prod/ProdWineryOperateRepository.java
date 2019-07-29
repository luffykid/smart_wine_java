package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdWineryOperate;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdWineryOperateRepository extends AdvancedJpaRepository<ProdWineryOperate,Integer> {

	@Modifying
	@Transactional
	void deleteByProdId(Integer prodId);
}

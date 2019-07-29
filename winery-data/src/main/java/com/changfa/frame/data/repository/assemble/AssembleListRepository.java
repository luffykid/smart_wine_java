package com.changfa.frame.data.repository.assemble;

import com.changfa.frame.data.entity.assemble.AssembleCommodity;
import com.changfa.frame.data.entity.assemble.AssembleList;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2019/05/09 0011.
 */
public interface AssembleListRepository extends AdvancedJpaRepository<AssembleList,Integer> {

	/* 该团购商品 拼团没有成功的团队列表  */
	@Query(value = "SELECT * FROM assemble_list  WHERE is_delete = 1 AND assemble_status = 1 AND assemble_commodity= ?1 ORDER BY create_time DESC ",nativeQuery = true)
	List<AssembleList> findByAssembleCommodity(Integer assembleId);


	/* 该团购商品 拼团没有成功的团队列表  */
	@Query(value = "select al.*  from assemble_list al \n" +
			"LEFT JOIN assemble_commodity ac on ac.id = al.assemble_commodity\n" +
			"LEFT JOIN prod p on p.id = ac.prod_id\n" +
			"where al.is_delete=1 and ac.is_delete=1 \n" +
			"and ac.winery_id = ?1 and p.name like CONCAT('%' ,?2, '%') \n" +
			"and al.assemble_status = 1 \n" +
			"ORDER BY al.create_time DESC ",nativeQuery = true)
	List<AssembleList> findByAssembleStatus(Integer wineryId, String input, Integer assembleStatus);

}

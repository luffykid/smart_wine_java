package com.changfa.frame.data.repository.bargaining;

import com.changfa.frame.data.entity.assemble.AssembleCommodity;
import com.changfa.frame.data.entity.bargaining.BargainingCommodity;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2019/05/09 0011.
 */
public interface BargainingCommodityRepository extends AdvancedJpaRepository<BargainingCommodity,Integer> {

	@Query(value = "SELECT\n" +
			"\tbc.*\n" +
			"FROM\n" +
			"\tbargaining_commodity bc\n" +
			"LEFT JOIN prod p ON p.id = bc.prod_id\n" +
			"WHERE\n" +
			"\tbc.is_delete = 1\n" +
			"AND bc.winery_id = ?1\n" +
			"AND p. NAME LIKE CONCAT('%' ,? 2, '%')\n" +
			"ORDER BY\n" +
			"\tbc.top_time DESC",nativeQuery = true)
	List<BargainingCommodity> findByWineryIdLikeName(Integer wineryId, String input);


	/*砍价商品的发起数量*/
	@Query(value = "SELECT\n" +
			"\tCOUNT(*)\n" +
			"FROM\n" +
			"\tbargaining_user bu\n" +
			"LEFT JOIN bargaining_commodity bc ON bu.bargaining_commodity = bc.id\n" +
			"WHERE\n" +
			"\tbc.is_delete = 1\n" +
			"AND bc.winery_id = ?1\n" +
			"AND bc.id = ?2",nativeQuery = true)
	int findByWineryIdAndIdUser(Integer wineryId, Integer id);

}

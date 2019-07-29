package com.changfa.frame.data.repository.assemble;

import com.changfa.frame.data.entity.assemble.AssembleCommodity;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2019/05/09 0011.
 */
public interface AssembleCommodityRepository extends AdvancedJpaRepository<AssembleCommodity,Integer> {

	@Query(value = "SELECT ac.* FROM assemble_commodity ac LEFT JOIN prod p ON p.id = ac.prod_id WHERE ac.is_delete = 1 AND ac.winery_id= ?1 AND p.NAME LIKE CONCAT('%' ,?2, '%') ORDER BY ac.top_time DESC ",nativeQuery = true)
	List<AssembleCommodity> findByWineryIdLikeName(Integer wineryId, String input);

	/*同一个商品的拼团人数*/
	@Query(value = "select COUNT(*) from assemble_user au LEFT JOIN assemble_commodity ac on au.assemble_commodity = ac.id where ac.is_delete = 1 AND  ac.winery_id= ?1 AND ac.id= ?2",nativeQuery = true)
	int findByWineryIdAndIdUser(Integer wineryId, Integer id);

	/*同一个商品的拼团队数*/
	@Query(value = "select COUNT(*) from assemble_list al LEFT JOIN assemble_commodity ac on al.assemble_commodity = ac.id where ac.is_delete = 1 AND al.is_delete = 1 AND ac.winery_id= ?1 AND ac.id= ?2",nativeQuery = true)
	int findByWineryIdAndIdTeam(Integer wineryId, Integer id);

	/*同一个团队的拼团人数*/
	@Query(value = "select COUNT(*) from assemble_user   where assemble_list = ?1 ",nativeQuery = true)
	int findByList(Integer assembleListId);

	/* 拼团详情  除了当前商品显示其他拼团商品*/
	@Query(value = "SELECT ac.* FROM assemble_commodity ac LEFT JOIN prod p ON p.id = ac.prod_id WHERE ac.is_delete = 1 AND ac.winery_id= ?1 AND ac.id != ?2 ORDER BY ac.top_time DESC ",nativeQuery = true)
	List<AssembleCommodity> findByWineryIdAndId(Integer wineryId, Integer assembleId);

}

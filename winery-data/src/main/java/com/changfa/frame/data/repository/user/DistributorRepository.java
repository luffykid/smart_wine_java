package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Distributor;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface DistributorRepository extends AdvancedJpaRepository<Distributor,Integer> {

	//该酒庄下通过姓名查询分销员
	@Query(value = "select * from distributor where winery_id = ?1 and name = ?2 and status !=0 ;",nativeQuery = true)
	Distributor findByWineryIdAndName(Integer wineryId, String name);

	//该酒庄下通过姓名查询分销员
	@Query(value = "select * from distributor where winery_id = ?1 and user_id = ?2  ;",nativeQuery = true)
	Distributor findByWineryIdAndUserId(Integer wineryId, Integer userId);

	@Modifying
	@Transactional
	void deleteByUserId(Integer userId);

	/*运营端 分销员管理列表*/
	@Query(value = "SELECT * from distributor  where name like CONCAT('%',?1,'%')  order by create_time desc",nativeQuery = true)
	List<Distributor> findAllByName(String search);







}

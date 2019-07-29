package com.changfa.frame.data.repository.winery;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WineryRepository extends AdvancedJpaRepository<Winery, Integer> {

	Winery findByName(String name);

	@Query(value = "select * from winery  where is_delete = 1 and status= 'A' and  name LIKE CONCAT('%' ,?1, '%') order by create_time desc  ", nativeQuery = true)
	List<Winery> findAllLikeName(String name);

//	@Query("select a from winery a where a.name = ?1")
//	Winery findWineryByName(String name);

	List<Winery> findAllByStatus(String status);


}

package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.PointExchangeVoucher;
import com.changfa.frame.data.entity.point.PointRewardRule;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PointExchangeVoucherRepository extends AdvancedJpaRepository<PointExchangeVoucher,Integer> {
    List<PointExchangeVoucher> findByPointRewardRuleId(Integer rid);

    List<PointExchangeVoucher> findByPointRewardRuleIdAndStatus(Integer rid,String status);

    @Modifying
    @Transactional
    @Query(value = "delete from point_exchange_voucher where winery_id = ?1",nativeQuery = true)
    void deleteByWId(int wineryId);


    PointExchangeVoucher findByWineryIdAndVoucherId(Integer wineryId,Integer voucherId);

    List<PointExchangeVoucher> findByVoucherId(Integer voucherId);
}

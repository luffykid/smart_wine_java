package com.changfa.frame.data.repository.deposit;

import com.changfa.frame.data.entity.deposit.DepositRule;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepositRuleRepository extends AdvancedJpaRepository<DepositRule, Integer> {

    DepositRule findByWineryIdAndStatus(Integer wineryId, String status);

    @Query(value = "select * from deposit_rule where winery_id = ?1 limit 1",nativeQuery = true)
    DepositRule findByWineryId(Integer wineryId);

    @Query(value = "select count(*) from deposit_rule d LEFT JOIN deposit_rule_spec_detail s on d.id = s.deposit_rule_id where s.present_voucher_id =?1 ",nativeQuery = true)
    Integer findByVoucher(Integer voucherId);

}

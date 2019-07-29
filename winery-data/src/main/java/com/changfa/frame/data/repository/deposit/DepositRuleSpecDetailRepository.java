package com.changfa.frame.data.repository.deposit;

import com.changfa.frame.data.entity.deposit.DepositRuleSpecDetail;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepositRuleSpecDetailRepository extends AdvancedJpaRepository<DepositRuleSpecDetail, Integer> {

    List<DepositRuleSpecDetail> findByDepositRuleId(Integer depositRuleId);

    @Modifying
    @Transactional
    @Query(value = "delete from deposit_rule_spec_detail where deposit_rule_id = ?1",nativeQuery = true)
    void deleteByDepositRuleId(int id);
}

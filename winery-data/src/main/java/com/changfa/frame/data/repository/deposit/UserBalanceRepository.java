package com.changfa.frame.data.repository.deposit;

import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface UserBalanceRepository extends AdvancedJpaRepository<UserBalance,Integer> {

    UserBalance findByUserId(Integer userId);
}

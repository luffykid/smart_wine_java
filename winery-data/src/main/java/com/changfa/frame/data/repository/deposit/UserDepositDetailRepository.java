package com.changfa.frame.data.repository.deposit;

import com.changfa.frame.data.entity.deposit.UserDepositDetail;

import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface UserDepositDetailRepository extends AdvancedJpaRepository<UserDepositDetail, Integer> {

    List<UserDepositDetail> findByUserIdOrderByCreateTimeDesc(Integer userId);
}
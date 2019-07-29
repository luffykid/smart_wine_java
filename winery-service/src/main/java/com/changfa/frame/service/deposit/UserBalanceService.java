package com.changfa.frame.service.deposit;

import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {
    private static Logger log = LoggerFactory.getLogger(UserBalanceService.class);

    @Autowired
    private UserBalanceRepository userBalanceRepository;


    public UserBalance findByUserId(Integer userId) {
        return userBalanceRepository.findByUserId(userId);
    }


}

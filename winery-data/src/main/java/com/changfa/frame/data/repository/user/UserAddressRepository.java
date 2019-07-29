package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.UserAddress;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAddressRepository extends AdvancedJpaRepository<UserAddress,Integer> {
    @Query(value = "select * from user_address where user_id = ?1 and is_default = 'Y' limit 1",nativeQuery = true)
    UserAddress findDefaultAddressByUserId(Integer userId);

    @Query(value = "select * from user_address where user_id = ?1",nativeQuery = true)
    List<UserAddress> findAddressByUserId(Integer userId);
}

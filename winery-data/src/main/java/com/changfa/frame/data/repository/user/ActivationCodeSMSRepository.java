package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.ActivationCodeSMS;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivationCodeSMSRepository extends AdvancedJpaRepository<ActivationCodeSMS,Integer> {

    @Query(value = "select * from activation_code where phone = ?1 and type = ?2 and status = 'A' and create_time = (select max(create_time) from activation_code where phone = ?1 and type = ?2) order by id desc LIMIT 1",nativeQuery = true)
    ActivationCodeSMS findByPhoneAndType(String phone, String type);

}

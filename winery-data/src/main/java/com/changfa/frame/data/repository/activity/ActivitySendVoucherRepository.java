package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.AcitivitySendVoucher;
import com.changfa.frame.data.entity.activity.Activity;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivitySendVoucherRepository extends AdvancedJpaRepository<AcitivitySendVoucher,Integer> {
    List<AcitivitySendVoucher> findByActivityId(Integer aid);

    @Modifying
    @Transactional
    @Query(value = "delete from acitivity_send_voucher where activity_id = ?1",nativeQuery = true)
    void deleteByActivityId(int id);
}

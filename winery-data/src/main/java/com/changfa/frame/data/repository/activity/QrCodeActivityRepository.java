package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.QrCodeActivity;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QrCodeActivityRepository extends AdvancedJpaRepository<QrCodeActivity,Integer> {


    @Query(value = "select * from qr_code_activity where winery_id = ?1 and status = 'A' LIMIT 1 ",nativeQuery = true)
    QrCodeActivity findByWineryId(Integer wineryId);

    List<QrCodeActivity> findByWineryIdAndStatus(Integer wineryId,String status);
}

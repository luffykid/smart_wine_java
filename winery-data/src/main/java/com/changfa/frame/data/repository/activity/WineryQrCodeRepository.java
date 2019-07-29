package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.WineryQrCode;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface WineryQrCodeRepository extends AdvancedJpaRepository<WineryQrCode,Integer> {

        List<WineryQrCode> findByWineryIdAndStatus(Integer wineryId,String status);
}

package com.changfa.frame.data.repository.winery;

import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface WineryConfigureRepository extends AdvancedJpaRepository<WineryConfigure, Integer> {

    WineryConfigure findByAppId(String appId);

    WineryConfigure findByWineryId(Integer wId);
}

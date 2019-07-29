package com.changfa.frame.data.repository.wine;

import com.changfa.frame.data.entity.wine.Wine;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface WineRepository extends AdvancedJpaRepository<Wine, Integer> {

    List<Wine> findByWineryIdAndStatus(Integer wineryId,String status);

}

package com.changfa.frame.data.repository.wine;

import com.changfa.frame.data.entity.wine.UserWine;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface UserWineRepository extends AdvancedJpaRepository<UserWine, Integer> {

    UserWine findByUserIdAndWineId(Integer userId,Integer wineId);


    List<UserWine> findByUserId(Integer userId);

}

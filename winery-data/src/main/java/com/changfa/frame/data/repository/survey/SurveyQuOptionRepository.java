package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyQuOption;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyQuOptionRepository extends AdvancedJpaRepository<SurveyQuOption,Integer> {

    List<SurveyQuOption> findAllByQuIdOrderBySortAsc(Integer questionId);
}

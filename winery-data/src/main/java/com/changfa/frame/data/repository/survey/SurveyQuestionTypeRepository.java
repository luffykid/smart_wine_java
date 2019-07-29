package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyAnOption;
import com.changfa.frame.data.entity.survey.SurveyQuestionType;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyQuestionTypeRepository extends AdvancedJpaRepository<SurveyQuestionType, Integer> {


}

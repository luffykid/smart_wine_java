package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyQuestionType;
import com.changfa.frame.data.entity.survey.SurveyTypeRef;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyTypeRefRepository extends AdvancedJpaRepository<SurveyTypeRef,Integer> {

	/*统计同一个答卷下的类型数量*/
	int countAllBySurveyId(Integer surveyId);


}

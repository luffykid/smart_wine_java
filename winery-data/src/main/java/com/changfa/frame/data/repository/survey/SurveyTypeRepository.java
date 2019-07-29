package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyQuestionType;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyTypeRepository extends AdvancedJpaRepository<SurveyQuestionType,Integer> {

    @Query(value = "SELECT sqt.* \n" +
            "FROM survey_question_type sqt\n" +
            "LEFT JOIN survey_type_ref str ON str.type_id = sqt.id\n" +
            "WHERE str.survey_id = ?1 \n" +
            "ORDER BY sqt.sort ASC",nativeQuery = true)
    List<SurveyQuestionType> surveyQuestionTypeListBySurveyId(Integer surveyQuestionnaireId);
}

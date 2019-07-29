package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyQuestionnaire;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyQuestionnaireRepository extends AdvancedJpaRepository<SurveyQuestionnaire,Integer> {

    @Query(value = "SELECT * FROM survey_questionnaire sq where sq.is_delete = 1 ORDER BY update_time DESC",nativeQuery = true)
    List<SurveyQuestionnaire> surveyQuestionnaireList();

    @Query(value = "SELECT * FROM survey_questionnaire sq  where sq.surey_questionnaire and sq.is_delete = 1  LIKE CONCAT('%',?1,'%')   ORDER BY update_time DESC",nativeQuery = true)
    List<SurveyQuestionnaire> findAllBySurveyName(String name);

    @Query(value = "SELECT * FROM survey_questionnaire sq  where sq.is_delete = 1 ORDER BY update_time DESC",nativeQuery = true)
    List<SurveyQuestionnaire> findAllBySurveyName();
}

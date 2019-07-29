package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyQuestion;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyQuestionRepository extends AdvancedJpaRepository<SurveyQuestion,Integer> {

    @Query(value = "SELECT sq.*\n" +
            "FROM survey_question sq \n" +
            "LEFT JOIN survey_question_type_ref sqtr ON sqtr.question_id = sq.id\n" +
            "WHERE sqtr.type_id = ?1\n" +
            "ORDER BY sq.sort ASC",nativeQuery = true)
    List<SurveyQuestion> questionListByType(Integer typeId);
}

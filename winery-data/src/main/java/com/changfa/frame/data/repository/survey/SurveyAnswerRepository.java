package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyAnswer;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyAnswerRepository extends AdvancedJpaRepository<SurveyAnswer, Integer> {

    @Query(value = "SELECT MAX(sa.sort) FROM survey_answer sa WHERE sa.survey_id = ?1", nativeQuery = true)
    Integer findAnsererByMaxSort(Integer surveyId);

    @Query(value = "SELECT sa.* FROM survey_answer sa WHERE sa.survey_id = ?1 AND (sa.answerer_name LIKE CONCAT('%',?2,'%') OR sa.phone = ?2 ) ORDER BY sa.create_time DESC", nativeQuery = true)
    List<SurveyAnswer> answerList(Integer surveyId, String search);

    @Query(value = "SELECT sa.* FROM survey_answer sa WHERE sa.survey_id = ?1 ORDER BY sa.create_time DESC", nativeQuery = true)
    List<SurveyAnswer> answerList(Integer surveyId);

    @Query(value = "SELECT SUM(sqo.content) FROM survey_an_option sao LEFT JOIN survey_qu_option sqo ON  sao.qu_id = sqo.qu_id WHERE sao.qu_option_id = sqo.sort and sao.belong_answer_id = ?1", nativeQuery = true)
    Integer sumScoreBySurvey(Integer belongAnswerId);

    @Query(value = "DELETE FROM survey_an_option WHERE id = 1?", nativeQuery = true)
    void deleteByanswerId(Integer answerId);

    /*问卷的答题人数*/
	int countAllBySurveyId(Integer surveyId);



}

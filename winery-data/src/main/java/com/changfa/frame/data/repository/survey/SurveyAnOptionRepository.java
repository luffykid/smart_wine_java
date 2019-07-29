package com.changfa.frame.data.repository.survey;

import com.changfa.frame.data.entity.survey.SurveyAnOption;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface SurveyAnOptionRepository extends AdvancedJpaRepository<SurveyAnOption, Integer> {

    @Query(value = "SELECT sqo.content FROM survey_an_option sa LEFT JOIN survey_qu_option sqo ON sa.qu_option_id = sqo.id WHERE sa.qu_id = ?1 AND sa.belong_answer_id = ?2", nativeQuery = true)
    String findByQuestionAndAnswer(Integer quId, Integer answerId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM survey_an_option WHERE belong_answer_id = 1?", nativeQuery = true)
    void deleteByanswerId(Integer answerId);

    @Transactional
    void deleteByBelongAnswerId(Integer answerId);
}

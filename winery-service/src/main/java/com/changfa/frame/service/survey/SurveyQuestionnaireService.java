package com.changfa.frame.service.survey;

import com.changfa.frame.data.dto.operate.SurveyQuestionnaireListDTO;
import com.changfa.frame.data.dto.saas.*;
import com.changfa.frame.data.entity.survey.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.repository.survey.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SurveyQuestionnaireService {
    private static Logger log = LoggerFactory.getLogger(SurveyQuestionnaireService.class);

    @Autowired
    private SurveyQuestionnaireRepository surveyQuestionnaireRepository;
    @Autowired
    private SurveyTypeRepository surveyTypeRepository;
    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;
    @Autowired
    private SurveyQuOptionRepository surveyQuOptionRepository;
    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;
    @Autowired
    private SurveyAnOptionRepository surveyAnOptionRepository;
    @Autowired
    private SurveyQuestionTypeRepository surveyQuestionTypeRepository;
    @Autowired
    private SurveyTypeRefRepository surveyTypeRefRepository;
    @Autowired
    private SurveyQuestionTypeRefRepository surveyQuestionTypeRefRepository;

    public List<SurveyQuestionnaire> surveyQuestionnaireList() {
        return this.surveyQuestionnaireRepository.surveyQuestionnaireList();
    }

    // 问卷详情
    public SurveyQuestionnaireDTO surveyQuestionnaireDetail(Integer surveyQuestionnaireId) {
        // 问卷基本信息
        SurveyQuestionnaire surveyQuestionnaire = this.surveyQuestionnaireRepository.getOne(surveyQuestionnaireId);
        SurveyQuestionnaireDTO surveyQuestionnaireDTO = new SurveyQuestionnaireDTO();
        surveyQuestionnaireDTO.setId(surveyQuestionnaire.getId());
        surveyQuestionnaireDTO.setSurveyName(surveyQuestionnaire.getSurveyName());
        surveyQuestionnaireDTO.setSurveyNote(surveyQuestionnaire.getSurveyNote());

        // 题目类型信息 列表
        List<SurveyQuestionType> surveyQuestionTypeList = surveyTypeRepository.surveyQuestionTypeListBySurveyId(surveyQuestionnaireId);

        // 根据题目类型获取题目
        List<SurveyQuestionTypeDTO> surveyQuestionTypeDTOList = new ArrayList<>();
        for (SurveyQuestionType surveyQuestionType : surveyQuestionTypeList) {
            SurveyQuestionTypeDTO surveyQuestionTypeDTO = new SurveyQuestionTypeDTO();
            List<SurveyQuestion> surveyQuestionList = this.surveyQuestionRepository.questionListByType(surveyQuestionType.getId());
            // 根据题目获取选项
            List<SurveyQuestionDTO> surveyQuestionDTOList = new ArrayList<>();
            for (SurveyQuestion surveyQuestion : surveyQuestionList) {
                SurveyQuestionDTO surveyQuestionDTO = new SurveyQuestionDTO();
                List<SurveyQuOption> surveyQuOptionList = this.surveyQuOptionRepository.findAllByQuIdOrderBySortAsc(surveyQuestion.getId());
                surveyQuestionDTO.setId(surveyQuestion.getId());
                surveyQuestionDTO.setQuTitle(surveyQuestion.getQuTitle());
                surveyQuestionDTO.setSort(surveyQuestion.getSort());
                surveyQuestionDTO.setCreateTime(surveyQuestion.getCreateTime());
                surveyQuestionDTO.setSurveyQuOptionList(surveyQuOptionList);
                surveyQuestionDTOList.add(surveyQuestionDTO);
            }
            surveyQuestionTypeDTO.setId(surveyQuestionType.getId());
            surveyQuestionTypeDTO.setTypeName(surveyQuestionType.getTypeName());
            surveyQuestionTypeDTO.setSort(surveyQuestionType.getSort());
            surveyQuestionTypeDTO.setCreateTime(surveyQuestionType.getCreateTime());
            surveyQuestionTypeDTO.setUpdateTime(surveyQuestionType.getUpdateTime());
            surveyQuestionTypeDTO.setSurveyQuestionDTOList(surveyQuestionDTOList);
            surveyQuestionTypeDTOList.add(surveyQuestionTypeDTO);
        }
        surveyQuestionnaireDTO.setSurveyQuestionTypeDTOList(surveyQuestionTypeDTOList);
        return surveyQuestionnaireDTO;
    }

    // 提交答卷
    public Integer submit(AnswerDTO answerDTO) {
        Integer sort = this.surveyAnswerRepository.findAnsererByMaxSort(answerDTO.getSurveyId());
        // 答题者信息表
        SurveyAnswer surveyAnswer = new SurveyAnswer();
        surveyAnswer.setSurveyId(answerDTO.getSurveyId());
        surveyAnswer.setAnswererName(answerDTO.getAnswererName());
        surveyAnswer.setCompanyName(answerDTO.getCompanyName());
        surveyAnswer.setPhone(answerDTO.getPhone());
        if (sort == null) {
            surveyAnswer.setSort(1);
        } else {
            surveyAnswer.setSort(sort + 1);
        }
        surveyAnswer.setCreateTime(new Timestamp(System.currentTimeMillis()));
        SurveyAnswer surveyAnswerSave = this.surveyAnswerRepository.saveAndFlush(surveyAnswer);

        // 答案保存表
        for (AnOptionDTO anOptionDTO : answerDTO.getAnOptionDTOList()) {
            SurveyAnOption surveyAnOption = new SurveyAnOption();
            surveyAnOption.setBelongAnswerId(surveyAnswerSave.getId());
            surveyAnOption.setBelongSurveyId(answerDTO.getSurveyId());
            surveyAnOption.setQuId(anOptionDTO.getQuId());
            surveyAnOption.setQuOptionId(anOptionDTO.getQuOptionId());
            this.surveyAnOptionRepository.save(surveyAnOption);
        }
        return surveyAnswerSave.getId();
    }

    // 答卷列表
    public List<AnswerDTO> answerList(String search) {
        List<SurveyAnswer> answerList;
        if (search == null) {
            answerList = this.surveyAnswerRepository.answerList(1);
        } else {
            answerList = this.surveyAnswerRepository.answerList(1, search);
        }
        List<AnswerDTO> answerDTOList = new ArrayList<>();
        for (SurveyAnswer surveyAnswer : answerList) {
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setId(surveyAnswer.getId());
            answerDTO.setAnswererName(surveyAnswer.getAnswererName());
            answerDTO.setPhone(surveyAnswer.getPhone());
            answerDTO.setCompanyName(surveyAnswer.getCompanyName());
            answerDTO.setCreateTime(surveyAnswer.getCreateTime());
            Integer score = this.surveyAnswerRepository.sumScoreBySurvey(surveyAnswer.getId());
            answerDTO.setScore(score);
            answerDTOList.add(answerDTO);
        }
        return answerDTOList;
    }

    // 答卷详情
    public AnswerDTO answerDetail(Integer answerId) {
        // 答卷基本信息
        SurveyAnswer surveyAnswer = this.surveyAnswerRepository.getOne(answerId);
        Integer score = this.surveyAnswerRepository.sumScoreBySurvey(answerId);
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswererId(surveyAnswer.getId());
        answerDTO.setSurveyId(surveyAnswer.getSurveyId());
        answerDTO.setAnswererName(surveyAnswer.getAnswererName());
        answerDTO.setPhone(surveyAnswer.getPhone());
        answerDTO.setCompanyName(surveyAnswer.getCompanyName());
        answerDTO.setScore(score);
        //答题者留言反馈
        answerDTO.setAnswererMessage(surveyAnswer.getAnswererMessage());

        //根据答题酒旗星建议
        if(answerDTO.getScore() < 120){
            answerDTO.setWinerySuggestion("小于120分，游客中心是酒庄营销的重要组成部分，所以一个好的酒庄需求有个时尚化的游客中心。");
        }else if(answerDTO.getScore() >= 120){
            answerDTO.setWinerySuggestion("大于等于120分，游客中心是酒庄营销的重要组成部分，所以一个好的酒庄需求有个时尚化的游客中心。");
        }
        surveyAnswer.setScore(answerDTO.getScore());
        surveyAnswer.setWinerySuggestion(answerDTO.getWinerySuggestion());
        this.surveyAnswerRepository.saveAndFlush(surveyAnswer);

        // 答卷详情
        // 题目类型列表
        List<SurveyQuestionType> surveyQuestionTypeList = this.surveyTypeRepository.surveyQuestionTypeListBySurveyId(surveyAnswer.getSurveyId());
        List<SurveyQuestionTypeDTO> surveyQuestionTypeDTOList = new ArrayList<>();
        for (SurveyQuestionType surveyQuestionType : surveyQuestionTypeList) {
            Integer typeScore = 0;
            SurveyQuestionTypeDTO surveyQuestionTypeDTO = new SurveyQuestionTypeDTO();
            surveyQuestionTypeDTO.setTypeName(surveyQuestionType.getTypeName());
            // 题目列表
            List<SurveyQuestion> surveyQuestionList = this.surveyQuestionRepository.questionListByType(surveyQuestionType.getId());
            List<SurveyQuestionDTO> surveyQuestionDTOList = new ArrayList<>();
            for (SurveyQuestion surveyQuestion : surveyQuestionList) {
                SurveyQuestionDTO surveyQuestionDTO = new SurveyQuestionDTO();
                // 得分
                String quScore = this.surveyAnOptionRepository.findByQuestionAndAnswer(surveyQuestion.getId(), answerId);
                surveyQuestionDTO.setQuTitle(surveyQuestion.getQuTitle());
                if (null != quScore) {
                    surveyQuestionDTO.setScore(Integer.valueOf(quScore));
                    typeScore += Integer.valueOf(quScore);
                }
                surveyQuestionDTOList.add(surveyQuestionDTO);
            }
            surveyQuestionTypeDTO.setTypeScore(typeScore);
            surveyQuestionTypeDTO.setSurveyQuestionDTOList(surveyQuestionDTOList);
            surveyQuestionTypeDTOList.add(surveyQuestionTypeDTO);
        }
        answerDTO.setSurveyQuestionTypeDTOList(surveyQuestionTypeDTOList);
        return answerDTO;
    }

    // 提交留言反馈
    public void submitMessage(SurveyAnswer surveyAnswer , String message) {
            surveyAnswer.setAnswererMessage(message);
        this.surveyAnswerRepository.saveAndFlush(surveyAnswer);
    }


    // 删除答卷
    public void deleteAnswer(Integer answerId) {
        // 删除答案选项保存表
        this.surveyAnOptionRepository.deleteByBelongAnswerId(answerId);
        // 删除答题者信息表
        this.surveyAnswerRepository.deleteById(answerId);
    }


    //运营端  问卷设置 ***********************************************


    // 问卷设置 添加修改问卷
    public Integer addQuestionnaire(AdminUser adminUser,SurveyQuestionnaireDTO surveyQuestionnaireDTO) {
        //调查问卷表
        SurveyQuestionnaire surveyQuestionnaire = null;
        if(surveyQuestionnaireDTO.getId()==null){
            surveyQuestionnaire = new SurveyQuestionnaire();
            surveyQuestionnaire.setUserId(adminUser.getId());
            surveyQuestionnaire.setCreateTime(new Date());
            surveyQuestionnaire.setUpdateTime(surveyQuestionnaire.getCreateTime());
            surveyQuestionnaire.setModifier(null);
            surveyQuestionnaire.setIsDelete(1);
        }else{
            surveyQuestionnaire = surveyQuestionnaireRepository.getOne(surveyQuestionnaireDTO.getId());
            surveyQuestionnaire.setUpdateTime(new Date());
            surveyQuestionnaire.setModifier(adminUser.getId());
        }
        surveyQuestionnaire.setSurveyName(surveyQuestionnaireDTO.getSurveyName());
        surveyQuestionnaire.setSurveyNote(surveyQuestionnaireDTO.getSurveyNote());
        surveyQuestionnaire.setIsRelease(surveyQuestionnaireDTO.getIsRelease());//是否发布 Y:发布，N未发布
        surveyQuestionnaireRepository.saveAndFlush(surveyQuestionnaire);
        //题目类别表
        List<SurveyQuestionTypeDTO> surveyQuestionTypeDTOList = surveyQuestionnaireDTO.getSurveyQuestionTypeDTOList();
        if(null != surveyQuestionTypeDTOList && surveyQuestionTypeDTOList.size()>0 ){
            for(int i = 0; i< surveyQuestionTypeDTOList.size(); i++){
                SurveyQuestionTypeDTO surveyQuestionTypeDTO = surveyQuestionTypeDTOList.get(i);
                SurveyQuestionType surveyQuestionType = null;
                if(surveyQuestionTypeDTO.getId()==null){
                    surveyQuestionType = new SurveyQuestionType();
                    if(surveyQuestionnaireDTO.getId()==null){
                        surveyQuestionType.setSort(i+1);
                    }else{
                        int typeCounts=  surveyTypeRefRepository.countAllBySurveyId(surveyQuestionnaire.getId());
                        surveyQuestionType.setSort(typeCounts+1);
                    }
                    surveyQuestionType.setCreateTime(surveyQuestionnaire.getCreateTime());
                    surveyQuestionType.setUpdateTime(surveyQuestionType.getCreateTime());
                }else{
                    surveyQuestionType = surveyQuestionTypeRepository.getOne(surveyQuestionTypeDTO.getId());
                    surveyQuestionType.setUpdateTime(new Date());
                }
                surveyQuestionType.setTypeName(surveyQuestionTypeDTO.getTypeName());
                surveyQuestionTypeRepository.saveAndFlush(surveyQuestionType);
                //问卷及类别关联表
                if(surveyQuestionTypeDTO.getId()==null){
                    SurveyTypeRef surveyTypeRef = new SurveyTypeRef();
                    surveyTypeRef.setSurveyId(surveyQuestionnaire.getId());
                    surveyTypeRef.setTypeId(surveyQuestionType.getId());
                    surveyTypeRefRepository.saveAndFlush(surveyTypeRef);
                }
                //题目表
                List<SurveyQuestionDTO> surveyQuestionDTOList = surveyQuestionTypeDTO.getSurveyQuestionDTOList();
                if(null != surveyQuestionDTOList && surveyQuestionDTOList.size()>0 ){
                    for(int j = 0; j< surveyQuestionDTOList.size(); j++){
                        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionDTOList.get(j);
                        SurveyQuestion surveyQuestion = null;
                        if(surveyQuestionDTO.getId()==null){
                            surveyQuestion = new SurveyQuestion();
                            surveyQuestion.setSort(j+1);
                            surveyQuestion.setCreateTime(surveyQuestionType.getCreateTime());
                        }else{
                            surveyQuestion = surveyQuestionRepository.getOne(surveyQuestionDTO.getId());
                        }
                        surveyQuestion.setQuTitle(surveyQuestionDTO.getQuTitle());
                        surveyQuestionRepository.saveAndFlush(surveyQuestion);
                        //题目及题目类型关联表
                        if(surveyQuestionDTO.getId()==null){
                            SurveyQuestionTypeRef surveyQuestionTypeRef = new SurveyQuestionTypeRef();
                            surveyQuestionTypeRef.setTypeId(surveyQuestionType.getId());
                            surveyQuestionTypeRef.setQuestionId(surveyQuestion.getId());
                            surveyQuestionTypeRefRepository.saveAndFlush(surveyQuestionTypeRef);
                            //题目选项
                            List<SurveyQuOption> surveyQuOptionList = surveyQuestionDTO.getSurveyQuOptionList();
                            if(null != surveyQuOptionList && surveyQuOptionList.size()>0 ){
                                for(int m = 0; m< surveyQuestionDTOList.size(); m++){
                                    SurveyQuOption surveyQuOptionDTO = surveyQuOptionList.get(m);
                                    SurveyQuOption surveyQuOption = new SurveyQuOption();
                                    surveyQuOption.setQuId(surveyQuestion.getId());
                                    surveyQuOption.setContent(surveyQuOptionDTO.getContent());
                                    surveyQuOption.setSort(m);
                                    surveyQuOptionRepository.saveAndFlush(surveyQuOption);
                                }
                            }

                        }
                    }
                }
            }
        }
        return surveyQuestionnaire.getId();
    }

    // 问卷设置 问卷列表
    public List<SurveyQuestionnaireListDTO> questionnaireList(String search) {
        List<SurveyQuestionnaire> surveyQuestionnaireList;
        if (search == null) {
            surveyQuestionnaireList = surveyQuestionnaireRepository.findAllBySurveyName();
        } else {
            surveyQuestionnaireList = surveyQuestionnaireRepository.findAllBySurveyName(search);
        }
        List<SurveyQuestionnaireListDTO> surveyQuestionnaireListDTOList = new ArrayList<>();
        for (SurveyQuestionnaire surveyQuestionnaire : surveyQuestionnaireList) {
            SurveyQuestionnaireListDTO surveyQuestionnaireListDTO = new SurveyQuestionnaireListDTO();
            surveyQuestionnaireListDTO.setId(surveyQuestionnaire.getId());
            surveyQuestionnaireListDTO.setSurveyName(surveyQuestionnaire.getSurveyName());
            surveyQuestionnaireListDTO.setIsRelease(surveyQuestionnaire.getIsRelease());
            surveyQuestionnaireListDTO.setCreateTime(surveyQuestionnaire.getCreateTime());
            int answerNum = surveyAnswerRepository.countAllBySurveyId(surveyQuestionnaire.getId());
            surveyQuestionnaireListDTO.setAnswerNum(answerNum);
            surveyQuestionnaireListDTOList.add(surveyQuestionnaireListDTO);
        }
        return surveyQuestionnaireListDTOList;
    }



    // 删除问卷
    public void deleteQuestionnaire(Integer questionnaireId) {
        SurveyQuestionnaire surveyQuestionnaire = surveyQuestionnaireRepository.getOne(questionnaireId);
        if(null != surveyQuestionnaire){
            surveyQuestionnaire.setIsDelete(0);
            surveyQuestionnaireRepository.saveAndFlush(surveyQuestionnaire);
        }

    }

















}

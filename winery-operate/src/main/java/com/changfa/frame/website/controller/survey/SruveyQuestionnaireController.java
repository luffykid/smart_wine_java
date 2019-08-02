package com.changfa.frame.website.controller.survey;


import com.changfa.frame.data.dto.operate.SurveyQuestionnaireListDTO;
import com.changfa.frame.data.dto.saas.AnswerDTO;
import com.changfa.frame.data.dto.saas.SurveyQuestionnaireDTO;
import com.changfa.frame.data.entity.survey.SurveyAnswer;
import com.changfa.frame.data.entity.survey.SurveyQuestionnaire;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.repository.survey.SurveyAnswerRepository;
import com.changfa.frame.service.survey.SurveyQuestionnaireService;
import com.changfa.frame.service.user.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/surveyQuestionnaire")
@Api(value = "问卷管理", tags = {"问卷管理"})
public class SruveyQuestionnaireController {

    private static Logger log = LoggerFactory.getLogger(SruveyQuestionnaireController.class);

    @Autowired
    private SurveyQuestionnaireService surveyQuestionnaireService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;


    @ApiOperation(value = "问卷统计 答卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/answerList", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> answerList(@RequestParam("token") String token, String search) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("答卷列表：" + "token:" + token + ",search:" + search);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            List<AnswerDTO> answerDTOList = surveyQuestionnaireService.answerList(search);
            resultMap.put("status", 201);
            resultMap.put("message", answerDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "问卷统计 答卷详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "answerId", value = "答卷Id", dataType = "Integer", paramType = "query")})
    @RequestMapping(value = "/answerDetail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> answerDetail(@RequestParam("token") String token, @RequestParam("answerId") Integer answerId) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("答卷详情：" + "token:" + token + "answerId：" + answerId);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            AnswerDTO answerDetail = surveyQuestionnaireService.answerDetail(answerId);
            resultMap.put("status", 201);
            resultMap.put("message", answerDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "问卷统计 删除答卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "answerId", value = "答卷Id", dataType = "Integer", paramType = "query")})
    @RequestMapping(value = "/deleteAnswer", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> deleteAnswer(@RequestParam("token") String token, @RequestParam("answerId") Integer answerId) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("删除答卷：" + "token:" + token + "answerId：" + answerId);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            surveyQuestionnaireService.deleteAnswer(answerId);
            resultMap.put("status", 201);
            resultMap.put("message", "删除成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "问卷设置 添加修改问卷")
    @RequestMapping(value = "/addQuestionnaire", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addQuestionnaire(@RequestParam("token") String token, @RequestBody SurveyQuestionnaireDTO surveyQuestionnaireDTO) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("添加问卷：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            Integer id = surveyQuestionnaireService.addQuestionnaire(adminUser, surveyQuestionnaireDTO);
            resultMap.put("status", 201);
            resultMap.put("data", id);
            resultMap.put("message", "添加修改问卷成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "问卷设置 问卷列表")
    @RequestMapping(value = "/questionnaireList", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> questionnaireList(@RequestParam("token") String token, String search) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("问卷列表：" + "token:" + token + ",search:" + search);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            List<SurveyQuestionnaireListDTO> surveyQuestionnaireListDTOList =surveyQuestionnaireService.questionnaireList(search);
            resultMap.put("status", 201);
            resultMap.put("data", surveyQuestionnaireListDTOList);
            resultMap.put("message", "查询成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "问卷设置 问卷详情")
    @RequestMapping(value = "/questionnaireDetail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> questionnaireDetail(@RequestParam("token") String token, @RequestParam("questionnaireId") Integer questionnaireId) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("答卷详情：" + "token:" + token + "questionnaireId：" + questionnaireId);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            SurveyQuestionnaireDTO surveyQuestionnaireDTO = surveyQuestionnaireService.surveyQuestionnaireDetail(questionnaireId);
            resultMap.put("status", 201);
            resultMap.put("data", surveyQuestionnaireDTO);
            resultMap.put("message", "查询成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "问卷设置 删除问卷")
    @RequestMapping(value = "/deleteQuestionnaire", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> deleteQuestionnaire(@RequestParam("token") String token, @RequestParam("questionnaireId") Integer questionnaireId) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("删除问卷：" + "token:" + token + "questionnaireId：" + questionnaireId);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            surveyQuestionnaireService.deleteQuestionnaire(questionnaireId);
            resultMap.put("status", 201);
            resultMap.put("message", "删除成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }






















    @ApiOperation(value = "问卷系统 获取问卷列表")
    @RequestMapping(value = "/surveyQuestionnaireList", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> surveyQuestionnaireList(@RequestParam("token") String token) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("问卷列表：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            List<SurveyQuestionnaire> surveyQuestionnaireList = surveyQuestionnaireService.surveyQuestionnaireList();
            resultMap.put("status", 201);
            resultMap.put("message", surveyQuestionnaireList);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "问卷系统 问卷详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "surveyQuestionnaireId", value = "问卷Id", dataType = "Integer", paramType = "query")})
    @RequestMapping(value = "/surveyQuestionnaireDetail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> surveyQuestionnaireDetail(@RequestParam("token") String token, @RequestParam("surveyQuestionnaireId") Integer surveyQuestionnaireId) {
        try {
            Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
            log.info("问卷详情：" + "token:" + token + "id" + surveyQuestionnaireId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            SurveyQuestionnaireDTO surveyQuestionnaireDTO = surveyQuestionnaireService.surveyQuestionnaireDetail(surveyQuestionnaireId);
            resultMap.put("status", 201);
            resultMap.put("message", surveyQuestionnaireDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "问卷系统 提交答卷")
    @RequestMapping(value = "/submitQuestionnaire", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> submitQuestionnaire(@RequestBody AnswerDTO answerDTO) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("提交答卷");
            Integer surveyAnswerId = surveyQuestionnaireService.submit(answerDTO);
            resultMap.put("status", 201);
            resultMap.put("surveyAnswerId", surveyAnswerId);
            resultMap.put("message", "提交问卷成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "问卷系统 提交留言反馈")
    @RequestMapping(value = "/submitMessage", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> submitMessage(@RequestParam("token") String token, @RequestParam("answererId") Integer answererId, @RequestParam("message") String message) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("提交留言反馈");
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                resultMap.put("status", 201);
                resultMap.put("message", "找不到token");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }
            SurveyAnswer surveyAnswer = this.surveyAnswerRepository.getOne(answererId);
            if(surveyAnswer != null ){
                surveyQuestionnaireService.submitMessage(surveyAnswer,message);
            }else{
                resultMap.put("status", 201);
                resultMap.put("message", "用户答题表信息有误");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            }

            resultMap.put("status", 201);
            resultMap.put("message", "提交留言反馈成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




}

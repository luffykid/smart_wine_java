package com.changfa.frame.website.controller.survey;


import com.changfa.frame.data.dto.saas.AnswerDTO;
import com.changfa.frame.data.dto.saas.SurveyQuestionnaireDTO;
import com.changfa.frame.data.entity.survey.SurveyAnswer;
import com.changfa.frame.data.entity.survey.SurveyQuestionnaire;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.repository.survey.SurveyAnswerRepository;
import com.changfa.frame.service.survey.SurveyQuestionnaireService;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.client.JSON;
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
@Api(value = "问卷Controller", tags = {"问卷操作接口"})
public class SruveyQuestionnaireController {

    private static Logger log = LoggerFactory.getLogger(SruveyQuestionnaireController.class);

    @Autowired
    private SurveyQuestionnaireService surveyQuestionnaireService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;

    //问卷列表
    @ApiOperation(value = "获取问卷列表")
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

    //问卷详情
    @ApiOperation(value = "问卷详情")
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


    /*// 提交答卷
    @RequestMapping(value = "/submitQuestionnaire", method = RequestMethod.POST)
    public String submitQuestionnaire(@RequestBody AnswerDTO answerDTO) {
        try {
            log.info("提交答卷"*//* +  "answerDTO:" + answerDTO*//*);
            surveyQuestionnaireService.submit(answerDTO);
            return JsonReturnUtil.getJsonIntReturn(0, "", "提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }*/

    // 提交答卷
    @ApiOperation(value = "提交答卷")
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

    // 答卷列表
    @ApiOperation(value = "答卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/answerList", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> answerList(@RequestParam("token") String token, String search) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("答卷列表：" + "token:" + token + ",search:" + search);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
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

    // 答卷详情
    @ApiOperation(value = "答卷详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "answerId", value = "答卷Id", dataType = "Integer", paramType = "query")})
    @RequestMapping(value = "/answerDetail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> answerDetail(@RequestParam("token") String token, @RequestParam("answerId") Integer answerId) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("答卷详情：" + "token:" + token + "answerId：" + answerId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
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

    // 提交留言反馈
    @ApiOperation(value = "提交留言反馈")
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
            SurveyAnswer surveyAnswer = this.surveyAnswerRepository.findOne(answererId);
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

    // 删除答卷
    @ApiOperation(value = "删除答卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "answerId", value = "答卷Id", dataType = "Integer", paramType = "query")})
    @RequestMapping(value = "/deleteAnswer", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> deleteAnswer(@RequestParam("token") String token, @RequestParam("answerId") Integer answerId) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            log.info("删除答卷：" + "token:" + token + "answerId：" + answerId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
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


}

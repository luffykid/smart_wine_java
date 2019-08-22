package com.changfa.frame.website.controller.common;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MemberService;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器基类
 *
 * @author wyy
 * @date 2019年08月15日
 */
public abstract class BaseController {
    private static final long serialVersionUID = -6344078923170236539L;
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    /**
     * 应用接口异常处理
     *
     * @param response Response相应对象
     * @param ex
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse response, Exception ex) {
        Class eClass = ex.getClass();
        Map<String, Object> map = new HashMap<>();
        map.put(ResultUtil.RESULT_PARAM_NAME, ResultUtil.Result.FAIL.getValue());
        if (!eClass.equals(CustomException.class)) {
            log.error(ExceptionUtils.getStackTrace(ex));
        }
        //以下异常中，除注释过的，其他异常可不关注
        if (eClass.equals(MissingServletRequestParameterException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.MISS_PARAMETER, map);
        } else if (eClass.equals(TypeMismatchException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.TYPE_MIS_MATCH, map);
        } else if (eClass.equals(HttpMessageNotReadableException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.RESOURCE_NOT_READABLE, map);
        } else if (eClass.equals(HttpRequestMethodNotSupportedException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.METHOD_NOT_SUPPORTED, map);
        } else if (eClass.equals(HttpMediaTypeNotAcceptableException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.MEDIA_TYPE_NOT_ACCEPT, map);
        } else if (eClass.equals(HttpMediaTypeNotSupportedException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.MEDIA_TYPE_NOT_SUPPORTED, map);
        } else if (eClass.equals(ConversionNotSupportedException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.SERVER_ERROR, map);
        } else if (eClass.equals(HttpMessageNotWritableException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.SERVER_ERROR, map);
        } else if (eClass.equals(CustomException.class)) { //系统业务异常
            addExceptionToMap((CustomException) ex, map);
        } else if (eClass.equals(CustomException.class)) { //系统业务异常
            CustomException ce = (CustomException) ex;
            addExceptionToMap(new CustomException(ce.getErrorCode(), ce.getMessage()), map);
        } else {
            addResCodeToMap(RESPONSE_CODE_ENUM.SERVER_ERROR, map);
        }

        // 错误相应编码回写
        PrintWriter writer = null;
        try {
            response.setContentType("application/json; charset=UTF-8");
            writer = response.getWriter();
            writer.write(JSONObject.fromObject(map).toString());
            writer.flush();
        } catch (Exception e) {
            IOUtils.closeQuietly(writer);
            log.error("接口异常:{}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * 添加异常信息到map中
     *
     * @param responseCodeEnum 错误响应编码枚举类对象
     * @param map              响应错误编码集合
     */
    protected void addResCodeToMap(RESPONSE_CODE_ENUM responseCodeEnum, Map<String, Object> map) {
        map.put(ResultUtil.ERRORCODE_PARAM_NAME, responseCodeEnum.getCode());
        map.put(ResultUtil.ERRORMSG_PARAM_NAME, responseCodeEnum.getMsg());
    }

    /**
     * 添加异常信息到map中
     *
     * @param appInterfaceException 接口异常类
     * @param map                   接口异常集合
     */
    protected void addExceptionToMap(CustomException appInterfaceException, Map<String, Object> map) {
        map.put(ResultUtil.ERRORCODE_PARAM_NAME, appInterfaceException.getErrorCode());
        map.put(ResultUtil.ERRORMSG_PARAM_NAME, appInterfaceException.getErrorMsg());
    }

    /**
     * 添加数据到结果对象中
     *
     * @param obj 封装接口集合参数
     * @return
     */
    public Map<String, Object> getResult(Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put(ResultUtil.RESULT_PARAM_NAME, ResultUtil.Result.SUCCESS.getValue());
        map.put(ResultUtil.DATA_PARAM_NAME, obj);
        return map;
    }

    /**
     * 获取当前会员
     *
     * @param request 请求对象
     */
    public Member getCurMember(HttpServletRequest request) {

//        // 取出登陆账号
//        String headerAcctName = request.getHeader("request-header-acct");
//        if (StringUtils.isBlank(headerAcctName)) {
//            throw new CustomException(RESPONSE_CODE_ENUM.NOT_LOGIN_ERROR);
//        }
//
//        // 根据手机号查询会员
//        Member member = memberService.selectByPhone(headerAcctName);

          //  本地开发使用
        Member member = memberService.getById(1L);
        return member;
    }
}

package com.changfa.frame.website.interceptor;

import com.changfa.frame.core.redis.RedisClient;
import com.changfa.frame.core.redis.RedisConsts;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口令牌拦截器
 *
 * @author WYY
 * @version 1.0
 * @date 2019/08/9
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 接口Token令牌
     */
    public static final String REQUEST_HEADER_TOKEN_KEY = "request-header-token";

    /**
     * 接口(openID)
     */
    public static final String REQUEST_HEADER_OPENID_KEY = "request-header-openID";


    @Autowired
    private RedisClient redisClient;

    /**
     * 请求前预处理TOKEN判断
     * todo 仅做TOKEN简单校验，未做JWT签名及鉴权
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理对象
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("********** 进入登录拦截 **************");
        //        // 取出token
//        String headerToken = request.getHeader(REQUEST_HEADER_TOKEN_KEY);
//
//        // 取出登陆账号
//        String openId = request.getHeader(REQUEST_HEADER_OPENID_KEY);
//        if (StringUtils.isBlank(headerToken) || StringUtils.isBlank(openId)) {
//            throw new CustomException(RESPONSE_CODE_ENUM.NOT_WXMINI_LOGIN_ERROR);
//        }
//
//        // 获取redis中的token
//        Object redisToken = redisClient.get(RedisConsts.WXMINI_OPENID + openId);
//        if (redisToken == null) {
//            throw new CustomException(RESPONSE_CODE_ENUM.NOT_LOGIN_ERROR);
//        }
//
//        // token不相同则重新登陆
//        if (!StringUtils.equals(headerToken, String.valueOf(redisToken))) {
//            throw new CustomException(RESPONSE_CODE_ENUM.TOKEN_IS_NOT_EXIST);
//        }
//
//        // 重置token时间
//        redisClient.setAndExpire(RedisConsts.WXMINI_OPENID + openId, headerToken, RedisConsts.WXMINI_OPENID_EXPIRE);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}

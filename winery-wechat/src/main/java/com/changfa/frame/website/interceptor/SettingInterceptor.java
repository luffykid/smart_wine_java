package com.changfa.frame.website.interceptor;

import com.changfa.frame.service.mybatis.common.SettingUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SystemConfig对象拦截器
 *
 * @author wyy
 * @date 2019-08-26 02:17
 */
public class SettingInterceptor extends HandlerInterceptorAdapter {

    /**
     * setting拦截器
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SettingUtils.get();
    }
}

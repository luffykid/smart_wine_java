package com.changfa.frame.website.interceptor;

import com.changfa.frame.website.utils.SettingUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * setting拦截器
 */
public class SettingInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && modelAndView.isReference()) {
            ModelMap modelMap = modelAndView.getModelMap();
            if (modelMap != null && !modelMap.containsAttribute("setting")) {
                SettingUtils.get();
            }
        }
    }
}

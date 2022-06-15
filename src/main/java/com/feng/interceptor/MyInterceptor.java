package com.feng.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements AsyncHandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long requestStart = System.currentTimeMillis();
        request.setAttribute("startTime", requestStart);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求结束时间
        long requestEnd = System.currentTimeMillis();
        long startTime = (Long) request.getAttribute("startTime");
        long sub = requestEnd - startTime;
        request.removeAttribute("startTime");
        LOGGER.info("{}请求一共消耗{}毫秒,请求方式{}", request.getRequestURL(), sub, request.getMethod());
    }
}

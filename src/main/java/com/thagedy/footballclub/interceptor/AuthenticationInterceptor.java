package com.thagedy.footballclub.interceptor;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by thagedy on 2017/2/21.
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    boolean intercepterEnable = true;

    public boolean isIntercepterEnable() {
        return intercepterEnable;
    }

    public void setIntercepterEnable(boolean intercepterEnable) {
        this.intercepterEnable = intercepterEnable;
    }

    Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (!intercepterEnable){
            return true;
        }

        HttpSession session = httpServletRequest.getSession();
        Object loginUser = session.getAttribute("loginUser");
        String sessionId = session.getId();
        logger.info("------------------sessionId:"+sessionId+" -----------------------");
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        logger.info("--------------requestURL:"+requestURL+"--------------");
        String method = httpServletRequest.getMethod();
        logger.info("--------------------------------------method:"+method+"----------------------------------------");
        if (loginUser!=null){
            logger.info("-------------------------------loginUser:"+loginUser.toString()+"---------------------------");
            return true;
        }else {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            String json = JsonUtils.objectToJson(ClubResult.build(10002,"请登录",false));
            httpServletResponse.getWriter().print(json);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.thagedy.footballclub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tianqi Cui on 2016/9/19.
 * 解决测试环境联调时的跨域问题
 * 生产及外部测试环境已经不需要
 */

@Configuration
@EnableAutoConfiguration
public class CorConfiguration {

    @Value("${synapse.allowcors:false}")
    private boolean allowCors = false;

    @Bean
    FilterRegistrationBean corsFilter(@Value("${tagert.origin:*}") String origin) {

        return new FilterRegistrationBean(new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException {
                if (allowCors) {
                    HttpServletRequest request = (HttpServletRequest) req;
                    HttpServletResponse response = (HttpServletResponse) res;
                    String method = request.getMethod();
                    // this origin value could just as easily have come from a database
                    response.setHeader("Access-Control-Allow-Origin", origin);
                    response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
                    response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
                    //   response.setHeader("Access-Control-Allow-Credentials", "true");
                    response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                    if ("OPTIONS".equals(method)) {
                        response.setStatus(HttpStatus.OK.value());
                    } else {
                        chain.doFilter(req, res);
                    }
                } else {
                    chain.doFilter(req, res);
                }

            }

            public void init(FilterConfig filterConfig) {
            }

            public void destroy() {
            }
        });
    }

    public boolean isAllowCors() {
        return allowCors;
    }

    public void setAllowCors(boolean allowCors) {
        this.allowCors = allowCors;
    }
}

package cn.sitedev.browser.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义认证失败处理
 * @author: QChen
 * @create: 2018/4/18 0018
 **/
@Component
public class MyAuthFailureHandler implements AuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * ObjectMapper提供了读写JSON的功能
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 当认证失败时调用
     *
     * @param request   认证过程中的请求
     * @param response  响应
     * @param exception 拒绝认证请求时抛出的异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登陆失败");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        // objectMapper.writeValueAsString(exception):将AuthenticationException对象写成json格式的字符串
        response.getWriter().write(objectMapper.writeValueAsString(exception));
    }
}

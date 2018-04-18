package cn.sitedev.browser.auth;

import cn.sitedev.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义认证成功处理
 * @author: QChen
 * @create: 2018/4/18 0018
 **/
@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * ObjectMapper提供了读写JSON的功能
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 当用户被成功认证时调用
     *
     * @param request        引发成功认证的请求
     * @param response       响应
     * @param authentication 在认证过程中创建的Authentication对象
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Authentication :包含着用户认证信息
        logger.info("登陆成功");
        response.setContentType("application/json;charset=UTF-8");
        // objectMapper.writeValueAsString(authentication):将Authentication对象写成json格式的字符串
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}

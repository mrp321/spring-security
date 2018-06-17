package cn.sitedev.browser.controller;/**
 * @description
 * @auther QChen
 * @date 2018/4/16 0016
 */

import cn.sitedev.core.support.SocialUserInfo;
import cn.sitedev.core.support.SimpleResponse;
import cn.sitedev.core.properties.SecurityConstants;
import cn.sitedev.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 浏览器安全控制层
 * @author: QChen
 * @create: 2018/4/16 0016
 **/
@RestController
public class BrowserSecurityController {
    /**
     * 缓存的request
     */
    private RequestCache requestCache = new HttpSessionRequestCache();
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 默认重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 安全配置
     */
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * @param request  请求
     * @param response 响应
     * @return
     * @throws IOException
     * @description 当需要身份认证时, 跳转至这里
     * @author QChen
     * @date 2018/4/16 0016 21:54
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    // 返回401状态码
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取缓存的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            // 引发跳转请求的url
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引入跳转的请求是:" + targetUrl);
            // 如果url是以.html结尾
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                // 跳转至用户配置的loginPage页面中(security-demo的application.properties文件中的配置my.security.browser.loginPage)
                // 如果用户没有配置,则跳转至标准的登陆页面(security-browser的signIn.html)
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证,请引导用户到登陆页");
    }

    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        // 使用providerSignInUtils工具类,从session中取出connection
        // Q:connection何时被放入了session中?
        // A:SocialAuthenticationFilter在doAuthentication()方法中捕获到UsersConnectionRepository的findUserIdsWithConnection()中抛出的异常时
        Connection connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        socialUserInfo.setProviderId(connection.getKey().getProviderId());
        socialUserInfo.setProviderUserId(connection.getKey().getProviderUserId());
        socialUserInfo.setNickname(connection.getDisplayName());
        socialUserInfo.setHeadimg(connection.getImageUrl());
        return socialUserInfo;
    }

    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse sessionInvalid() {
        String msg = "session失效";
        return new SimpleResponse(msg);
    }

}

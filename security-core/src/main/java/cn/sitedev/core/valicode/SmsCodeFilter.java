package cn.sitedev.core.valicode;

import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.valicode.ValidateCode;
import cn.sitedev.core.valicode.ValidateCodeController;
import cn.sitedev.core.valicode.ValidateCodeException;
import cn.sitedev.core.valicode.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 短信验证码过滤器
 * @auther qchen
 * @date 2018/4/30
 */
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {
    /**
     * 认证失败处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler;
    /**
     * session策略
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * Ant风格匹配模式实现
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 存入需要拦截的url
     */
    private Set<String> urls = new HashSet<>();
    /**
     * 安全属性类
     */
    private SecurityProperties securityProperties;

    /**
     * 这个方法将在所有的属性被初始化后调用
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 将字符串用指定分隔符分隔为数组
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(), ",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
        urls.add("/auth/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean action = false;
        for (String url : urls) {
            // 如果配置的url和当前请求的uri匹配得上,就进行过滤
            if (antPathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }
        // 匹配上,进行过滤
        if (action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                // 如果捕获到异常 ,使用自定义失败处理器处理
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        // 不是登陆请求,验证码过滤器不做处理
        filterChain.doFilter(request, response);
    }

    /**
     * 对验证码进行验证
     *
     * @param request
     * @throws ServletRequestBindingException
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
package cn.sitedev.core.auth;

import cn.sitedev.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @description 抽象的安全配置类
 * @auther qchen
 * @date 2018/5/1
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 认证成功处理器
     */
    @Autowired
    protected AuthenticationSuccessHandler authenticationSuccessHandler;
    /**
     * 认证失败处理器
     */
    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                // 设置登录url
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                // 设置表单提交url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                // 认证成功处理器
                .successHandler(authenticationSuccessHandler)
                // 认证失败处理器
                .failureHandler(authenticationFailureHandler);
    }
}

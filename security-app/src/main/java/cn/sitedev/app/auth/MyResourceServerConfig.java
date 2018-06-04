package cn.sitedev.app.auth;

import cn.sitedev.core.auth.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.sitedev.core.properties.SecurityConstants;
import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.valicode.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @description 资源服务器配置
 * @auther qchen
 * @date 2018/5/31
 */
@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
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
    /**
     * 安全属性类
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 短信验证码验证安全配置类
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 社交安全配置类
     */
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 表单登录
        http.formLogin()
                // 设置登录url
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                // 设置表单提交url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                // 认证成功处理器
                .successHandler(authenticationSuccessHandler)
                // 认证失败处理器
                .failureHandler(authenticationFailureHandler);

        http
                // 应用短信验证码相关配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                // 应用社交安全相关配置
                // 配置的作用就是向Spring Security的过滤器链上添加一个过滤器,过滤器会拦截特定请求,引导用户进行社交登录
                .apply(mySocialSecurityConfig)
                .and()
                // 对请求进行授权
                .authorizeRequests()
                // 当访问该url时,不需要身份验证
                // 如果不加这个配置,浏览器访问登录页面时,会报错:"localhost 将您重定向的次数过多"
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getSignUpUrl(),
                        "/user/regist",
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        securityProperties.getBrowser().getSignOutUrl(),
                        "/signOutSuccess.html")
                .permitAll()
                // 任何请求
                .anyRequest()
                // 都进行授权认证
                .authenticated()
                .and()
                // 此处暂时关闭CSRF(跨站防护)
                // 如果不关闭,登陆成功后,会报下面的错误
                // Invalid CSRF Token 'null' was found on the request parameter '_csrf' or header 'X-CSRF-TOKEN'.
                .csrf().disable();

    }
}

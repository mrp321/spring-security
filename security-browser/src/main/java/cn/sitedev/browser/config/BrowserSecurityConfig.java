package cn.sitedev.browser.config;

import cn.sitedev.browser.session.MyExpiredSessionStrategy;
import cn.sitedev.core.auth.AbstractChannelSecurityConfig;
import cn.sitedev.core.auth.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.sitedev.core.properties.SecurityConstants;
import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.valicode.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;


/**
 * @author QChen
 * @description 浏览器安全配置类
 * @date 2018/4/13 0013
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    /**
     * 安全属性类
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 短信验证码验证安全配置类
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 验证码安全配置类
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    /**
     * 社交安全配置类
     */
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * token repository
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        //基于JDBC的持久登录令牌存储库实现
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        // 配置数据源
        tokenRepository.setDataSource(dataSource);
        // 启动时自动建表(建表后请将该行注释,否则会报错)
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 用户名密码登录相关配置
        applyPasswordAuthenticationConfig(http);
        // 应用校验码相关的配置
        http.apply(validateCodeSecurityConfig)
                .and()
                // 应用短信验证码相关配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                // 应用社交安全相关配置
                // 配置的作用就是向Spring Security的过滤器链上添加一个过滤器,过滤器会拦截特定请求,引导用户进行社交登录
                .apply(mySocialSecurityConfig)
                .and()
                // 记住我功能配置
                .rememberMe()
                //配置记住我的数据源
                .tokenRepository(persistentTokenRepository())
                //配置记住我的过期时间
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                //设置登录逻辑
                .userDetailsService(userDetailsService)
                .and()
                // session管理
                .sessionManagement()
                // session失效处理
                .invalidSessionStrategy(invalidSessionStrategy)
                // 最大session数量
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                // 当session数据达到最大时,阻止后面的登录行为
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                // session过期策略
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
                .logout()
                // 退出url,默认是/logout
                .logoutUrl("/signOut")
                // 退出成功url
//                .logoutSuccessUrl("/signOutSuccess.html")
                // 退出成功处理器
                // logoutSuccessUrl和logoutSuccessHandler两个互斥,最多只能配其中一个
                .logoutSuccessHandler(logoutSuccessHandler)
                // 退出后删除cookies
                .deleteCookies("JSESSIONID")
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
                // 只有有ADMIN权限的用户,才能访问/user/*
                .antMatchers(HttpMethod.GET, "/user/*").hasRole("ADMIN")
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

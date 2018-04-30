package cn.sitedev.browser.config;

import cn.sitedev.browser.auth.MyAuthFailureHandler;
import cn.sitedev.browser.auth.MyAuthSuccessHandler;
import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.valicode.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


/**
 * @author QChen
 * @description 浏览器安全配置类
 * @date 2018/4/13 0013
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 安全属性类
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 认证成功处理器
     */
    @Autowired
    private MyAuthSuccessHandler myAuthSuccessHandler;
    /**
     * 认证失败处理器
     */
    @Autowired
    private MyAuthFailureHandler myAuthFailureHandler;
    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;

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
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        // 设置认证失败处理器
        validateCodeFilter.setAuthenticationFailureHandler(myAuthFailureHandler);
        // 设置安全属性类
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();
        String loginPage = "/auth/require";
        String loginProcUrl = "/auth/form";
        // 在UsernamePasswordAuthenticationFilter前添加验证码过滤器
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //表单登陆
                .formLogin()
                // 自定义登陆页面
                .loginPage(loginPage)
                // 自定义认证用户名和密码的url
                // 即,客户在登录页面中按下"sign in"按钮时,要访问的url,与登录页的form action一致
                .loginProcessingUrl(loginProcUrl)
                // 自定义认证成功处理器
                .successHandler(myAuthSuccessHandler)
                // 自定义认证失败处理器
                .failureHandler(myAuthFailureHandler)
                .and()
                // 记住我功能配置
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                // 对请求进行授权
                .authorizeRequests()
                // 当访问该url时,不需要身份验证
                // 如果不加这个配置,浏览器访问登录页面时,会报错:"localhost 将您重定向的次数过多"
                .antMatchers(loginPage, securityProperties.getBrowser().getLoginPage(), "/code/*").permitAll()
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

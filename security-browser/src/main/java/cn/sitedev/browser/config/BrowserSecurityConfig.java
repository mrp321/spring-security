package cn.sitedev.browser.config;

import cn.sitedev.browser.auth.MyAuthFailureHandler;
import cn.sitedev.browser.auth.MyAuthSuccessHandler;
import cn.sitedev.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author QChen
 * @description 浏览器安全配置类
 * @date 2018/4/13 0013
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private MyAuthSuccessHandler myAuthSuccessHandler;
    @Autowired
    private MyAuthFailureHandler myAuthFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginPage = "/auth/require";
        String loginProcUrl = "/auth/form";
        //表单登陆
        http.formLogin()
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
                // 对请求进行授权
                .authorizeRequests()
                // 当访问该url时,不需要身份验证
                // 如果不加这个配置,浏览器访问登录页面时,会报错:"localhost 将您重定向的次数过多"
                .antMatchers(loginPage, securityProperties.getBrowser().getLoginPage()).permitAll()
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

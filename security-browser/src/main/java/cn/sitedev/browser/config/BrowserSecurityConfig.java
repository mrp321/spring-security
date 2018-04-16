package cn.sitedev.browser.config;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单登陆
        http.formLogin()
                // 自定义登陆页面
                .loginPage("/signIn.html")
                .and()
                // 对请求进行授权
                .authorizeRequests()
                // 当访问该url时,不需要身份验证
                // 如果不加这个配置,浏览器访问signIn.html页面时,会报错:"localhost 将您重定向的次数过多"
                .antMatchers("/signIn.html").permitAll()
                // 任何请求
                .anyRequest()
                // 都进行授权认证
                .authenticated();
    }
}

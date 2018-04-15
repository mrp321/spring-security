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
                .and()
                // 对请求进行授权
                .authorizeRequests()
                // 任何请求
                .anyRequest()
                // 都进行授权认证
                .authenticated();

//        // 基础验证
//        http.httpBasic()
//                .and()
//                // 对请求进行授权
//                .authorizeRequests()
//                // 任何请求
//                .anyRequest()
//                // 都进行授权认证
//                .authenticated();
    }
}

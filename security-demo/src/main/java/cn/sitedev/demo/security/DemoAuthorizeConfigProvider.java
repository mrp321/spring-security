package cn.sitedev.demo.security;

import cn.sitedev.core.authorize.AuthorizeConfigProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @description Demo授权配置提供者
 * @auther qchen
 * @date 2018/6/25
 */
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers(HttpMethod.GET, "/user/*")
                .hasRole("OTHER")
                // 测试对静态页面进行授权
                .antMatchers("/demo.html")
                .hasRole("XXX");
    }
}

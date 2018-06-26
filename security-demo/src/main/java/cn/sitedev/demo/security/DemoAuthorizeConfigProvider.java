package cn.sitedev.demo.security;

import cn.sitedev.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
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
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers(HttpMethod.GET, "/user/*")
                .hasRole("OTHER")
                // 测试对静态页面进行授权
                .antMatchers("/demo.html")
                .hasRole("XXX")

                // 因为anyRequest().access("....")是针对所有请求的,因此应该放在配置的最后生效,所以在该类上添加注解@Order(Integer.MAX_VALUE)
                .anyRequest()
                // rbacService:RbacServiceImpl组件在spring容器中的名字
                // hasPermission(request, authentication):调用RbacServiceImpl中的方法以及方法参数
                .access("@rbacService.hasPermission(request, authentication)");
    }
}

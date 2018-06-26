package cn.sitedev.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 授权配置管理者接口(收集所有的AuthorizeConfigProvider)
 * @auther qchen
 * @date 2018/6/25
 */
@Component
public class MyAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
        // 除了上面的配置外,其他的所有请求都需要经过授权认证
        // 由于这里的.anyRequest().authenticated()配置会覆盖掉DemoAuthorizeConfigProvider类中.anyRequest().access("@rbacService.hasPermission(request, authentication)");的配置,因此需要暂时将这个配置注释掉
//        config.anyRequest().authenticated();
    }
}

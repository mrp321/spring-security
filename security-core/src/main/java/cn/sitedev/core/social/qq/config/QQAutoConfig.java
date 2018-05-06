package cn.sitedev.core.social.qq.config;

import cn.sitedev.core.properties.QQProperties;
import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @description QQ自动配置类
 * @auther qchen
 * @date 2018/5/2
 */
@Configuration
// 仅在配置文件中指定配置项被配置了(有值),该配置类才起作用
// prefix:配置项的前缀
// name:配置项的名称
@ConditionalOnProperty(prefix = "my.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();

        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
    }
}

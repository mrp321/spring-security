package cn.sitedev.core.social.weibo.config;

import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.properties.WeiboProperties;
import cn.sitedev.core.social.weibo.connect.WeiboConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @description
 * @auther qchen
 * @date 2018/5/7
 */
@Configuration
@ConditionalOnProperty(prefix = "my.security.social.weibo", name = "app-id")
public class WeiboAutoConfig extends SocialAutoConfigurerAdapter {

    /**
     * 安全属性类
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiboProperties weiboProperties = securityProperties.getSocial().getWeibo();
        return new WeiboConnectionFactory(weiboProperties.getProviderId(), weiboProperties.getAppId(), weiboProperties.getAppSecret());
    }
}

package cn.sitedev.core.social.weixin.config;

import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.properties.WeixinProperties;
import cn.sitedev.core.social.MyConnectView;
import cn.sitedev.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @description 微信登录配置
 * @auther qchen
 * @date 2018/5/6
 */
@Configuration
@ConditionalOnProperty(prefix = "my.security.social.weixin", name = "app-id")
public class WeixinAutoConfig extends SocialAutoConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
     * #createConnectionFactory()
     */
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinProperties = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinProperties.getProviderId(), weixinProperties.getAppId(),
                weixinProperties.getAppSecret());
    }

    /**
     * 绑定:'connect/' + providerId + 'Connected'
     * 解绑:'connect/' + providerId + 'Connect'
     * @return
     */
    @Bean({"connect/weixinConnected", "connect/weixinConnect"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new MyConnectView();
    }
}

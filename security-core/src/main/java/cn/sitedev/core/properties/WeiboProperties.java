package cn.sitedev.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @description 新浪微博属性类
 * @auther qchen
 * @date 2018/5/7
 */
// 继承SocialProperties类,可以获取appId和appSecret
public class WeiboProperties extends SocialProperties {
    /**
     * 服务提供商id
     */
    private String providerId = "weibo";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}

package cn.sitedev.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @description QQ属性类
 * @auther qchen
 * @date 2018/5/2
 */
public class QQProperties extends SocialProperties {
    /**
     * 服务提供商id
     */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}

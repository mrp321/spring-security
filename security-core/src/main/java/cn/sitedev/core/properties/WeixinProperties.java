package cn.sitedev.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @description 微信属性类
 * @auther qchen
 * @date 2018/5/6
 */
public class WeixinProperties extends SocialProperties {

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";

    /**
     * @return the providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}

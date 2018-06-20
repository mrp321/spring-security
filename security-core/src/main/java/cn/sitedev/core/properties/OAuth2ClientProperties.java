package cn.sitedev.core.properties;

/**
 * @description OAuth2客户端属性类
 * @auther qchen
 * @date 2018/6/19
 */
public class OAuth2ClientProperties {
    /**
     * clientId
     */
    private String clientId;
    /**
     * client secret
     */
    private String clientSecret;
    /**
     * 有效期(单位:s)
     */
    private int accessTokenValiditySeconds;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}

package cn.sitedev.core.properties;

/**
 * @description OAuth2属性类
 * @auther qchen
 * @date 2018/6/19
 */
public class OAuth2Properties {
    /**
     * 签名密钥
     */
    private String jwtSigningKey = "mySigningKey";

    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}

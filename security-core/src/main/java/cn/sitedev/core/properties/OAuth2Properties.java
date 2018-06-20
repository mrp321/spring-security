package cn.sitedev.core.properties;

/**
 * @description OAuth2属性类
 * @auther qchen
 * @date 2018/6/19
 */
public class OAuth2Properties {
    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}

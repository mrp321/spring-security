package cn.sitedev.core.social.qq.connect;

import cn.sitedev.core.social.qq.api.QQ;
import cn.sitedev.core.social.qq.api.impl.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @description QQ服务提供商
 * @auther qchen
 * @date 2018/5/2
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    private String appId;
    /**
     * 获取Authorization Code的url
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 通过Authorization Code获取Access Token的url
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {
        // 使用默认的OAuth2Template
        // OAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl)
        // clientId:即QQ的appId
        // clientSecret:即QQ的appSecret
        // authorizeUrl: 第三方应用将用户导向认证服务器时的url
        // accessTokenUrl:第三方应用申请令牌时的url
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}

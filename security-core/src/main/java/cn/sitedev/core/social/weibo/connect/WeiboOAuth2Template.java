package cn.sitedev.core.social.weibo.connect;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;

import java.util.Map;

/**
 * @description 完成新浪微博OAuth2认证流程模板类
 * @auther qchen
 * @date 2018/5/7
 */
public class WeiboOAuth2Template extends OAuth2Template {
    public WeiboOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        this.setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        return new WeiboAccessGrant(accessToken, scope, refreshToken, expiresIn, (String) response.get("uid"));
    }
}

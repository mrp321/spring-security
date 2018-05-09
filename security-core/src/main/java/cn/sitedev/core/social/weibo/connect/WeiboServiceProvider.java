package cn.sitedev.core.social.weibo.connect;

import cn.sitedev.core.social.weibo.api.Weibo;
import cn.sitedev.core.social.weibo.api.WeiboImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * @description 新浪微博服务提供商
 * @auther qchen
 * @date 2018/5/7
 */
public class WeiboServiceProvider extends AbstractOAuth2ServiceProvider<Weibo> {
    /**
     * 微博授权用户id
     */
    private String uid;
    /**
     * OAuth2的authorize接口
     */
    private static final String URL_AUTHORIZE = "https://api.weibo.com/oauth2/authorize";
    /**
     * OAuth2的access_token接口
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token";

    public WeiboServiceProvider(String appkey, String appSecret) {
        super(new WeiboOAuth2Template(appkey, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    }

    public WeiboServiceProvider(String uid) {
        super(null);
        this.uid = uid;
    }

    @Override
    public Weibo getApi(String accessToken) {
        return new WeiboImpl(accessToken, uid);
    }

}

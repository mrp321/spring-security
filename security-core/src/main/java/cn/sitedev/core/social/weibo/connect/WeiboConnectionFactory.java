package cn.sitedev.core.social.weibo.connect;

import cn.sitedev.core.social.weibo.api.Weibo;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @description 新浪微博连接工厂
 * @auther qchen
 * @date 2018/5/7
 */
public class WeiboConnectionFactory extends OAuth2ConnectionFactory<Weibo> {
    private String uid;

    public WeiboConnectionFactory(String providerId, String appKey, String appSecret) {
        super(providerId, new WeiboServiceProvider(appKey, appSecret), new WeiboAdapter());
    }

    /**
     * 从新浪微博AccessGrant中获取授权用户的uid作为服务提供商用户id
     *
     * @param accessGrant
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        String uid = ((WeiboAccessGrant) accessGrant).getUid();
        this.uid = uid;
        return uid;
    }

    @Override
    public Connection<Weibo> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<Weibo>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), new WeiboServiceProvider(uid), getApiAdapter());
    }
}

package cn.sitedev.core.social.weibo.connect;

import org.springframework.social.oauth2.AccessGrant;

/**
 * @description 新浪微博Access Token 信息的封装
 * 在标准AccessToken中, 包含accessToken,scope,refreshToken,expireTime(expiresIn)4个参数{@link AccessGrant}
 * 而在微博的AccessToken中,应该包含accessToken,expiresIn,remindIn(即将废除,请使用expiresIn),uid这4个参数,因此,需要重新封装AccessToken
 * @auther qchen
 * @date 2018/5/8
 */
public class WeiboAccessGrant extends AccessGrant {

    /**
     * 授权用户的id
     */
    private String uid;

    public WeiboAccessGrant(String accessToken) {
        super(accessToken);
    }

    public WeiboAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String uid) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
package cn.sitedev.core.social.weibo.connect;

import cn.sitedev.core.social.weibo.api.Weibo;
import cn.sitedev.core.social.weibo.api.WeiboUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @description 新浪微博 api适配器，将新浪微博api的数据模型转为spring social的标准模型。
 * @auther qchen
 * @date 2018/5/7
 */
public class WeiboAdapter implements ApiAdapter<Weibo> {
    @Override
    public boolean test(Weibo api) {
        return true;
    }

    @Override
    public void setConnectionValues(Weibo api, ConnectionValues values) {
        WeiboUserInfo weiboUserInfo = api.getUserInfo();
        values.setProviderUserId(weiboUserInfo.getIdstr());
        values.setProfileUrl(weiboUserInfo.getProfile_url());
        values.setImageUrl(weiboUserInfo.getAvatar_hd());
        values.setDisplayName(weiboUserInfo.getName());

    }

    @Override
    public UserProfile fetchUserProfile(Weibo api) {
        return null;
    }

    @Override
    public void updateStatus(Weibo api, String message) {

    }
}

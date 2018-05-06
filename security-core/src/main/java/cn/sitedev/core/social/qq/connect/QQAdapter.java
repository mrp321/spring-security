package cn.sitedev.core.social.qq.connect;

import cn.sitedev.core.social.qq.api.QQ;
import cn.sitedev.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @description 用于在Connection和Api间进行适配的类
 * @auther qchen
 * @date 2018/5/2
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试当前Api是否可用
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        // 这里设置为true,表示可用
        return true;
    }

    /**
     * 给ConnectionValues设值
     *
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        // 获取用户信息
        QQUserInfo userInfo = api.getUserInfo();
        // 显示名称
        values.setDisplayName(userInfo.getNickname());
        // 用户头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // 个人主页
        values.setProfileUrl(null);
        // 服务商的userId(即用户在服务商的唯一标识,这里即QQ的openid)
        values.setProviderUserId(userInfo.getOpenId());
    }

    /**
     * 获取标准结构的用户信息
     *
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        // 什么都不做
    }
}

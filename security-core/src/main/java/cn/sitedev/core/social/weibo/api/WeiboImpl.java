package cn.sitedev.core.social.weibo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @description 新浪微博接口具体实现
 * @auther qchen
 * @date 2018/5/7
 */
public class WeiboImpl extends AbstractOAuth2ApiBinding implements Weibo {
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 授权用户的UID
     */
    private String uid;
    /**
     * 根据用户ID获取用户信息
     */
    private static final String URL_USERS_SHOW = "https://api.weibo.com/2/users/show.json?uid=%s";

    public WeiboImpl(String accessToken, String uid) {
        // 将accessToken作为一个查询参数
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.uid = uid;
    }

    @Override
    public WeiboUserInfo getUserInfo() {
        // 由于在WeiboImpl构造器中设置为将access token作为一个查询参数(TokenStrategy.ACCESS_TOKEN_PARAMETER),因此,这里不用为access token设值
        String url = String.format(URL_USERS_SHOW, uid);
        // 发送get请求,返回一个字符串
        String result = getRestTemplate().getForObject(url, String.class);
        // 将json格式的字符串转为WeiboUserInfo对象
        WeiboUserInfo userInfo;
        try {
            userInfo = objectMapper.readValue(result, WeiboUserInfo.class);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取微博用户信息失败,失败信息:" + e.getMessage());
        }
    }
}

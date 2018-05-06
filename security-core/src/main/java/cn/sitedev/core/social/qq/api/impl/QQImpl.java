package cn.sitedev.core.social.qq.api.impl;

import cn.sitedev.core.social.qq.api.QQ;
import cn.sitedev.core.social.qq.api.QQUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @description QQ实现类
 * @auther qchen
 * @date 2018/5/1
 */
// 不能使用@Component注解,因为这个类是多实例的类,每次需要的时候,都会创建
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    /**
     * 用access token 获取openid的url
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openId;
    /**
     * 用于读写json的工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        // TokenStrategy.ACCESS_TOKEN_PARAMETER:将access token作为一个查询参数
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        // 将URL_GET_OPENID中的%s替换成accessToken
        String url = String.format(URL_GET_OPENID, accessToken);
        // 发送get请求,返回一个字符串
        //请求返回字符串示例: "callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );"
        // 详情见:http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        // 从返回的字符串中截取出openid
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public QQUserInfo getUserInfo() {
        // 由于在QQImpl构造器中设置为将access token作为一个查询参数,因此,这里不用为access token设值
        String url = String.format(URL_GET_USERINFO, appId, openId);
        // 发送get请求,返回一个字符串
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        // 将json格式的字符串转为QQUserInfo对象
        QQUserInfo userInfo;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败,失败信息:" + e.getMessage());
        }
    }
}

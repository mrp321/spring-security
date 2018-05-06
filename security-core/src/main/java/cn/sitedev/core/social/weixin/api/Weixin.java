package cn.sitedev.core.social.weixin.api;

/**
 * @description 微信API调用接口
 * @auther qchen
 * @date 2018/5/6
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}

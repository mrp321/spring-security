package cn.sitedev.core.properties;

/**
 * @description 社交属性类
 * @auther qchen
 * @date 2018/5/2
 */
public class SocialProperties {
    /**
     * 登录处理url
     */
    private String filterProcessesUrl = "/auth";
    /**
     * QQ属性类
     */
    private QQProperties qq = new QQProperties();
    /**
     * 微信属性类
     */
    private WeixinProperties weixin = new WeixinProperties();
    /**
     * 新浪微博属性类
     */
    private WeiboProperties weibo = new WeiboProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }

    public WeiboProperties getWeibo() {
        return weibo;
    }

    public void setWeibo(WeiboProperties weibo) {
        this.weibo = weibo;
    }
}

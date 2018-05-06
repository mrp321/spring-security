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
}

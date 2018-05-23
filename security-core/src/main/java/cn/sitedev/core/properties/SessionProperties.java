package cn.sitedev.core.properties;

/**
 * @description session属性类
 * @auther qchen
 * @date 2018/5/23
 */
public class SessionProperties {
    /**
     * 同一个用户在系统中的最大session数,默认为1
     */
    private int maximumSessions = 1;
    /**
     * 达到最大session时是否阻止新的登录请求,默认为false,不阻止,新的登录会使老的登录失效
     */
    private boolean maxSessionsPreventsLogin;
    /**
     * session失效时跳转的url
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean isMaxSessionsPreventsLogin() {
        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }
}

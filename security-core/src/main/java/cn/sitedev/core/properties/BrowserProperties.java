package cn.sitedev.core.properties;

/**
 * @description: 浏览器属性类
 * @author: QChen
 * @create: 2018/4/16 0016
 **/
public class BrowserProperties {
    /**
     * 登录页(这里指定默认的登录页)
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    /**
     * 登陆类型(默认返回json)
     */
    private LoginType loginType = LoginType.JSON;
    /**
     * "记住我"时间长度(默认设置3600s)
     */
    private int rememberMeSeconds = 3600;
    /**
     * 默认注册页
     */
    private String signUpUrl = "/signUp.html";
    /**
     * session属性类
     */
    private SessionProperties session  = new SessionProperties();

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }
}

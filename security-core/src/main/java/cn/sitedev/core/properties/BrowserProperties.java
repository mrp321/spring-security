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
    private String loginPage = "/signIn.html";
    /**
     * 登陆类型(默认返回json)
     */
    private LoginType loginType = LoginType.JSON;
    /**
     * "记住我"时间长度(默认设置3600s)
     */
    private int rememberMeSeconds = 3600;

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
}

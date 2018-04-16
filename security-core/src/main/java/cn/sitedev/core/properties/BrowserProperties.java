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

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}

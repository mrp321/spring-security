package cn.sitedev.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 安全属性类
 * @author: QChen
 * @create: 2018/4/16 0016
 **/
// 把同类的配置信息自动封装成实体类
@ConfigurationProperties(prefix = "my.security")
public class SecurityProperties {
    /**
     * 浏览器属性类
     */
    // 由于在security-demo的application.properties中是这样配置的:my.security.browser.loginPage
    // 其中,'my.security'是@ConfigurationProperties注解的prefix属性值,'browser'是BrowserProperties实例名,loginPage是BrowserProperties类中的属性名
    private BrowserProperties browser = new BrowserProperties();
    /**
     * 验证码属性类
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}

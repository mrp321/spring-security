package cn.sitedev.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @description 将SocialAuthenticationFilter添加进过滤器链的配置类
 * @auther qchen
 * @date 2018/5/3
 */
public class MySpringSocialConfigurer extends SpringSocialConfigurer {
    /**
     * 登录处理url
     */
    private String filterProcessesUrl;

    public MySpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 这里覆写postProcess方法,用于自定义Spring Social的默认登录处理url
     *
     * @param object
     * @param <T>
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        // 这里重新设置下SocialAuthenticationFilter的filterProcessesUrl,使用自定义的
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}

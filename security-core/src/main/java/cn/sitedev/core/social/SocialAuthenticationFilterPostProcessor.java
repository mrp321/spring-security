package cn.sitedev.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @description 社交认证过滤器后处理器接口
 * @auther qchen
 * @date 2018/6/7
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
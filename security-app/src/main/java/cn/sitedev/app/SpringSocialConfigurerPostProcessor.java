package cn.sitedev.app;

import cn.sitedev.core.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @description SpringSocial配置器后处理器
 * @auther qchen
 * @date 2018/6/13
 */
@Component
// 实现BeanPostProcessor接口的作用:在spring容器中所有bean 初始化之前和初始化之后都经过这个类中的方法
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 在使用App模块时,在mySocialSecurityConfig(该bean在security-core项目的SocialConfig类中配置)初始化以后,修改其注册页面
        if (StringUtils.equals(beanName, "mySocialSecurityConfig")) {
            MySpringSocialConfigurer configurer = (MySpringSocialConfigurer) bean;
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}

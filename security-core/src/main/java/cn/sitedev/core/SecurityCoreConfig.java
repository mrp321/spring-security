package cn.sitedev.core;

import cn.sitedev.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * /**
 *
 * @description: 配置类
 * @author: QChen
 * @create: 2018/4/16 0016
 **/
@Configuration
// 为带有@ConfigurationProperties注解的Bean提供有效的支持
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}

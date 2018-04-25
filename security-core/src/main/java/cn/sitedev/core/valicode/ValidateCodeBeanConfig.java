package cn.sitedev.core.valicode;

import cn.sitedev.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 验证码生成器Bean配置类
 * @author: QChen
 * @create: 2018/4/26 0026
 **/
@Configuration
public class ValidateCodeBeanConfig {
    /**
     * 安全属性类
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    // 初始化bean之前会先在spring容器中寻找是否有一个叫做imageCodeGenerator的bean
    // 如果能够找到,就使用找到的那个bean
    // 如果找不到,就使用这个bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }
}

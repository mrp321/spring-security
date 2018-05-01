package cn.sitedev.core.valicode.sms;

import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.core.valicode.ValidateCode;
import cn.sitedev.core.valicode.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @description: 短信验证码生成器
 * @author: QChen
 * @create: 2018/4/26 0026
 **/
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
    /**
     * 安全属性类
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 生成图片验证码
     *
     * @param request
     * @return
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }

}

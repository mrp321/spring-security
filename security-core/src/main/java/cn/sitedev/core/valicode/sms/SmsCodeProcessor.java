package cn.sitedev.core.valicode.sms;

import cn.sitedev.core.valicode.ValidateCode;
import cn.sitedev.core.valicode.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.Servlet;

/**
 * 短信验证码处理器
 *
 * @auther qchen
 * @date 2018/4/30
 */
@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 发送验证码
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobileNo");
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}

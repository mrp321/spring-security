package cn.sitedev.core.valicode.sms;

/**
 * 短信验证码发送商接口
 *
 * @author QChen
 * @date 2018-4-26
 */
public interface SmsCodeSender {
    /**
     * 短信验证码发送
     *
     * @param mobileNo 手机号
     * @param code     验证码
     */
    void send(String mobileNo, String code);
}

package cn.sitedev.core.valicode.sms;

/**
 * @description: 默认短信验证码发送商实现
 * @author: QChen
 * @create: 2018/4/26 0026
 **/
public class DefaultSmsCodeSender implements SmsCodeSender {
    /**
     * 发送短信验证码
     *
     * @param mobileNo 手机号
     * @param code     验证码
     */
    @Override
    public void send(String mobileNo, String code) {
        System.out.println("向手机号" + mobileNo + "发送验证码" + code);
    }
}

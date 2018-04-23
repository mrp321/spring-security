package cn.sitedev.core.valicode;


import org.springframework.security.core.AuthenticationException;

/**
 * @description: 验证码异常类
 * @author: QChen
 * @create: 2018/4/19 0019
 **/
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}

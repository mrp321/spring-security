package cn.sitedev.app.social.exception;

/**
 * @description App安全异常
 * @auther qchen
 * @date 2018/6/13
 */
public class AppSecretException extends RuntimeException {
    public AppSecretException(String message) {
        super(message);
    }
}

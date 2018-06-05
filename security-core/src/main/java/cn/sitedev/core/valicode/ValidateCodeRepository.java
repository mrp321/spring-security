package cn.sitedev.core.valicode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description 验证码存储库
 * @auther qchen
 * @date 2018/6/5
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     *
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     *
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     *
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);

}

package cn.sitedev.core.valicode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证码生成器
 * @author: QChen
 * @create: 2018/4/26 0026
 **/
public interface ValidateCodeGenerator {
    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}

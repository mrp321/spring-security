package cn.sitedev.core.valicode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 功能描述：校验码处理器，封装不同检验码的处理逻辑
 *
 * @author qchen
 * @date 2018/4/30
 */

public interface ValidateCodeProcessor {
    /**
     * 验证码放入sesssion时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";


    /**
     * 创建验证码
     *
     * @param request 请求
     * @throws Exception
     */
    // ServletWebRequest:spring的工具类,封装请求和响应
    void create(ServletWebRequest request) throws Exception;
}

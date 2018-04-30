package cn.sitedev.core.valicode.impl;

import cn.sitedev.core.valicode.ValidateCode;
import cn.sitedev.core.valicode.ValidateCodeGenerator;
import cn.sitedev.core.valicode.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 功能描述：校验码处理器，封装不同检验码的处理逻辑
 *
 * @author qchen
 * @date 2018/4/30
 */
// 用到了spring的依赖查找
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 收集系统中所有的{@link ValidateCodeGenerator}接口的实现
     */

    // 当spring看到这样的一个map后,会在启动时查找容器中所有ValidateCodeGenerator接口的实现
    // 找到后,将相应的bean以bean的名字为key存入map中
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 创建校验码
     *
     * @param request
     */
    public void create(ServletWebRequest request) throws Exception {
        // 生成
        C validateCode = generate(request);
        // 保存
        save(request, validateCode);
        // 发送
        send(request, validateCode);

    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getProcessType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessType(request).toUpperCase(), validateCode);
    }


    /**
     * 发送校验码,由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private String getProcessType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

}

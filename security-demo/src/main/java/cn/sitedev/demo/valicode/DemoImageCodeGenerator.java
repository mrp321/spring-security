package cn.sitedev.demo.valicode;

import cn.sitedev.core.valicode.ImageCode;
import cn.sitedev.core.valicode.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: Demo项目图片验证码生成器
 * @author: QChen
 * @create: 2018/4/26 0026
 **/
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode generate(ServletWebRequest request) {
        // 这里仅是简单测试一下,没有具体的图片验证码生成逻辑
        // 当调用验证码生成接口的时候,会报空指针异常
        System.out.println("Demo项目的图片验证码生成器");
        return null;
    }
}

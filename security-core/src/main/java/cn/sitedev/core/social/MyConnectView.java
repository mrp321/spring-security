package cn.sitedev.core.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description 绑定/解绑视图
 * @auther qchen
 * @date 2018/5/16
 */
public class MyConnectView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (model.get("connection") == null) {
            response.getWriter().write("<h3>解绑成功</h3>");
        } else {
            response.getWriter().write("<h3>绑定成功</h3>");
        }

    }
}

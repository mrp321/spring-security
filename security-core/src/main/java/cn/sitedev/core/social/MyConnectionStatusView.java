package cn.sitedev.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 社交账号绑定状态视图
 * @auther qchen
 * @date 2018/5/15
 */
@Component("connect/status")
public class MyConnectionStatusView extends AbstractView {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
        Map<String, Boolean> result = new HashMap<>();
        // 服务提供商的绑定状态并不是看当前userconnection表中有几条数据
        // 而是看当前登录的用户的userId和userconnection表中userId匹配的数据
        // 注意:认证成功后,放入session中的UserDetails一定得是SocialUserDetails,因为这样,才能根据userId在数据库中查出用户信息
        for (String key : connections.keySet()) {
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }
        // 添加成功标识
        result.put("success", true);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}

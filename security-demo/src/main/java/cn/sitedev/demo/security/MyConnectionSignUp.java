package cn.sitedev.demo.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @description 用户自定义注册
 * @auther qchen
 * @date 2018/5/6
 */
@Component
public class MyConnectionSignUp implements ConnectionSignUp {
    /**
     * 该方法会在JdbcUsersConnectionRepository的findUserIdsWithConnection方法中被调用
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        // 根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }
}

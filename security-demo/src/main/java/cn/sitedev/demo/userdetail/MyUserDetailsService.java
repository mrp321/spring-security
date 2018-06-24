package cn.sitedev.demo.userdetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义用户认证逻辑的类
 * @author: QChen
 * @create: 2018/4/15 0015
 **/
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登陆用户名" + username);
        return buildUser(username);

    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("社交登陆用户id" + userId);
        // 密码加密
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        String password = passwordEncoder.encode("123456");
        logger.info("登陆密码[加密]" + password);
        // 1. 根据用户名查找社交用户信息
        // User(String username, String password,  Collection<? extends GrantedAuthority> authorities)
        // username : 用户名
        // password : 密码
        // authorities : 用户权限集合
        // AuthorityUtils.commaSeparatedStringToAuthorityList(String authorityString): 将以逗号分隔的权限字符串转为权限集合
        return new SocialUser(userId, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER"));
    }
}

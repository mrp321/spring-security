package cn.sitedev.browser.userdetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义用户认证逻辑的类
 * @author: QChen
 * @create: 2018/4/15 0015
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登陆用户名" + username);
        // 1. 根据用户名查找用户信息
        // User(String username, String password,  Collection<? extends GrantedAuthority> authorities)
        // username : 用户名
        // password : 密码
        // authorities : 用户权限集合
        // AuthorityUtils.commaSeparatedStringToAuthorityList(String authorityString): 将以逗号分隔的权限字符串转为权限集合
        // return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        // 2. 根据查找到的用户信息判断用户是否被冻结
        // User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
        // username : 用户名
        // password : 密码
        // enabled : 是否被禁用
        // accountNonExpired : 账号是否过期
        // credentialsNonExpired : 密码是否过期
        // accountNonLocked : 账号是否锁定
        // authorities : 用户权限集合
        return new User(username, "123456", true, true, true, false, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}

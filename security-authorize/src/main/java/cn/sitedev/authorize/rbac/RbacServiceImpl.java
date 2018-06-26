package cn.sitedev.authorize.rbac;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @description Rbac service(用于将自己开发的权限模块和Spring Security进行对接)
 * @auther qchen
 * @date 2018/6/26
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principle = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principle instanceof UserDetails) {
            String username = ((UserDetails) principle).getUsername();
            // 读取用户所拥有的权限的所有的url
            // 这里应该读取数据库中的数据(根据用户读取权限表,再根据读取出来的权限读取资源表)
            Set<String> urls = new HashSet<>();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}

package cn.sitedev.authorize.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @description
 * @auther qchen
 * @date 2018/6/26
 */
public interface RbacService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}

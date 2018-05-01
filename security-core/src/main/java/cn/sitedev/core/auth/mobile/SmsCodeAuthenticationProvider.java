package cn.sitedev.core.auth.mobile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @description 短信验证码认证提供器
 * @auther qchen
 * @date 2018/4/30
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private UserDetailsService userDetailsService;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //验证用户权限
        check(user);
        SmsCodeAuthenticationToken authResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authResult.setDetails(authToken.getDetails());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 账号信息权限验证
     *
     * @param user
     */
    private void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            logger.info("User account is locked");
            throw new LockedException(SmsCodeAuthenticationProvider.this.messages.getMessage("SmsCodeAuthenticationProvider.locked", "User account is locked"));
        } else if (!user.isEnabled()) {
            logger.info("User account is disabled");
            throw new DisabledException(SmsCodeAuthenticationProvider.this.messages.getMessage("SmsCodeAuthenticationProvider.disabled", "User is disabled"));
        } else if (!user.isAccountNonExpired()) {
            logger.info("User account is expired");
            throw new AccountExpiredException(SmsCodeAuthenticationProvider.this.messages.getMessage("SmsCodeAuthenticationProvider.expired", "User account has expired"));
        }
        if (!user.isCredentialsNonExpired()) {
            logger.info("User account credentials have expired");
            throw new CredentialsExpiredException(SmsCodeAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
        }
    }
}

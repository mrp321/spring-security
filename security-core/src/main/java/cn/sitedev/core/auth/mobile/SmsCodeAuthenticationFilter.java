package cn.sitedev.core.auth.mobile;

import cn.sitedev.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 短信验证码认证过滤器
 * @auther qchen
 * @date 2018/4/30
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String FORM_MOBILE_KEY = "mobileNo";
    private String mobileNoParameter = FORM_MOBILE_KEY;
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        // 拦截路径
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }

    /**
     * 获取手机号封装到SmsCodeAuthenticationToken实例中
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobileNo = this.obtainMobileNo(request);
            if (mobileNo == null) {
                mobileNo = "";
            }
            mobileNo = mobileNo.trim();
            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobileNo);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取手机号
     *
     * @param request
     * @return
     */
    protected String obtainMobileNo(HttpServletRequest request) {
        return request.getParameter(this.mobileNoParameter);
    }

    /**
     * 把请求信息也放到Token里面
     *
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileNoParameter;
    }

}

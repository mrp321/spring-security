package cn.sitedev.app.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义认证成功处理
 * @author: QChen
 * @create: 2018/4/18 0018
 **/
@Component
// 继承默认的认证成功处理器
public class MyAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * ObjectMapper提供了读写JSON的功能
     */
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 当用户被成功认证时调用
     *
     * @param request        引发成功认证的请求
     * @param response       响应
     * @param authentication 在认证过程中创建的Authentication对象
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Authentication :包含着用户认证信息
        logger.info("登陆成功");
        // 从请求头中获取Authorization
        String header = request.getHeader("Authorization");
        // 判断Authorization是否为空,以及是否以Basic开头
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无Client信息");
        }

        // 获取clientId和clientSecret的数组
        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;
        // 从数组中获取clientId
        String clientId = tokens[0];
        // 从数组中获取clientSecret
        String clientSecret = tokens[1];
        // 调用ClientDetailsService中的方法,获取ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        // clientId和clientSecret判断
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientSecret);
        }
        // 创建TokenRequest
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        // 创建OAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        // 创建OAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        // 获取OAuth2AccessToken
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        // 将accesstoken写入到响应中
        response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));


    }

    /**
     * 从请求头中解码出clientId和clientSecret
     *
     * @param header  请求头
     * @param request 请求
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {
        // 请求头=>Authorization=Basic bXlDbGllbnRJZDpteUNsaWVudFNlY3JldA==
        // header.substring(6)=>bXlDbGllbnRJZDpteUNsaWVudFNlY3JldA==
        // base64解码=>token=myClientId:myClientSecret
        // token.substring(0, delim)=>myClientId
        // token.substring(delim + 1)=>myClientSecret
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}

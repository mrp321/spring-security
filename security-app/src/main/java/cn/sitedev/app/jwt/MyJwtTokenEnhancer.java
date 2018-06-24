package cn.sitedev.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @description Jwt 令牌增强
 * @auther qchen
 * @date 2018/6/21
 */
public class MyJwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("company", "myCompany");
        // 设置附加信息
        // 因为在DefaultTokenServices类中的createAccessToken(OAuth2Authentication, OAuth2RefreshToken)方法中使用的是DefaultOAuth2AccessToken,因此这里需要强制转换
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}

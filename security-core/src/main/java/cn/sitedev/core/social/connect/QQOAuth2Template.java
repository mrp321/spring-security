package cn.sitedev.core.social.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @description QQ OAuth2Template
 * @auther qchen
 * @date 2018/5/5
 */
public class QQOAuth2Template extends OAuth2Template {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 将useParametersForClientAuthentication设置为true,这样调用方法exchangeForAccess时参数才会带上client_id和client_secret
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 覆写创建RestTemplate的方法,添加用于处理Content-Type为text/plain的消息转化器
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate template = super.createRestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return super.createRestTemplate();
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        // 对accessTokenUrl进行post请求,返回String类型的响应结果
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        // 返回结果示例:access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
        // 从返回的结果中分割出返回的数据
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        //授权令牌，Access_Token
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        //该access token的有效期，单位为秒
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        //在授权自动续期步骤中，获取新的Access_Token时需要提供的参数
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }
}

package cn.sitedev.app.auth;

import cn.sitedev.core.properties.OAuth2ClientProperties;
import cn.sitedev.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 认证服务器配置
 * @auther qchen
 * @date 2018/5/30
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        configSingleClient(clients);
        configMultiClients(clients);
    }

    /**
     * 配置多个第三方应用
     *
     * @param clients
     * @throws Exception
     */
    private void configMultiClients(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (OAuth2ClientProperties config : securityProperties.getOauth2().getClients()) {
                builder.withClient(config.getClientId())
                        .secret(config.getClientSecret())
                        // access_token有效期
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        // refresh_token有效期
                        .refreshTokenValiditySeconds(2592000)
                        .authorizedGrantTypes("refresh_token", "password")
                        .scopes("all", "read", "write");
            }
        }
    }

    /**
     * 配置单个第三方应用
     *
     * @param clients
     * @throws Exception
     */
    private void configSingleClient(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置clientId和clientSecret
                // 当配置此项后,原先在application.properties中配置的security.oauth2.client.client-id和security.oauth2.client.client-secret会失效
                .withClient("myClientId").secret("myClientSecret")
                // 令牌有效期(单位:s)(默认是0,表示永不过期)
                .accessTokenValiditySeconds(7200)
                // 授权模式(数组)
                .authorizedGrantTypes("refresh_token", "password")
                // 权限(数组)
                // 如果服务器端针对指定客户端配置了scope参数,则客户端在发起请求时可以不带scope参数
                // 如果客户端在发起请求时带了scope参数,则该scope参数一定要在服务器端配置的scope参数集合内
                .scopes("all", "read", "write");
    }
}

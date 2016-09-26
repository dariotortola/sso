package com.konecta.sso.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public OAuth2RequestFactory oauth2RequestFactory() {
        DefaultOAuth2RequestFactory def = new DefaultOAuth2RequestFactory(clientDetailsService());
        def.setCheckUserScopes(true);
        return def;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // usamos tokens jwt
        endpoints.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }

    /**
     * Esta query es algo compleja porque tiene varias subconsultas con el
     * objetivo de que el mantenimiento sea más sencillo. Utiliza una función
     * propia de Oracle para combinar scopes, resource_ids,
     * authorized_grant_types, authorities y autoApprove a partir de los
     * resultados en varias tablas
     */
    private static final String SELECT_CLIENT_DETAILS = "select A.codigo as client_id, A.password as client_secret, (select LISTAGG(resources.codigo, ',') WITHIN GROUP (ORDER BY resources.codigo) from APLICACION_RECURSO AR inner join APLICACIONES resources on resources.id = AR.RECURSO where AR.CLIENTE = A.ID) as \"resource_ids\", (select LISTAGG(APSC.scope, ',') WITHIN GROUP (ORDER BY APSC.scope) from APLICACION_SCOPES APSC where APSC.APLICACION = A.ID) as \"scope\", (select LISTAGG(GT.name, ',') WITHIN GROUP (ORDER BY GT.name) from ALLOWED_GRANT_TYPES AGT inner join GRANT_TYPES GT on GT.ID = AGT.GRANT_TYPE where AGT.CLIENTE  = A.ID) as \"authorized_grant_types\", A.web_server_redirect_uri, (select LISTAGG(P.codigo, ',') WITHIN GROUP (ORDER BY P.codigo) from PERMISOS P inner join APLICACION_RECURSO AR on AR.RECURSO = P.ID inner join APLICACIONES resources on resources.id = AR.RECURSO where AR.CLIENTE = A.ID) as \"authorities\", A.access_token_validity, A.refresh_token_validity, A.additional_information, (select LISTAGG(APSC.scope, ',') WITHIN GROUP (ORDER BY APSC.scope) from APLICACION_SCOPES APSC where APSC.APLICACION = A.ID AND APSC.auto_Approve > 0) as \"autoapprove\" from aplicaciones A where A.codigo = ?";

    @Bean
    public ClientDetailsService clientDetailsService() {
        JdbcClientDetailsService service = new JdbcClientDetailsService(dataSource);
        service.setSelectClientDetailsSql(SELECT_CLIENT_DETAILS);
        return service;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }
}

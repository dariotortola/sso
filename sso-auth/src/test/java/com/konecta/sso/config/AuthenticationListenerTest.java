package com.konecta.sso.config;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.konecta.sso.service.UserClientAuthoritiesService;

@RunWith(SpringRunner.class)
public class AuthenticationListenerTest {

    @InjectMocks
    private AuthenticationListener listener = new AuthenticationListener();
    @Mock
    private JdbcTemplate template;
    @Mock
    private UserClientAuthoritiesService service;

    @Test
    public void notLog() {
        Authentication authentication = Mockito.mock(Authentication.class);
        AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(authentication);
        listener.onApplicationEvent(event);

        Mockito.verifyZeroInteractions(template, service);
    }

    @Test
    public void logLastAny() {
        OAuth2Authentication auth = Mockito.mock(OAuth2Authentication.class);
        AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(auth);

        String username = "username";
        String clientId = "client";
        Mockito.when(auth.getName()).thenReturn(username);
        /*
         * can't use a mock, throws error when using when
         */
        OAuth2Request request = new OAuth2Request(Collections.emptyMap(), clientId, Collections.emptyList(), true,
                Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), Collections.emptyMap());
        Mockito.when(auth.getOAuth2Request()).thenReturn(request);

        ReflectionTestUtils.setField(listener, "soloConAuthorities", false);
        ReflectionTestUtils.setField(listener, "soloUltimoLogin", true);

        // no previous login
        Mockito.when(template.update(Matchers.anyString(), Matchers.any(Date.class), Matchers.eq(username),
                Matchers.eq(clientId))).thenReturn(0);
        listener.onApplicationEvent(event);
        // once for the update, once for the insert
        Mockito.verify(template, Mockito.times(2)).update(Matchers.anyString(), Matchers.any(Date.class),
                Matchers.eq(username), Matchers.eq(clientId));
    }

    @Test
    public void logAllAny() {
        OAuth2Authentication auth = Mockito.mock(OAuth2Authentication.class);
        AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(auth);

        String username = "username";
        String clientId = "client";
        Mockito.when(auth.getName()).thenReturn(username);
        /*
         * can't use a mock, throws error when using when
         */
        OAuth2Request request = new OAuth2Request(Collections.emptyMap(), clientId, Collections.emptyList(), true,
                Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), Collections.emptyMap());
        Mockito.when(auth.getOAuth2Request()).thenReturn(request);

        ReflectionTestUtils.setField(listener, "soloConAuthorities", false);
        ReflectionTestUtils.setField(listener, "soloUltimoLogin", false);

        listener.onApplicationEvent(event);
        // once for the insert
        Mockito.verify(template, Mockito.times(1)).update(Matchers.contains("insert"), Matchers.any(Date.class),
                Matchers.eq(username), Matchers.eq(clientId));
    }

    @Test
    public void logLastAuth() {
        OAuth2Authentication auth = Mockito.mock(OAuth2Authentication.class);
        AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(auth);

        String username = "username";
        String clientId = "client";
        Mockito.when(auth.getName()).thenReturn(username);
        /*
         * can't use a mock, throws error when using when
         */
        OAuth2Request request = new OAuth2Request(Collections.emptyMap(), clientId, Collections.emptyList(), true,
                Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), Collections.emptyMap());
        Mockito.when(auth.getOAuth2Request()).thenReturn(request);

        ReflectionTestUtils.setField(listener, "soloConAuthorities", true);
        ReflectionTestUtils.setField(listener, "soloUltimoLogin", true);

        // no authorities
        Mockito.when(service.getAuthorities(auth)).thenReturn(new ArrayList<>());

        // do nothing
        listener.onApplicationEvent(event);
        Mockito.verify(service).getAuthorities(auth);
        Mockito.verifyZeroInteractions(template);

        // authorities
        // no previous login
        List<String> authorities = Collections.singletonList("USER");
        Mockito.when(service.getAuthorities(auth)).thenReturn(authorities);
        Mockito.when(template.update(Matchers.anyString(), Matchers.any(Date.class), Matchers.eq(username),
                Matchers.eq(clientId))).thenReturn(0);
        listener.onApplicationEvent(event);
        // once for the update, once for the insert
        Mockito.verify(template, Mockito.times(2)).update(Matchers.anyString(), Matchers.any(Date.class),
                Matchers.eq(username), Matchers.eq(clientId));

        // authorities
        // previous login
        Mockito.reset(template);
        Mockito.when(template.update(Matchers.anyString(), Matchers.any(Date.class), Matchers.eq(username),
                Matchers.eq(clientId))).thenReturn(1);
        listener.onApplicationEvent(event);
        // only once when update
        Mockito.verify(template, Mockito.times(1)).update(Matchers.anyString(), Matchers.any(Date.class),
                Matchers.eq(username), Matchers.eq(clientId));
    }
}

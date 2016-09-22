package com.konecta.sso.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserClientAuthoritiesServiceTest {

    @InjectMocks
    private UserClientAuthoritiesService service = new UserClientAuthoritiesService();
    @Mock
    private JdbcTemplate jdbc;

    @Test
    public void test() {
        OAuth2Authentication logged = Mockito.mock(OAuth2Authentication.class);
        String username = "username";
        String client = "client";
        Mockito.when(logged.getName()).thenReturn(username);
        OAuth2Request request = new OAuth2Request(Collections.emptyMap(), client, Collections.emptyList(), false,
                Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), Collections.emptyMap());
        Mockito.when(logged.getOAuth2Request()).thenReturn(request);

        BaseMatcher<Object[]> matcher = new BaseMatcher<Object[]>() {

            @Override
            public boolean matches(Object item) {
                Object[] array = (Object[]) item;
                List<Object> all = Arrays.asList(array);
                return all.contains(username) && all.contains(client);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
        service.getAuthorities(logged);

        Mockito.verify(jdbc).query(Matchers.anyString(), Matchers.argThat(matcher), (RowMapper<?>) Matchers.any(RowMapper.class));
    }
}

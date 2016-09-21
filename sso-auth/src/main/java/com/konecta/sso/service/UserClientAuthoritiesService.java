package com.konecta.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserClientAuthoritiesService {
    @Autowired
    private JdbcTemplate jdbc;

    private static final String AUTHORITIES_BY_USER_CLIENT = "select ps.codigo from PERMISOS ps inner join PERFIL_PERMISO pp on ps.ID = pp.PERMISO inner join USUARIO_PERFIL up on up.PERFIL = pp.PERFIL inner join USUARIOS u on u.ID = up.USUARIO inner join APLICACIONES a on a.ID = ps.APLICACION where u.USERNAME = ? and a.CODIGO = ?";
    private static final RowMapper<String> AUTHORITY_MAPPER = (rs, rowNum) -> rs.getString("codigo");

    /**
     * @param logged
     * @return authorities que tiene el username en logged en el cliente
     *         definido por logged
     */
    public List<String> getAuthorities(OAuth2Authentication logged) {
        String clientId = logged.getOAuth2Request().getClientId();
        String username = logged.getName();

        return jdbc.query(AUTHORITIES_BY_USER_CLIENT, new String[] { username, clientId }, AUTHORITY_MAPPER);
    }
}

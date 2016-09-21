package com.konecta.sso.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class KonectaJdbcUserDetailsManager extends JdbcUserDetailsManager {

    private static final String KONECTA_USERS_BY_USERNAME = "select username, sha1_base64 as password, activo from usuarios where username = ? union select username, sha1_base64 as password, activo from usuarios where meta4 = ? union select username, sha1_base64 as password, activo from usuarios where email = ?";

    private static final String KONECTA_GROUP_AUTHORITIES_BY_USERNAME = "select g.id, g.codigo as group_name, a.codigo as authority from perfiles g inner join usuario_perfil gm on g.id = gm.perfil inner join perfil_permiso ga on ga.perfil = g.id inner join permisos a on a.id = ga.permiso inner join usuarios u on u.id = gm.usuario where u.username = ?";

    
    
    @Autowired
    public KonectaJdbcUserDetailsManager(DataSource datasource) {
        super();
        // queries normales para konecta
        setUsersByUsernameQuery(KONECTA_USERS_BY_USERNAME);
        setEnableGroups(true);
        setGroupAuthoritiesByUsernameQuery(KONECTA_GROUP_AUTHORITIES_BY_USERNAME);
        setEnableAuthorities(false);
        setDataSource(datasource);
    }

    @Override
    protected void addCustomAuthorities(String username, List<GrantedAuthority> authorities) {
        // esto permite la autenticación de cualquier usuario registrado en base
        // de datos, tenga permisos o no
        if (authorities.isEmpty()) {
            // añadimos una autoridad aleatoria
            char inicial = 'a';
            char[] chars = new char[20];
            Random random = new Random();
            for (int i = 0; i < chars.length; i++) {
                chars[i] = (char) ('a' + random.nextInt('z' - inicial));
            }
            authorities.add(new SimpleGrantedAuthority(new String(chars)));
        }
    }

    /**
     * inicializado a 1, por defecto
     */
    private int userParameters = 1;

    /**
     * @param query
     * @return cantidad de ? en query
     */
    private static int countParameters(String query) {
        int start = 0;
        int total = 0;
        int pos;
        while (-1 < (pos = query.indexOf('?', start))) {
            total++;
            start = 1 + pos;
        }
        return total;
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        String[] parameters = new String[userParameters];
        Arrays.fill(parameters, username);

        return getJdbcTemplate().query(this.getUsersByUsernameQuery(), parameters, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                return new User(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            }

        });
    }

    @Override
    public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
        super.setUsersByUsernameQuery(usersByUsernameQueryString);
        userParameters = countParameters(usersByUsernameQueryString);
    }

}

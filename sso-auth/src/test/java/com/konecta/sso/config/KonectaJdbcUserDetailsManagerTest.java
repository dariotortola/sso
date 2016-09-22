package com.konecta.sso.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class KonectaJdbcUserDetailsManagerTest {

    private DataSource datasource;
    private KonectaJdbcUserDetailsManager manager;

    @Before
    public void prepare() {
        datasource = Mockito.mock(DataSource.class);
        manager = new KonectaJdbcUserDetailsManager(datasource);
    }

    @Test
    public void test() throws SQLException {
        String username = "username";
        
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(datasource.getConnection()).thenReturn(connection);
        
        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement(Matchers.anyString())).thenReturn(statement);
        
        ResultSet result = Mockito.mock(ResultSet.class);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.getString(1)).thenReturn(username);
        Mockito.when(result.getString(2)).thenReturn("password");
        Mockito.when(result.getBoolean(3)).thenReturn(true);
        Mockito.when(result.next()).thenReturn(true, false);

        manager.loadUserByUsername(username);
    }
}

package dataacces;

import dataaccess.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author daniCV
 */

public class ConexionTest {
    
    @Test
    public void getConnectionTest() throws SQLException {
       Connection currentConnection = new Conexion().getConnection();
       Assert.assertNotNull(currentConnection);
    }
    
    @Test
    public void setConnectionTest() throws SQLException {
        Connection currentConnection = new Conexion().getConnection();
        Conexion connection = new Conexion();
        connection.setConnection(currentConnection);
        Assert.assertNotNull(connection);
    }
}

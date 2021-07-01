package dataaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniCV
 */

public class Conexion {
    private Connection connection;
    private String DRIVER;
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    
    public void readPropertiesFile() {
        try (InputStream inputStream = new FileInputStream("src\\dataaccess\\data.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            DRIVER = properties.getProperty("DRIVER");
            URL = properties.getProperty("URL");
            USERNAME = properties.getProperty("USER");
            PASSWORD = properties.getProperty("PASSWORD");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() throws SQLException {
        readPropertiesFile();
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); 
    }
    
    public Connection getConnection() throws SQLException {
        connect();
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    } 
    
    public void disconnect() {
        if (connection!=null) {
            try{
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
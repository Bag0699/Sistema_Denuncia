package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBdSingleton {

    private static ConexionBdSingleton instance;
    private Connection cn;

    
    ConfigLoader configLoader = new ConfigLoader("config.properties");
    String url = configLoader.getProperty("db.url");
    String user = configLoader.getProperty("db.username");
    String psw = configLoader.getProperty("db.password");
    String driver = configLoader.getProperty("db.driver");

    public ConexionBdSingleton() throws SQLException {

        try {
            Class.forName(driver);
            this.cn = DriverManager.getConnection(url, user, psw);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public void cerrarConexion() throws SQLException {
        try {
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            cn.close();
        } finally {
            cn.close();
        }
    }

    public static ConexionBdSingleton getInstace() throws SQLException {
        if (instance == null) {
            instance = new ConexionBdSingleton();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConexionBdSingleton();
        }
        return instance;
    }

    public Connection getConnection() {
        return cn;
    }
}

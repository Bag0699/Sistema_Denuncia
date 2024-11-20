package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBdSingleton {

    private static ConexionBdSingleton instance;
    private Connection cn;

      private String url = "jdbc:sqlserver://FRANCO\\SQLEXPRESS:1433;" +
                        "database=DenunciasDb;" +
                        "trustServerCertificate=true;" +
                        "encrypt=true;";
  
    private String user = "xz";
    private String psw = "xz";
    
    public ConexionBdSingleton()throws SQLException{
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.cn = DriverManager.getConnection(url, user, psw);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }
    public void cerrarConexion() throws SQLException {
        try {
            cn.close();
        } catch (SQLException e){
            e.printStackTrace();
            cn.close();
        }finally {
            cn.close();
        }
    }

    public static ConexionBdSingleton getInstace() throws SQLException{
        if(instance == null){
            instance = new ConexionBdSingleton();
        }else if (instance.getConnection().isClosed()){
            instance = new ConexionBdSingleton();
        }
        return instance;
    }
    
    public Connection getConnection(){
        return cn;
    }
}

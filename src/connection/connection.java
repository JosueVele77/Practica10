package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connection implements configurable{
    protected Connection conn=null;
    protected Statement stmt= null;
    protected ResultSet res= null;

    public Connection openConn() {
        try {
            Class.forName(driver);
            conn=DriverManager.getConnection(getURL());
            if(conn!=null) {
                System.out.println("Conexion Exitosa");
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.err.println("Error de conexión en el Driver");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.err.println("Error en el URL");
        }
        return conn;
    }

    public boolean closeConn() {
        try {
            if (conn != null) {
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
        return false;
    }
}
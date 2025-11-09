package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    //static: no va a cambiar el valor.
    private static String url = "jdbc:mysql://localhost:3306/sesion_4?serverTimeZone=UTC";
    private static String user = "root";
    private static String password = "";
    private static Connection connection;

    public static Connection getConnection() {
        try{
            //diferencia entre drivemanager y entity
            return connection = DriverManager.getConnection(url, user,password);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }


}

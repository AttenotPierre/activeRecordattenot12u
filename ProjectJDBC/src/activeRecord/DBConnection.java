package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // --- Paramètres de connexion (encapsulés) ---
    private String userName = "root";
    private String password = "";
    private String serverName = "127.0.0.1";
    private String portNumber = "3306"; // MAMP
    private static String dbName = "testpersonne"; // valeur par défaut

    // --- L'unique connexion ---
    private static Connection connect=null;

    // Constructeur privé -> Singleton
    private DBConnection() throws SQLException {

        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        String urlDB = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
        System.out.println("Connexion à : " + urlDB);
        connect = DriverManager.getConnection(urlDB, connectionProps);
    }
    // Factory : fournit la Connection
    public static synchronized Connection getConnection() throws SQLException {
        if(connect == null) {
            new DBConnection();
        }
        return connect;
    }


    public static void setNomDB(String nom) {
        if(nom!=null && nom != dbName){
            dbName=nom;
            connect=null;
        }
    }

}

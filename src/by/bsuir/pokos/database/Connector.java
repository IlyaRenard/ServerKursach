package by.bsuir.pokos.database;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import com.*;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Gavrilik on 01.05.2020.
 */
public class Connector {

    private static final String URL      ="jdbc:mysql://localhost:3306/slls?autoReconnect=true&useSSL=false&useUnicode=true&amp&characterEncoding=utf8&maxReconnects=10";
    private static final String USER     = "root";
    private static final String PASSWORD ="";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Driver driver;
            driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }
}

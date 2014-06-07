package cir.lab.csn.data.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class JDBCConnectionMaker implements ConnectionMaker {
    private static String DB_PATH = "jdbc:mysql://127.0.0.1:3306/csn_db";
    private static String DB_USER = "root";
    private static String DB_PW = "root";

    public Connection makeConnection() throws ClassNotFoundException, SQLException, IOException {
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection c  = DriverManager.getConnection(DB_PATH, DB_USER, DB_PW);
//        return c;

        Properties props = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("db.properties");
        props.load(in);
        in.close();
        String url = props.getProperty("JDBC.URL");
        String username = props.getProperty("JDBC.Username");
        String password = props.getProperty("JDBC.Password");
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
        Class.forName("com.mysql.jdbc.Driver");
        Connection c  = DriverManager.getConnection(url, username, password);
        return c;
    }
}

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
    private static String DB_PATH = "jdbc:mysql://117.16.146.55:3306/csn";
    private static String DB_USER = "root";
    private static String DB_PW = "890217";

    public Connection makeConnection() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c  = DriverManager.getConnection(DB_PATH, DB_USER, DB_PW);
        return c;
    }
}
